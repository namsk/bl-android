package com.example.android.movielistapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private ListView list;
//    private final static String server = "http://1.233.30.63:3000";
    private final static String server = "http://192.168.1.130:3000";
    ArrayList<Movie> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = server + "/movies";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("[Response]", response.toString());
                        try {
                            JSONArray movies = response.getJSONArray("movies");
                            Log.d("[movies]", movies.toString());

                            Gson gson = new Gson();
                            Movie[] array = gson.fromJson(movies.toString(), Movie[].class);
                            data = new ArrayList<>(Arrays.asList(array));

                            MovieAdapter adapter = new MovieAdapter(MainActivity.this,
                                    R.layout.list_item_movie,
                                    data);

                            list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(request);
//        MovieManager.getInstance().getQueue().add(request);
        list.setOnItemClickListener(this);
    }

    //
    //  class Movie는 외부에 선언

    //  Holder의 선언
    class MovieHolder {
        public ImageView imageView;
        public TextView tvTitle;
        public TextView tvDescription;

        public MovieHolder(View root) {
            imageView = root.findViewById(R.id.imageView);
            tvTitle = root.findViewById(R.id.tvTitle);
            tvDescription = root.findViewById(R.id.tvDescription);
        }
    }

    //  Custom Adapter 구현
    class MovieAdapter extends ArrayAdapter<Movie> {
//        Context context;
//        int resource;
        int resource;
        ArrayList<Movie> data;

        public MovieAdapter(Context context, int resource, ArrayList<Movie> data) {
            super(context, resource);
            this.data = data;
            this.resource = resource;
        }

//        public MovieAdapter(@NonNull Context context,
//                            int resource,
//                            @NonNull ArrayList<Movie> data) {
//            super(context, resource);
//            this.context = context;
//            this.resource = resource;
//            this.data = data;
//        }

        @Override
        public int getCount() {
            return data.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            MovieHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resource, null);
                holder = new MovieHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MovieHolder) convertView.getTag();
            }
            final ImageView imageView = holder.imageView;
            TextView tvTitle = holder.tvTitle;
            TextView tvDescription = holder.tvDescription;

            Movie movie = data.get(position);

            //  Image 처리
            String imageUrl = server + "/images/" + movie.getImage();
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

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

            queue.add(request);
//            MovieManager.getInstance().getQueue().add(request);
            tvTitle.setText(movie.getTitle());
            tvDescription.setText(movie.getDirector()
                    + "(개봉년도:"
                    + movie.getYear() + ")");

            return convertView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView,
                            View view,
                            int position,
                            long id) {
        if (adapterView == list) {
            Movie movie = data.get(position);
//            Toast.makeText(this,
//                    movie.getTitle() + ":" + movie.getId(),
//                    Toast.LENGTH_LONG)
//                    .show();
            Intent intent = new Intent(MainActivity.this, MovieInfoActivity.class);
            intent.putExtra("id", movie.getId());
            startActivity(intent);
        }
    }
}
