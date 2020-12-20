package com.geniusee.cinema.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieDto {
    private String name;
    private String description;
    private int durationMinutes;
}
