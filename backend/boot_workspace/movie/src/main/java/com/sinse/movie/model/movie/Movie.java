package com.sinse.movie.model.movie;

import lombok.Data;

@Data
public class Movie {
    private String title;
    private String director;
    private String year;
    private String genre;
    private String rating;
}
