package com.api.study.domain.DTO.consulta;

import com.api.study.domain.entity.Consulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        Consulta consulta,

        @NotBlank
        String motivoDoCancelamento) {
}
