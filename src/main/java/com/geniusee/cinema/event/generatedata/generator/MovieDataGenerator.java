package com.geniusee.cinema.event.generatedata.generator;

import com.geniusee.cinema.domain.movie.Movie;
import com.geniusee.cinema.event.generatedata.DataGenerator;
import com.geniusee.cinema.repository.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Order(1)
@Component
@Profile("!unit-test")
@RequiredArgsConstructor
public class MovieDataGenerator implements DataGenerator {
    private final MovieRepository movieRepository;

    @Override
    public void generate() {
        List<Movie> movies = Arrays.asList(
                new Movie("On the Rocks", null, 100),
                new Movie("The Trip to Greece", null, 130),
                new Movie("Lost Girls", null, 115)
        );
        movieRepository.saveAll(movies);
    }
}
