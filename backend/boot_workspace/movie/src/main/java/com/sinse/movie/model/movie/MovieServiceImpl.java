package com.sinse.movie.model.movie;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    //@Autowired 대신 생성자 주입
    private MovieHandler movieHandler;

    public MovieServiceImpl(MovieHandler movieHandler) {
        this.movieHandler = movieHandler;
    }

    @Override
    public List<Movie> parse() {
        return movieHandler.getMovieList();
    }
}
