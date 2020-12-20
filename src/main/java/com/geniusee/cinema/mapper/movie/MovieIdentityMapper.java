package com.geniusee.cinema.mapper.movie;

import com.geniusee.cinema.domain.movie.Movie;
import com.geniusee.cinema.dto.movie.MovieIdentityDto;
import com.geniusee.cinema.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieIdentityMapper extends BaseMapper<MovieIdentityDto, Movie> {
}
