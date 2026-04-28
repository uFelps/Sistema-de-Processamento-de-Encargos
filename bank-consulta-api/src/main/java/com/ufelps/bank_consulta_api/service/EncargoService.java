package com.ufelps.bank_consulta_api.service;

import com.ufelps.bank_consulta_api.dto.EncargoDto;
import com.ufelps.bank_consulta_api.mapper.EncargoMapper;
import com.ufelps.bank_consulta_api.repository.EncargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EncargoService {

    private final EncargoRepository encargoRepository;

    public Optional<EncargoDto> findById(UUID id) {
        return encargoRepository.findById(id)
                .map(EncargoMapper::toDto);
    }

    public Page<EncargoDto> findByNumeroConta(String numeroConta, Pageable pageable) {
        return encargoRepository.findByNumeroConta(numeroConta, pageable)
                .map(EncargoMapper::toDto);
    }
}
