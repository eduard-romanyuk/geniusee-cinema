package com.geniusee.cinema.dto.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class TicketDto {
    @Min(value = 1, message = "Movie id cannot be less than 1")
    private long movieId;
    @Min(value = 1, message = "Hall number cannot be less than 1")
    private int hall;
    @Min(value = 1, message = "Place number cannot be less than 1")
    private int place;
    @Min(value = 0, message = "Price cannot be negative")
    private int price;
}
