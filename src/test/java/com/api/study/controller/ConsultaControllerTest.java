package com.api.study.controller;

import com.api.study.domain.DTO.Especialidade;
import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.DTO.consulta.DadosDetalhamentoConsulta;
import com.api.study.domain.service.AgendaDeConsultas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsulta;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsulta;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;


    @Test
    @WithMockUser
    @DisplayName("VALIDACAO DE REQUISICAO COM SUCESSO")
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l,5l, data);
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsulta.write(
                                        new DadosAgendamentoConsulta(2l, 5l, data, especialidade )
                                ).getJson())
                )
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalhamentoConsulta.write(
                dadosDetalhamento
        ).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser
    @DisplayName("VALIDACAO DE ERRO 400 QUANDO NAO ENVIO O BODY NA REQUISICAO")
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}