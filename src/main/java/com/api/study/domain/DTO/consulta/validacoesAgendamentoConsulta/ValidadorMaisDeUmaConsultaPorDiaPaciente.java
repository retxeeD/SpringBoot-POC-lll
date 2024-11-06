package com.api.study.domain.DTO.consulta.validacoesAgendamentoConsulta;

import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.ValidacaoException;
import com.api.study.domain.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMaisDeUmaConsultaPorDiaPaciente implements ValidadorAgendamentoDeConsulta {

    @Autowired
    ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        if (repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario)){
            throw new ValidacaoException("Paciente ja possui uma consulta agendada nesse dia.");
        }
    }
}