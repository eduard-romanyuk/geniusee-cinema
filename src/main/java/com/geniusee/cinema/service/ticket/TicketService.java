package com.geniusee.cinema.service.ticket;

import com.geniusee.cinema.domain.ticket.Ticket;
import com.geniusee.cinema.dto.ticket.TicketDto;
import com.geniusee.cinema.dto.ticket.TicketIdentityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface TicketService {
    TicketIdentityDto findById(long id);

    TicketIdentityDto create(TicketDto ticketDto);

    TicketIdentityDto update(TicketDto ticketDto, long id);

    void delete(long id);

    Page<TicketIdentityDto> findAll(Specification<Ticket> specification, Pageable pageable);
}
