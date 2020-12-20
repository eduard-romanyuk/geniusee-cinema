package com.geniusee.cinema.mapper.ticket.movie;

import com.geniusee.cinema.domain.ticket.Ticket;
import com.geniusee.cinema.dto.ticket.TicketDto;
import com.geniusee.cinema.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketMapper extends BaseMapper<TicketDto, Ticket> {
    @Override
    default TicketDto toDto(Ticket ticket){
        throw new UnsupportedOperationException("Entity cannot be mapped to non-identity dto");
    }

    void updateEntityFromDto(TicketDto dto, @MappingTarget Ticket entity);
}
