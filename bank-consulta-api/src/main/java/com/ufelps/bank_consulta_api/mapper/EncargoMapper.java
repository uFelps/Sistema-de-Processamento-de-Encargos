package com.ufelps.bank_consulta_api.mapper;

import com.ufelps.bank_consulta_api.dto.EncargoDto;
import com.ufelps.bank_consulta_api.model.Encargo;

public class EncargoMapper {

    private EncargoMapper() {
    }

    public static EncargoDto toDto(Encargo entity) {
        if (entity == null) {
            return null;
        }

        return new EncargoDto(
                entity.getIdLancamento(),
                entity.getNumeroConta(),
                entity.getTipoLancamento(),
                entity.getValor(),
                entity.getDataEntrada(),
                entity.getStatusEncargo()
        );
    }
}
