package com.ufelps.bank_consulta_api.repository;

import com.ufelps.bank_consulta_api.model.Encargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EncargoRepository extends JpaRepository<Encargo, UUID> {
    Page<Encargo> findByNumeroConta(String numeroConta, Pageable pageable);
}
