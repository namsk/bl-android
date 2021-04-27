package com.example.android.movielistapp;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by namsk on 2018. 2. 12..
 */

class MovieManager {
    private static final MovieManager ourInstance = new MovieManager();
    private RequestQueue queue;

    static MovieManager getInstance() {
        return ourInstance;
    }
    private MovieManager() {
        queue = Volley.newRequestQueue(MyApplication.getContext());
    }

    public RequestQueue getQueue() {
        return this.queue;
    }
}
