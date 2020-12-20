package com.geniusee.cinema.mapper.movie;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.dto.MovieIdentityDto;
import com.geniusee.cinema.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieIdentityMapper extends BaseMapper<MovieIdentityDto, Movie> {
}
