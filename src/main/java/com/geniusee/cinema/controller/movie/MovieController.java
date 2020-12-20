package com.geniusee.cinema.controller.movie;

import com.geniusee.cinema.dto.MovieDto;
import com.geniusee.cinema.dto.MovieIdentityDto;
import com.geniusee.cinema.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
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
}
