package org.example.uam_app1.modelo;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter

public class Carrera {
    @Id
    private int id;
    @Column(name = "Nombre", length = 30, nullable = false)
    @Required(message = "El nombre es obligatorio")
    private String nombre;
    private String descripcion;
    @Column(name = "Facultad", length = 30, nullable = false)
    @Required(message = "La facultad es obligatoria")
    private String facultad;
}
