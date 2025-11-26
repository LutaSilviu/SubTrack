package com.proiect.subtrack.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/invoice")
@Tag(name = "Invoice", description = "Invoice related endpoints")
public class InvoiceController {

    @GetMapping
    @Operation(summary = "Invoice root", description = "Placeholder invoice endpoint")
    public ResponseEntity<Void> root(){
        return ResponseEntity.noContent().build();
    }
}
