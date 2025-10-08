package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.InvoiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Long> {
}
