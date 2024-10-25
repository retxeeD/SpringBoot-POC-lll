package com.api.study.domain.DTO.consulta.validacoesCancelamentoConsulta;

import com.api.study.domain.DTO.consulta.DadosCancelamentoConsulta;
import com.api.study.domain.DTO.consulta.MotivoCancelamentoEnum;
import com.api.study.domain.ValidacaoException;

public class ValidadorMotivoDoCancelamento implements ValidadorCancelamentoDeConsulta{
    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var motivo = dados.motivoDoCancelamento();
        try{
            MotivoCancelamentoEnum.valueOf(motivo);
        }catch (Exception ex){
            throw new ValidacaoException("Motivo do cancelamento informado nao é valido.");
        }
    }
}
