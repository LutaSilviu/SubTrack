package com.proiect.subtrack.mappers.impl;

import com.proiect.subtrack.domain.dto.SubscriptionDto;
import com.proiect.subtrack.domain.entities.SubscriptionEntity;
import com.proiect.subtrack.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapperImpl implements Mapper<SubscriptionEntity, SubscriptionDto> {

    private final ModelMapper modelMapper;

    public SubscriptionMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public SubscriptionDto mapTo(SubscriptionEntity subscriptionEntity) {
        return modelMapper.map(subscriptionEntity, SubscriptionDto.class);
    }

    @Override
    public SubscriptionEntity mapFrom(SubscriptionDto subscriptionDto) {
        return modelMapper.map(subscriptionDto, SubscriptionEntity.class);
    }
}
