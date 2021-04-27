package com.example.android.movielistapp;

/**
 * Created by namsk on 2018. 2. 14..
 */

public class Movie {
    private Integer id;
    private String title;
    private String director;
    private String image;
    private Integer year;

    public Integer getYear() {
        return year;
    }

    //  ... getters and setters
    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
