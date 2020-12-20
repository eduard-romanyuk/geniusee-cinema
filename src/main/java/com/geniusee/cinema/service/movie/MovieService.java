package com.geniusee.cinema.service.movie;

import com.geniusee.cinema.dto.MovieDto;
import com.geniusee.cinema.dto.MovieIdentityDto;

public interface MovieService {
    MovieIdentityDto findById(long id);

    MovieIdentityDto create(MovieDto movieDto);

    MovieIdentityDto update(MovieDto movieDto, long id);

    void delete(long id);
}
