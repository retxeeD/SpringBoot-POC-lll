package com.api.study.domain.service;

import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.DTO.consulta.DadosCancelamentoConsulta;
import com.api.study.domain.DTO.consulta.DadosDetalhamentoConsulta;
import com.api.study.domain.DTO.consulta.validacoesAgendamentoConsulta.ValidadorAgendamentoDeConsulta;
import com.api.study.domain.DTO.consulta.validacoesCancelamentoConsulta.ValidadorCancelamentoDeConsulta;
import com.api.study.domain.ValidacaoException;
import com.api.study.domain.entity.Consulta;
import com.api.study.domain.entity.Medico;
import com.api.study.domain.repository.ConsultaRepository;
import com.api.study.domain.repository.MedicoRepository;
import com.api.study.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private List<ValidadorAgendamentoDeConsulta> validadoresAgendamentoConsulta;
    private List<ValidadorCancelamentoDeConsulta> validadorCancelamentoConsultas;

    @Autowired
    public AgendaDeConsultas(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, List<ValidadorAgendamentoDeConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadoresAgendamentoConsulta = validadores;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("ID do paciente informado não existe.");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("ID do medico informado não existe.");
        }

        validadoresAgendamentoConsulta.forEach(validador -> validador.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if (medico == null){
            throw new ValidacaoException("Não existe médico desta especialidade disponível nesta data.");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando médico não é informado.");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelarConsulta(DadosCancelamentoConsulta dados) {
        validadorCancelamentoConsultas.forEach(validador -> validador.validar(dados));
        consultaRepository.deleteById(dados.consulta().getId());
    }

}
