package com.geniusee.cinema.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class MovieDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;
    @Min(value = 0, message = "Duration cannot be negative")
    private int durationMinutes;
}
