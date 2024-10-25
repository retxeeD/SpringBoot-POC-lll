package com.api.study.domain.DTO.consulta.validacoesAgendamentoConsulta;

import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.ValidacaoException;
import com.api.study.domain.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if (dados.idMedico() == null){
            return;
        }
        if (repository.findAtivoById(dados.idMedico())){
            throw new ValidacaoException("Medico informado deve estar ativo para agendar consulta.");
        }
    }
}
