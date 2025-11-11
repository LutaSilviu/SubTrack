package com.proiect.subtrack.mappers.impl;

import com.proiect.subtrack.domain.dto.PlanDto;
import com.proiect.subtrack.domain.entities.PlanEntity;
import com.proiect.subtrack.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlanMapperImpl implements Mapper<PlanEntity, PlanDto> {

    private final ModelMapper modelMapper;

    public PlanMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public PlanDto mapTo(PlanEntity planEntity) {
        return modelMapper.map(planEntity, PlanDto.class);
    }

    @Override
    public PlanEntity mapFrom(PlanDto planDto) {
        return modelMapper.map(planDto, PlanEntity.class);

    }
}
