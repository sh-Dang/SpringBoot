package com.sinse.movie.controller;

import com.sinse.movie.model.movie.Movie;
import com.sinse.movie.model.movie.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestMovieController {
    private MovieService movieService;

    public RestMovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping("/test")
    public String test() {
        return "되는사람 없음";
    }
    @GetMapping("/movies")
    public List<Movie> movies() {
        return movieService.parse();
    }
}
