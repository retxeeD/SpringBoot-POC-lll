package com.api.study.domain.DTO.consulta.validacoesAgendamentoConsulta;

import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.ValidacaoException;
import com.api.study.domain.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Medico já possui outra consulta para este horario.");
        }
    }
}
