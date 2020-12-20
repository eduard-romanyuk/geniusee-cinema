package com.geniusee.cinema.mapper;

import java.util.List;

public interface ToDtoMapper<DTO, ENTITY> {
    DTO toDto(ENTITY entity);

    List<DTO> toDto(List<? extends ENTITY> entities);
}
