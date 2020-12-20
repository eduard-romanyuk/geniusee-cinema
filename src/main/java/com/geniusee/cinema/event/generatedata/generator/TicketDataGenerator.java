package com.geniusee.cinema.event.generatedata.generator;

import com.geniusee.cinema.domain.Ticket;
import com.geniusee.cinema.event.generatedata.DataGenerator;
import com.geniusee.cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Order(2)
@Component
@Profile("!unit-test")
@RequiredArgsConstructor
public class TicketDataGenerator implements DataGenerator {
    private final TicketRepository ticketRepository;

    @Override
    public void generate() {
        List<Ticket> tickets = Arrays.asList(
                new Ticket(1, 1, 6, 100),
                new Ticket(1, 1, 2, 200),
                new Ticket(2, 1, 1, 120),
                new Ticket(2, 1, 4, 560),
                new Ticket(3, 1, 1, 400),
                new Ticket(3, 1, 3, 300)
        );
        ticketRepository.saveAll(tickets);
    }
}
