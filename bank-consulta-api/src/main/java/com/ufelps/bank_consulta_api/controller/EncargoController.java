package com.ufelps.bank_consulta_api.controller;

import com.ufelps.bank_consulta_api.dto.EncargoDto;
import com.ufelps.bank_consulta_api.dto.PageDto;
import com.ufelps.bank_consulta_api.service.EncargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/encargos")
@RequiredArgsConstructor
public class EncargoController {

    private final EncargoService encargoService;

    @GetMapping("/{id}")
    public ResponseEntity<EncargoDto> getById(@PathVariable UUID id) {
        return encargoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conta/{numeroConta}")
    public ResponseEntity<Page<EncargoDto>> getByNumeroConta(@PathVariable String numeroConta, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<EncargoDto> encargos = encargoService.findByNumeroConta(numeroConta, pageable);
        
        if (encargos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(encargos);
    }
}
