package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
@Table(name="citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private LocalDateTime fecha;
    private String matricula;

    @ManyToOne
    @JoinColumn(name="id_cliente")
    private Cliente cliente;

    // Constructores
    public Cita(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "cliente=" + cliente.getNombre() +
                ", id=" + id +
                ", fecha=" + fecha +
                ", matricula='" + matricula + '\'' +
                '}';
    }
}