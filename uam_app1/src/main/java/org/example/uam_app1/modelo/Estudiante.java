package org.example.uam_app1.modelo;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter

public class Estudiante {
    @Id
    private String cif;
    @Column(name = "Nombres", length = 30, nullable = false)
    @Required(message = "El nombre es obligatorio")
    private String nombre;
    @Column(name = "Apellidos", length = 30, nullable = false)
    @Required(message = "Los apellidos son obligatorios")
    private String apellidos;
    LocalDate fechaNac;
}
