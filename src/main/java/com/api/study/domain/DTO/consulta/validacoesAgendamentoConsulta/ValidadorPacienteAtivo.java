package com.api.study.domain.DTO.consulta.validacoesAgendamentoConsulta;

import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.ValidacaoException;
import com.api.study.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if (!repository.findAtivoById(dados.idPaciente())){
            throw new ValidacaoException("Paciente informado deve estar ativo para agendar consulta.");
        }
    }
}
