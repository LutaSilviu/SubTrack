package com.proiect.subtrack.mappers.impl;

import com.proiect.subtrack.domain.dto.InvoiceDto;
import com.proiect.subtrack.domain.entities.InvoiceEntity;
import com.proiect.subtrack.mappers.Mapper;
import org.modelmapper.ModelMapper;

public class InvoiceMapperImpl implements Mapper<InvoiceEntity, InvoiceDto> {

    private final ModelMapper modelMapper;

    public InvoiceMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public InvoiceDto mapTo(InvoiceEntity invoiceEntity) {
        return modelMapper.map(invoiceEntity, InvoiceDto.class);
    }

    @Override
    public InvoiceEntity mapFrom(InvoiceDto invoiceDto) {
        return modelMapper.map(invoiceDto, InvoiceEntity.class);
    }
}
