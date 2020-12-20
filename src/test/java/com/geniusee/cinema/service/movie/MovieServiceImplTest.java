package com.geniusee.cinema.service.movie;

import com.geniusee.cinema.domain.movie.Movie;
import com.geniusee.cinema.dto.movie.MovieDto;
import com.geniusee.cinema.dto.movie.MovieIdentityDto;
import com.geniusee.cinema.mapper.movie.MovieIdentityMapper;
import com.geniusee.cinema.mapper.movie.MovieMapper;
import com.geniusee.cinema.repository.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("unit-test")
class MovieServiceImplTest {
    @MockBean
    private MovieRepository movieRepository;
    @SpyBean
    private MovieMapper movieMapper;
    @SpyBean
    private MovieIdentityMapper movieIdentityMapper;

    @Autowired
    private MovieService movieService;

    @Test
    void findById_EntityNotFound() {
        long id = 1;
        assertThrows(EntityNotFoundException.class, () -> movieService.findById(id));
        Mockito.verify(movieRepository, Mockito.atLeastOnce()).findById(id);
        Mockito.verify(movieIdentityMapper, Mockito.never()).toDto(anyMovie());
    }

    @Test
    void findById_EntityExists() {
        Movie movie = sampleMovie();
        Mockito.when(movieRepository.findById(movie.getId()))
                .thenReturn(Optional.of(movie));

        MovieIdentityDto movieDto = movieService.findById(movie.getId());

        assertEquals(movie.getId(), movieDto.getId());
        Mockito.verify(movieRepository, Mockito.atLeastOnce()).findById(movie.getId());
        Mockito.verify(movieIdentityMapper, Mockito.atLeastOnce()).toDto(movie);
    }

    @Test
    void create() {
        Movie movie = sampleMovie();
        MovieDto movieDto = createMovieDto();
        Mockito.when(movieMapper.toEntity(movieDto)).thenReturn(movie);
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        movieService.create(movieDto);

        Mockito.verify(movieMapper, Mockito.atLeastOnce()).toEntity(movieDto);
        assertSaveAndMapDto(movie);
    }

    @Test
    void update_EntityNotFound() {
        long id = 1;
        Mockito.when(movieRepository.getOne(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> movieService.update(createMovieDto(), id));
        Mockito.verify(movieRepository, Mockito.atLeastOnce()).getOne(id);
        Mockito.verify(movieMapper, Mockito.never()).updateEntityFromDto(any(MovieDto.class), anyMovie());
        Mockito.verify(movieRepository, Mockito.never()).save(anyMovie());
        Mockito.verify(movieIdentityMapper, Mockito.never()).toDto(anyMovie());
    }

    @Test
    void update_EntityExists() {
        Movie movie = sampleMovie();
        Mockito.when(movieRepository.getOne(movie.getId())).thenReturn(movie);
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        MovieDto movieDto = createMovieDto();
        movieService.update(movieDto, movie.getId());

        Mockito.verify(movieRepository, Mockito.atLeastOnce()).getOne(movie.getId());
        Mockito.verify(movieMapper, Mockito.atLeastOnce()).updateEntityFromDto(movieDto, movie);
        assertSaveAndMapDto(movie);
    }

    @Test
    void delete() {
        long id = 1;

        movieService.delete(id);

        Mockito.verify(movieRepository, Mockito.atLeastOnce()).deleteById(id);
    }

    @Test
    void findAll() {
        Page<Movie> page = new PageImpl<>(Collections.singletonList(sampleMovie()));
        Specification<Movie> specification = Specification.where(nameLike("girl"));
        Pageable pageable = Pageable.unpaged();
        Mockito.when(movieRepository.findAll(specification, pageable)).thenReturn(page);

        movieService.findAll(specification, pageable);

        Mockito.verify(movieRepository, Mockito.atLeastOnce()).findAll(specification, pageable);
        Mockito.verify(movieIdentityMapper, Mockito.atLeastOnce()).toDto(anyMovie());
    }

    private Movie anyMovie() {
        return any(Movie.class);
    }

    private Movie sampleMovie() {
        Movie movie = new Movie("Sample", null, 100);
        movie.setId(1);
        return movie;
    }

    private MovieDto createMovieDto() {
        Movie movie = sampleMovie();
        MovieDto movieDto = movieIdentityMapper.toDto(movie);
        Mockito.clearInvocations(movieIdentityMapper);
        return movieDto;
    }

    private void assertSaveAndMapDto(Movie movie) {
        Mockito.verify(movieRepository, Mockito.atLeastOnce()).save(movie);
        Mockito.verify(movieIdentityMapper, Mockito.atLeastOnce()).toDto(movie);
    }

    private static Specification<Movie> nameLike(String name) {
        return (movie, cq, cb) -> cb.like(movie.get("name"), "%" + name + "%");
    }
}