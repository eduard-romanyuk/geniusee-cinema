package com.geniusee.cinema.mapper.ticket.movie;

import com.geniusee.cinema.domain.ticket.Ticket;
import com.geniusee.cinema.dto.ticket.TicketIdentityDto;
import com.geniusee.cinema.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketIdentityMapper extends BaseMapper<TicketIdentityDto, Ticket> {
}
