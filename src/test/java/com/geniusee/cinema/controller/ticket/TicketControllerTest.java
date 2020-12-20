package com.geniusee.cinema.controller.ticket;

import com.geniusee.cinema.dto.ticket.TicketDto;
import com.geniusee.cinema.dto.ticket.TicketIdentityDto;
import com.geniusee.cinema.service.ticket.TicketService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
class TicketControllerTest {
    @MockBean
    private TicketService ticketService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findById_NotFound() throws Exception {
        long id = 1;
        Mockito.when(ticketService.findById(id)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/tickets/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findById_Ok() throws Exception {
        long id = 1;
        Mockito.when(ticketService.findById(id)).thenReturn(sampleDto());

        mockMvc.perform(get("/tickets/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void create_BadRequest_ValidationException() throws Exception {
        checkBadRequestValidationException(post("/tickets"));
    }

    @Test
    void create_BadRequest_InvalidReference() throws Exception {
        checkBadRequestInvalidReference(post("/tickets"), ticketService.create(anyTicketDto()));
    }

    @Test
    void create_Ok() throws Exception {
        checkOk(post("/tickets"), ticketService.create(anyTicketDto()));
    }

    @Test
    void update_BadRequest_ValidationException() throws Exception {
        checkBadRequestValidationException(put("/tickets/1"));
    }

    @Test
    void update_BadRequest_InvalidReference() throws Exception {
        checkBadRequestInvalidReference(put("/tickets/1"), ticketService.update(anyTicketDto(), anyLong()));
    }

    @Test
    void update_Ok() throws Exception {
        checkOk(put("/tickets/1"), ticketService.update(anyTicketDto(), anyLong()));
    }

    @Test
    void delete_Ok() throws Exception {
        mockMvc.perform(delete("/tickets/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findAll_Ok() throws Exception {
        TicketIdentityDto dto = sampleDto();
        Page<TicketIdentityDto> samplePage = new PageImpl<>(Collections.singletonList(dto));
        Mockito.when(ticketService.findAll(any(), any())).thenReturn(samplePage);

        mockMvc.perform(get("/tickets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void findAll_InvalidSort() throws Exception {
        Mockito.when(ticketService.findAll(any(), any()))
                .thenThrow(PropertyReferenceException.class);

        mockMvc.perform(get("/tickets"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid sort fieldName"));
    }

    private TicketDto anyTicketDto() {
        return any(TicketDto.class);
    }

    private TicketIdentityDto sampleDto() {
        TicketIdentityDto dto = new TicketIdentityDto();
        dto.setId(1);
        dto.setMovieId(1);
        dto.setHall(1);
        dto.setPlace(1);
        dto.setPrice(100);
        return dto;
    }

    private RequestBuilder jsonBody(MockHttpServletRequestBuilder request, Object body) {
        return request
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject(body).toString());
    }

    private void checkBadRequestValidationException(MockHttpServletRequestBuilder request) throws Exception {
        TicketIdentityDto dto = sampleDto();
        dto.setPrice(-100);

        mockMvc.perform(jsonBody(request, dto))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.price").value("Price cannot be negative"));
    }

    private void checkBadRequestInvalidReference(MockHttpServletRequestBuilder request,
                                                 TicketIdentityDto mockMethodCall) throws Exception {
        String errorMessage = "fk_ticket_movie_id";
        TicketIdentityDto dto = sampleDto();
        Mockito.when(mockMethodCall).thenThrow(new DataIntegrityViolationException(errorMessage));


        mockMvc.perform(jsonBody(request, dto))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    private void checkOk(MockHttpServletRequestBuilder request, TicketIdentityDto mockMethodCall) throws Exception {
        TicketIdentityDto dto = sampleDto();
        Mockito.when(mockMethodCall).thenReturn(dto);

        mockMvc.perform(jsonBody(request, dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.movieId").value(dto.getMovieId()))
                .andExpect(jsonPath("$.price").value(dto.getPrice()));
    }
}