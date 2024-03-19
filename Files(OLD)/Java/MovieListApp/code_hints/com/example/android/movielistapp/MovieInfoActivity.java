package com.example.android.movielistapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieInfoActivity extends AppCompatActivity {
    private final static String server = "http://192.168.1.130:3000";
//    private final static String server = "http://192.168.1.4:3000";
    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDirector;
    private TextView tvYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        String url = server + "/movies/" + id;

        imageView = findViewById(R.id.imageView);
        tvTitle = findViewById(R.id.tvTitle);
        tvDirector = findViewById(R.id.tvDirector);
        tvYear = findViewById(R.id.tvYear);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("[Response]", response.toString());
                        try {
                            JSONObject movieJson = response.getJSONObject("movie");
                            Log.d("[movies]", movieJson.toString());

                            Gson gson = new Gson();
                            Movie movie = gson.fromJson(movieJson.toString(), Movie.class);

                            tvTitle.setText(movie.getTitle());
                            tvDirector.setText(movie.getDirector());
                            tvYear.setText("(" + movie.getYear() + ")");

                            String imageUrl = server + "/images/" + movie.getImage();

                            ImageRequest request = new ImageRequest(imageUrl,
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            imageView.setImageBitmap(response);
                                        }
                                    },
                                    0,
                                    0,
                                    ImageView.ScaleType.CENTER_INSIDE,
                                    Bitmap.Config.RGB_565,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });
                            MovieManager.getInstance().getQueue().add(request);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieInfoActivity.this,
                                "데이터를 불러올 수 없습니다",
                                Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }
                }
        );
        MovieManager.getInstance().getQueue().add(request);
    }
}
