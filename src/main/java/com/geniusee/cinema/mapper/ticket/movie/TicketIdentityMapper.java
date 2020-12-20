package com.geniusee.cinema.mapper.ticket.movie;

import com.geniusee.cinema.domain.ticket.Ticket;
import com.geniusee.cinema.dto.ticket.TicketIdentityDto;
import com.geniusee.cinema.mapper.ToDtoMapper;
import com.geniusee.cinema.mapper.ToEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketIdentityMapper extends ToEntityMapper<TicketIdentityDto, Ticket>,
        ToDtoMapper<TicketIdentityDto, Ticket> {
}
