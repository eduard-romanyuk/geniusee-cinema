package com.geniusee.cinema.mapper.movie;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.dto.MovieDto;
import com.geniusee.cinema.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper extends BaseMapper<MovieDto, Movie> {
    @Override
    default MovieDto toDto(Movie movie){
        throw new UnsupportedOperationException("Entity cannot be mapped to non-identity dto");
    }

    void updateEntityFromDto(MovieDto dto, @MappingTarget Movie entity);
}
