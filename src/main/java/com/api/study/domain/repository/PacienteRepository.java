package com.api.study.domain.repository;

import com.api.study.domain.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT P.ativo from Paciente P
            WHERE P.id = :idPaciente
            """)
    boolean findAtivoById(Long idPaciente);
}
