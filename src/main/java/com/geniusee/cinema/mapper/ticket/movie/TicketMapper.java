package com.geniusee.cinema.mapper.ticket.movie;

import com.geniusee.cinema.domain.ticket.Ticket;
import com.geniusee.cinema.dto.ticket.TicketDto;
import com.geniusee.cinema.mapper.ToEntityMapper;
import com.geniusee.cinema.mapper.UpdateEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper extends ToEntityMapper<TicketDto, Ticket>, UpdateEntityMapper<TicketDto, Ticket> {
}
