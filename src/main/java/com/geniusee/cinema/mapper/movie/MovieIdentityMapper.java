package com.geniusee.cinema.mapper.movie;

import com.geniusee.cinema.domain.movie.Movie;
import com.geniusee.cinema.dto.movie.MovieIdentityDto;
import com.geniusee.cinema.mapper.ToDtoMapper;
import com.geniusee.cinema.mapper.ToEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieIdentityMapper extends ToEntityMapper<MovieIdentityDto, Movie>,
        ToDtoMapper<MovieIdentityDto, Movie> {
}
