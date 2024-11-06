package com.api.study.domain.repository;

import com.api.study.domain.DTO.Especialidade;
import com.api.study.domain.entity.Medico;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT M from Medico M
            WHERE M.ativo = true
            AND M.especialidade = :especialidade
            AND M.id NOT IN (
                SELECT C.medico.id from Consulta C
                WHERE C.data = :data
            )
            order by rand()
            LIMIT 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            SELECT M.ativo from Medico M
            WHERE M.id = :idMedico
            """)
    boolean findAtivoById(Long idMedico);
}
