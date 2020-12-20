package com.geniusee.cinema.service.movie;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.dto.MovieDto;
import com.geniusee.cinema.dto.MovieIdentityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface MovieService {
    MovieIdentityDto findById(long id);

    MovieIdentityDto create(MovieDto movieDto);

    MovieIdentityDto update(MovieDto movieDto, long id);

    void delete(long id);

    Page<MovieIdentityDto> findAll(Specification<Movie> specification, Pageable pageable);
}
