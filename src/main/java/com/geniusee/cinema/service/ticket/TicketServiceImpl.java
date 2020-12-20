package com.geniusee.cinema.service.ticket;

import com.geniusee.cinema.domain.ticket.Ticket;
import com.geniusee.cinema.dto.ticket.TicketDto;
import com.geniusee.cinema.dto.ticket.TicketIdentityDto;
import com.geniusee.cinema.mapper.ticket.movie.TicketIdentityMapper;
import com.geniusee.cinema.mapper.ticket.movie.TicketMapper;
import com.geniusee.cinema.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final TicketIdentityMapper ticketIdentityMapper;

    @Override
    public TicketIdentityDto findById(long id) {
        return ticketRepository.findById(id)
                .map(ticketIdentityMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public TicketIdentityDto create(TicketDto ticketDto) {
        Ticket entity = ticketMapper.toEntity(ticketDto);
        entity = ticketRepository.save(entity);
        return ticketIdentityMapper.toDto(entity);
    }

    @Override
    public TicketIdentityDto update(TicketDto ticketDto, long id) {
        Ticket entity = ticketRepository.getOne(id);
        ticketMapper.updateEntityFromDto(ticketDto, entity);
        entity = ticketRepository.save(entity);
        return ticketIdentityMapper.toDto(entity);
    }

    @Override
    public void delete(long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Page<TicketIdentityDto> findAll(Specification<Ticket> specification, Pageable pageable) {
        Page<Ticket> page = ticketRepository.findAll(specification, pageable);
        return page.map(ticketIdentityMapper::toDto);
    }
}
