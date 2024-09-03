package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase <code>SesionClientes</code> representa la entidad que mapea la relación entre los clientes y sus sesiones,
 * ya sean colectivas o individuales. Esta entidad se utiliza para gestionar la participación de los clientes en
 * diferentes tipos de sesiones y, opcionalmente, para asignar un empleado responsable.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA y será mapeada a una
 * tabla en la base de datos. La tabla correspondiente en la base de datos se denomina <code>sesion_clientes</code>.</p>
 *
 * <p>Se utilizan las anotaciones de Lombok como <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter, y un constructor sin argumentos.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="sesion_clientes")
public class SesionClientes {

    /**
     * Identificador único para cada instancia de sesión de cliente.
     * Este campo es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Sesión colectiva a la cual está asociado el cliente.
     * Relación Many-to-One con la entidad <code>SesionColectiva</code>.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sesion_colectiva", foreignKey = @ForeignKey(name = "fk_colectiva_cliente"))
    private SesionColectiva sesionColectiva;

    /**
     * Cliente que participa en la sesión.
     * Relación Many-to-One con la entidad <code>Cliente</code>.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_cliente_sesion"))
    private Cliente cliente;

    /**
     * Empleado responsable de la sesión.
     * Relación Many-to-One con la entidad <code>Cliente</code> que actúa como empleado.
     * Este campo puede ser ajustado en el nombre si es necesario.
     */
    @ManyToOne
    @JoinColumn(name = "empleado_id") // ajusta el nombre si es necesario
    private Cliente empleado;

}
