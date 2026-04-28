package com.ufelps.bank_consulta_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TB_ENCARGO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Encargo {

    @Id
    private UUID idLancamento;
    private String numeroConta;
    private String tipoLancamento;
    private BigDecimal valor;
    private LocalDate dataEntrada;
    @Enumerated(EnumType.STRING)
    private StatusEncargo statusEncargo;
}
