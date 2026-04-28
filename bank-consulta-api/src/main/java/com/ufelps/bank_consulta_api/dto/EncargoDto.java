package com.ufelps.bank_consulta_api.dto;

import com.ufelps.bank_consulta_api.model.StatusEncargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncargoDto {
    private UUID idLancamento;
    private String numeroConta;
    private String tipoLancamento;
    private BigDecimal valor;
    private LocalDate dataEntrada;
    private StatusEncargo statusEncargo;
}
