package com.api.study.domain.DTO.consulta;

import lombok.Getter;

@Getter
public enum MotivoCancelamentoEnum {
    DESISTENCIA("Paciente Desistiu."),
    CANCELAMENTO_MEDICO("Medico cancelou."),
    CANCELAMENTO_PACIENTE("Paciente cancelou.");

    private final String motivo;

    MotivoCancelamentoEnum(String motivo){
        this.motivo = motivo;
    }
}
