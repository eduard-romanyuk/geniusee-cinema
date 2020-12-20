package com.geniusee.cinema.controller.movie;

import com.geniusee.cinema.dto.MovieDto;
import com.geniusee.cinema.dto.MovieIdentityDto;
import com.geniusee.cinema.service.movie.MovieService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
class MovieControllerTest {
    @MockBean
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;

    private static final String emptyBodyErrorMessage = "Request body is mandatory";
    private static final String emptyNameErrorMessage = "Name is mandatory";

    @Test
    void findById_NotFound() throws Exception {
        long id = 1;
        Mockito.when(movieService.findById(id)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/movies/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findById_Ok() throws Exception {
        long id = 1;
        Mockito.when(movieService.findById(id)).thenReturn(sampleDto());

        mockMvc.perform(get("/movies/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void create_BadRequest_EmptyBody() throws Exception {
        checkBadRequestEmptyBody(post("/movies"));
    }

    @Test
    void create_BadRequest_ValidationException() throws Exception {
        checkBadRequestValidationException(post("/movies"));
    }

    @Test
    void create_Ok() throws Exception {
        checkOk(post("/movies"), movieService.create(any(MovieDto.class)));
    }

    @Test
    void update_BadRequest_EmptyBody() throws Exception {
        checkBadRequestEmptyBody(put("/movies/1"));
    }

    @Test
    void update_BadRequest_ValidationException() throws Exception {
        checkBadRequestValidationException(put("/movies/1"));
    }

    @Test
    void update_Ok() throws Exception {
        checkOk(put("/movies/1"), movieService.update(any(MovieDto.class), anyLong()));
    }

    @Test
    void delete_Ok() throws Exception {
        mockMvc.perform(delete("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private MovieIdentityDto sampleDto() {
        MovieIdentityDto dto = new MovieIdentityDto();
        dto.setId(1);
        dto.setName("Sample name");
        dto.setDurationMinutes(60);
        return dto;
    }

    private RequestBuilder jsonBody(MockHttpServletRequestBuilder request, Object body) {
        return request
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject(body).toString());
    }

    private void checkBadRequestEmptyBody(RequestBuilder request) throws Exception {
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(emptyBodyErrorMessage));
    }

    private void checkBadRequestValidationException(MockHttpServletRequestBuilder request) throws Exception {
        MovieIdentityDto dto = sampleDto();
        dto.setName(null);

        mockMvc.perform(jsonBody(request, dto))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value(emptyNameErrorMessage));
    }

    private void checkOk(MockHttpServletRequestBuilder request, MovieIdentityDto mockMethodCall) throws Exception {
        MovieIdentityDto dto = sampleDto();
        Mockito.when(mockMethodCall).thenReturn(dto);

        mockMvc.perform(jsonBody(request, dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }
}