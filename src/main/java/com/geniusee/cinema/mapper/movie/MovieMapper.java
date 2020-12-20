package com.geniusee.cinema.mapper.movie;

import com.geniusee.cinema.domain.movie.Movie;
import com.geniusee.cinema.dto.movie.MovieDto;
import com.geniusee.cinema.mapper.ToEntityMapper;
import com.geniusee.cinema.mapper.UpdateEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper extends ToEntityMapper<MovieDto, Movie>, UpdateEntityMapper<MovieDto, Movie> {
}
