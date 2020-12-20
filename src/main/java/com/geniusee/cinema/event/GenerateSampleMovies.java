package com.geniusee.cinema.event;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("!unit-test")
@RequiredArgsConstructor
public class GenerateSampleMovies implements ApplicationListener<ContextRefreshedEvent> {
    private final MovieRepository movieRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Movie> movies = Arrays.asList(
                new Movie("On the Rocks", null, 100),
                new Movie("The Trip to Greece", null, 130),
                new Movie("Lost Girls", null, 115)
        );
        movieRepository.saveAll(movies);
    }
}
