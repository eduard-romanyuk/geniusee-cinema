package com.geniusee.cinema.mapper;

import java.util.List;

public interface ToEntityMapper<DTO, ENTITY> {

    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntity(List<DTO> dtos);

}
