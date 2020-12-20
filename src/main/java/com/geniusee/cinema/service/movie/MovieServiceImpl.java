package com.geniusee.cinema.service.movie;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.dto.MovieDto;
import com.geniusee.cinema.dto.MovieIdentityDto;
import com.geniusee.cinema.exception.ResourceNotFoundException;
import com.geniusee.cinema.mapper.movie.MovieIdentityMapper;
import com.geniusee.cinema.mapper.movie.MovieMapper;
import com.geniusee.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieIdentityMapper movieIdentityMapper;

    private static final String movieNotFoundFormat = "Movie with id=%d not found";

    @Override
    public MovieIdentityDto findById(long id) {
        return movieRepository.findById(id)
                .map(movieIdentityMapper::toDto)
                .orElseThrow(() -> notFound(id));
    }

    @Override
    public MovieIdentityDto create(MovieDto movieDto) {
        Movie entity = movieMapper.toEntity(movieDto);
        entity = movieRepository.save(entity);
        return movieIdentityMapper.toDto(entity);
    }

    @Override
    public MovieIdentityDto update(MovieDto dto, long id) {
        Movie entity = movieRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        movieMapper.updateEntityFromDto(dto, entity);
        entity = movieRepository.save(entity);
        return movieIdentityMapper.toDto(entity);
    }

    @Override
    public void delete(long id) {
        movieRepository.deleteById(id);
    }

    private ResourceNotFoundException notFound(long id) {
        return new ResourceNotFoundException(String.format(movieNotFoundFormat, id));
    }
}
