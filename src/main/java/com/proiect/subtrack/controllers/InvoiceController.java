package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.CreateInvoiceRequestBody;
import com.proiect.subtrack.domain.dto.InvoiceDto;
import com.proiect.subtrack.domain.entities.InvoiceEntity;
import com.proiect.subtrack.mappers.impl.InvoiceMapperImpl;
import com.proiect.subtrack.services.InvoiceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/invoice")
@Tag(name = "Invoice", description = "Invoice related endpoints")
@RequiredArgsConstructor
public class InvoiceController {

    final private InvoiceService invoiceService;
    final private InvoiceMapperImpl invoiceMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get invoices for subscription")
    public ResponseEntity<@NonNull List< InvoiceDto>> getInvoicesForSubscription(@PathVariable Long id){
        log.debug("Fetching invoices for subscription ID: {}", id);

        List<InvoiceEntity> invoiceForSubscription = invoiceService.getInvoiceForSubscription(id);

        List<InvoiceDto> invoiceDtos = invoiceForSubscription.stream().map(invoiceMapper::mapTo).toList();
        log.info("Retrieved {} invoices for subscription ID: {}", invoiceDtos.size(), id);
        return ResponseEntity.ok(invoiceDtos);
    }


    @PostMapping
    @Operation(summary = "Post an invoice")
    public  ResponseEntity<InvoiceDto> postInvoice(@RequestBody CreateInvoiceRequestBody requestBody){
        log.info("Creating invoice for subscription ID: {}", requestBody.getSubscriptionId());

        InvoiceEntity savedInvoice = invoiceService.save(requestBody.getSubscriptionId());
        InvoiceDto savedDto = invoiceMapper.mapTo(savedInvoice);

        log.info("Invoice created successfully with total: {}", savedDto.getTotal());
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
}
