package com.api.study.domain.DTO.consulta.validacoesCancelamentoConsulta;

import com.api.study.domain.DTO.consulta.DadosCancelamentoConsulta;
import com.api.study.domain.ValidacaoException;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioDeAntecedenciaCancelamento implements ValidadorCancelamentoDeConsulta{
    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var dataConsulta = dados.consulta().getData();
        var agora = LocalDateTime.now();
        var diferençaMinutos = Duration.between(agora, dataConsulta).toHours();
        if (diferençaMinutos < 24){
            throw new ValidacaoException("Consulta so pode ser cancelada com mais de 24 horas de antecedência.");
        }
    }
}
