package com.geniusee.cinema.service.movie;

import com.geniusee.cinema.domain.Movie;
import com.geniusee.cinema.dto.MovieDto;
import com.geniusee.cinema.dto.MovieIdentityDto;
import com.geniusee.cinema.mapper.movie.MovieIdentityMapper;
import com.geniusee.cinema.mapper.movie.MovieMapper;
import com.geniusee.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieIdentityMapper movieIdentityMapper;

    @Override
    public MovieIdentityDto findById(long id) {
        return movieRepository.findById(id)
                .map(movieIdentityMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public MovieIdentityDto create(MovieDto movieDto) {
        Movie entity = movieMapper.toEntity(movieDto);
        entity = movieRepository.save(entity);
        return movieIdentityMapper.toDto(entity);
    }

    @Override
    public MovieIdentityDto update(MovieDto dto, long id) {
        Movie entity = movieRepository.getOne(id);
        movieMapper.updateEntityFromDto(dto, entity);
        entity = movieRepository.save(entity);
        return movieIdentityMapper.toDto(entity);
    }

    @Override
    public void delete(long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Page<MovieIdentityDto> findAll(Specification<Movie> specification, Pageable pageable) {
        Page<Movie> movies = movieRepository.findAll(specification, pageable);
        return movies.map(movieIdentityMapper::toDto);
    }
}
