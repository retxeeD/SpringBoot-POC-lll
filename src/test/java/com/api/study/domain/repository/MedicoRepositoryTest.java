package com.api.study.domain.repository;

import com.api.study.domain.DTO.DadosEndereco;
import com.api.study.domain.DTO.Especialidade;
import com.api.study.domain.DTO.medico.DadosCadastroMedico;
import com.api.study.domain.DTO.paciente.DadosCadastroPaciente;
import com.api.study.domain.entity.Consulta;
import com.api.study.domain.entity.Medico;
import com.api.study.domain.entity.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve devolver null quando unico medico cadastrado não esta disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1(){
        var dataTest = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@voll.med","123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@paciente.com", "42166595863");
        cadastrarConsulta(medico, paciente, dataTest);

        var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, dataTest);
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve devolver medico quando existe medico disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2(){
        var dataTest = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@voll.med","123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, dataTest);
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime date){
        var consulta = new Consulta(null, medico, paciente, date);
        entityManager.persist(consulta);
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        var medico = new Medico(dadosCadastroMedico(nome, email, crm, especialidade));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(dadosCadastroPaciente(nome, email, cpf));
        entityManager.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade){
        return new DadosCadastroMedico(
                nome,
                email,
                "00000000000",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosCadastroPaciente(String nome, String email, String cpf){
        return new DadosCadastroPaciente(
                nome,
                email,
                cpf,
                "00000000000",
                Especialidade.CARDIOLOGIA,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco(){
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "São Paulo",
                "SP",
                "Esquina com rua Z",
                "18"
        );
    }

}