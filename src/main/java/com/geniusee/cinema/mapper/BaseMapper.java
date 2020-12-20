package com.geniusee.cinema.mapper;

import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

public interface BaseMapper<DTO, ENTITY> {

    DTO toDto(ENTITY entity);

    List<DTO> toDto(List<? extends ENTITY> entities);

    @InheritInverseConfiguration
    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntity(List<DTO> dtos);

}
