package com.geniusee.cinema.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @JoinColumn(name = "movie_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Movie movie;

    @Column(name = "movie_id", insertable = false, updatable = false)
    private long movieId;

    @Column(name = "hall")
    private int hall;

    @Column(name = "place")
    private int place;

    @Column(name = "price")
    private int price;

    public void setMovieId(long movieId) {
        this.movieId = movieId;
        this.movie = new Movie(movieId);
    }

    public Ticket(long movieId, int hall, int place, int price) {
        this.setMovieId(movieId);
        this.hall = hall;
        this.place = place;
        this.price = price;
    }
}
