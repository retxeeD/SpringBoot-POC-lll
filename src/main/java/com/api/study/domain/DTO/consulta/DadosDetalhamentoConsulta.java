package com.api.study.domain.DTO.consulta;

import com.api.study.domain.entity.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta dados) {
        this(dados.getId(), dados.getMedico().getId(), dados.getPaciente().getId(), dados.getData());
    }
}
