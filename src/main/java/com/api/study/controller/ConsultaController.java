package com.api.study.controller;

import com.api.study.domain.DTO.consulta.DadosCancelamentoConsulta;
import com.api.study.domain.DTO.consulta.DadosAgendamentoConsulta;
import com.api.study.domain.service.AgendaDeConsultas;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = service.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        service.cancelarConsulta(dados);
        return ResponseEntity.noContent().build();
    }
}
