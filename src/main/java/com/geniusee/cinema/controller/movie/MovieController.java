package com.geniusee.cinema.controller.movie;

import com.geniusee.cinema.dto.movie.MovieDto;
import com.geniusee.cinema.dto.movie.MovieIdentityDto;
import com.geniusee.cinema.service.movie.MovieService;
import com.geniusee.cinema.specification.movie.MovieSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/{id}")
    public MovieIdentityDto findById(@PathVariable long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public MovieIdentityDto create(@RequestBody @Valid MovieDto dto) {
        return movieService.create(dto);
    }

    @PutMapping("/{id}")
    public MovieIdentityDto update(@RequestBody @Valid MovieDto dto, @PathVariable long id) {
        return movieService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        movieService.delete(id);
    }

    @GetMapping
    public Page<MovieIdentityDto> findAll(MovieSpecification specification, Pageable pageable) {
        return movieService.findAll(specification, pageable);
    }
}
