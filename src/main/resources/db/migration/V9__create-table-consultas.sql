CREATE TABLE consultas (
    id bigint NOT NULL auto_increment,
    medico_id bigint NOT NULL,
    paciente_id bigint NOT NULL,
    data datetime NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_consultas_medicos_id FOREIGN KEY (medico_id) REFERENCES medicos(id),
    CONSTRAINT fk_consultas_pacientes_id FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);