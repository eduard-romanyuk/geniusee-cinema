package com.geniusee.cinema.mapper;

import org.mapstruct.MappingTarget;

public interface UpdateEntityMapper<DTO, ENTITY> {
    void updateEntityFromDto(DTO dto, @MappingTarget ENTITY entity);
}
