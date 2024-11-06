package com.api.study.domain.DTO.consulta.validacoesAgendamentoConsulta;

import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {
    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferençaMinutos = Duration.between(agora, dataConsulta).toMinutes();
        if (diferençaMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com mais de 30 minutos de antecedência.");
        }
    }
}
