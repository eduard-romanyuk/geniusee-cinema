package com.geniusee.cinema.controller.ticket;

import com.geniusee.cinema.dto.ticket.TicketDto;
import com.geniusee.cinema.dto.ticket.TicketIdentityDto;
import com.geniusee.cinema.service.ticket.TicketService;
import com.geniusee.cinema.specification.ticket.TicketSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{id}")
    public TicketIdentityDto findById(@PathVariable long id) {
        return ticketService.findById(id);
    }

    @PostMapping
    public TicketIdentityDto create(@RequestBody @Valid TicketDto dto) {
        return ticketService.create(dto);
    }

    @PutMapping("/{id}")
    public TicketIdentityDto update(@RequestBody @Valid TicketDto dto, @PathVariable long id) {
        return ticketService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        ticketService.delete(id);
    }

    @GetMapping
    public Page<TicketIdentityDto> findAll(TicketSpecification specification, Pageable pageable) {
        return ticketService.findAll(specification, pageable);
    }
}
