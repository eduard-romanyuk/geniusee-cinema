package com.geniusee.cinema.service.movie;

import com.geniusee.cinema.domain.movie.Movie;
import com.geniusee.cinema.dto.movie.MovieDto;
import com.geniusee.cinema.dto.movie.MovieIdentityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface MovieService {
    MovieIdentityDto findById(long id);

    MovieIdentityDto create(MovieDto movieDto);

    MovieIdentityDto update(MovieDto movieDto, long id);

    void delete(long id);

    Page<MovieIdentityDto> findAll(Specification<Movie> specification, Pageable pageable);
}
