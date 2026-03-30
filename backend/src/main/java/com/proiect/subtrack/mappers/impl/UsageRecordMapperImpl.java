package com.proiect.subtrack.mappers.impl;

import com.proiect.subtrack.domain.dto.UsageRecordDto;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsageRecordMapperImpl implements Mapper<UsageRecordEntity, UsageRecordDto> {

    private final ModelMapper modelMapper;

    public UsageRecordMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public UsageRecordDto mapTo(UsageRecordEntity usageRecordEntity) {
        return modelMapper.map(usageRecordEntity, UsageRecordDto.class);
    }

    @Override
    public UsageRecordEntity mapFrom(UsageRecordDto usageRecordDto) {
        return modelMapper.map(usageRecordDto, UsageRecordEntity.class);
    }
}
