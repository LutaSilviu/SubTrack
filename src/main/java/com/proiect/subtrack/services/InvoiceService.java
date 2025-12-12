package com.proiect.subtrack.services;

import com.proiect.subtrack.domain.entities.InvoiceEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvoiceService {

    List<InvoiceEntity> getInvoiceForSubscription(Long subscriptionId);

    InvoiceEntity save(Long subscriptionId);
}
