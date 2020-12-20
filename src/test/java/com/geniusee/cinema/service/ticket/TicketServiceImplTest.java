package com.geniusee.cinema.service.ticket;

import com.geniusee.cinema.domain.movie.Movie;
import com.geniusee.cinema.domain.ticket.Ticket;
import com.geniusee.cinema.dto.ticket.TicketDto;
import com.geniusee.cinema.dto.ticket.TicketIdentityDto;
import com.geniusee.cinema.mapper.ticket.movie.TicketIdentityMapper;
import com.geniusee.cinema.mapper.ticket.movie.TicketMapper;
import com.geniusee.cinema.repository.ticket.TicketRepository;
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
class TicketServiceImplTest {
    @MockBean
    private TicketRepository ticketRepository;
    @SpyBean
    private TicketMapper ticketMapper;
    @SpyBean
    private TicketIdentityMapper ticketIdentityMapper;

    @Autowired
    private TicketService ticketService;

    @Test
    void findById_EntityNotFound() {
        long id = 1;
        assertThrows(EntityNotFoundException.class, () -> ticketService.findById(id));
        Mockito.verify(ticketRepository, Mockito.atLeastOnce()).findById(id);
        Mockito.verify(ticketIdentityMapper, Mockito.never()).toDto(anyTicket());
    }

    @Test
    void findById_EntityExists() {
        Ticket ticket = sampleTicket();
        Mockito.when(ticketRepository.findById(ticket.getId()))
                .thenReturn(Optional.of(ticket));

        TicketIdentityDto ticketDto = ticketService.findById(ticket.getId());

        assertEquals(ticket.getId(), ticketDto.getId());
        Mockito.verify(ticketRepository, Mockito.atLeastOnce()).findById(ticket.getId());
        Mockito.verify(ticketIdentityMapper, Mockito.atLeastOnce()).toDto(ticket);
    }

    @Test
    void create() {
        Ticket ticket = sampleTicket();
        TicketDto ticketDto = createTicketDto();
        Mockito.when(ticketMapper.toEntity(ticketDto)).thenReturn(ticket);
        Mockito.when(ticketRepository.save(ticket)).thenReturn(ticket);

        ticketService.create(ticketDto);

        Mockito.verify(ticketMapper, Mockito.atLeastOnce()).toEntity(ticketDto);
        assertSaveAndMapDto(ticket);
    }

    @Test
    void update_EntityNotFound() {
        long id = 1;
        Mockito.when(ticketRepository.getOne(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> ticketService.update(createTicketDto(), id));
        Mockito.verify(ticketRepository, Mockito.atLeastOnce()).getOne(id);
        Mockito.verify(ticketMapper, Mockito.never()).updateEntityFromDto(any(TicketDto.class), anyTicket());
        Mockito.verify(ticketRepository, Mockito.never()).save(anyTicket());
        Mockito.verify(ticketIdentityMapper, Mockito.never()).toDto(anyTicket());
    }

    @Test
    void update_EntityExists() {
        Ticket ticket = sampleTicket();
        Mockito.when(ticketRepository.getOne(ticket.getId())).thenReturn(ticket);
        Mockito.when(ticketRepository.save(ticket)).thenReturn(ticket);

        TicketDto ticketDto = createTicketDto();
        ticketService.update(ticketDto, ticket.getId());

        Mockito.verify(ticketRepository, Mockito.atLeastOnce()).getOne(ticket.getId());
        Mockito.verify(ticketMapper, Mockito.atLeastOnce()).updateEntityFromDto(ticketDto, ticket);
        assertSaveAndMapDto(ticket);
    }

    @Test
    void delete() {
        long id = 1;

        ticketService.delete(id);

        Mockito.verify(ticketRepository, Mockito.atLeastOnce()).deleteById(id);
    }

    @Test
    void findAll() {
        PageImpl<Ticket> page = new PageImpl<>(Collections.singletonList(sampleTicket()));
        Specification<Ticket> specification = Specification.where(movieIdEqual(1));
        Pageable pageable = Pageable.unpaged();
        Mockito.when(ticketRepository.findAll(specification, pageable)).thenReturn(page);

        ticketService.findAll(specification, pageable);

        Mockito.verify(ticketRepository, Mockito.atLeastOnce()).findAll(specification, pageable);
        Mockito.verify(ticketIdentityMapper, Mockito.atLeastOnce()).toDto(anyTicket());
    }

    private Ticket anyTicket() {
        return any(Ticket.class);
    }

    private Ticket sampleTicket() {
        Ticket ticket = new Ticket(1, 1, 1, 100);
        ticket.setId(1);
        return ticket;
    }

    private TicketDto createTicketDto() {
        Ticket ticket = sampleTicket();
        TicketDto ticketDto = ticketIdentityMapper.toDto(ticket);
        Mockito.clearInvocations(ticketIdentityMapper);
        return ticketDto;
    }

    private void assertSaveAndMapDto(Ticket ticket) {
        Mockito.verify(ticketRepository, Mockito.atLeastOnce()).save(ticket);
        Mockito.verify(ticketIdentityMapper, Mockito.atLeastOnce()).toDto(ticket);
    }

    private static Specification<Ticket> movieIdEqual(long movieId) {
        return (ticket, cq, cb) -> cb.equal(ticket.get("movieId"), movieId);
    }
}