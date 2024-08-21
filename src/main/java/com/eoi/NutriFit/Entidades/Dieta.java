package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * La clase Dieta representa la entidad que almacena información sobre una dieta,
 * incluyendo detalles como el nombre, objetivos, categoría, descripción, y recursos multimedia asociados.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter y un constructor sin argumentos, respectivamente.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>dieta</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="dieta")
public class Dieta {

    /**
     * Identificador único de la entidad Dieta.
     * Este campo es autogenerado con la estrategia AUTO y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Nombre de la dieta. Este campo almacena el nombre asignado a la dieta.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Objetivos de la dieta. Este campo describe los objetivos que la dieta pretende alcanzar.
     */
    @Column(name = "objetivos")
    private String objetivos;

    /**
     * Categoría de la dieta, como puede ser pérdida de peso, aumento de masa muscular, etc.
     */
    @Column(name = "categoria")
    private String categoria;

    /**
     * Descripción detallada de la dieta, que puede incluir información sobre el plan alimenticio, restricciones, etc.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Recursos multimedia asociados con la dieta, tales como imágenes, videos o documentos.
     * Este campo se almacena como un arreglo de bytes (BLOB).
     */
    @Lob
    @Column(name = "recursos_multimedia")
    private byte[] recursos_multimedia;

    /**
     * Planificación de la frecuencia de la dieta, que indica cómo se debe seguir el plan alimenticio (diario, semanal, etc.).
     */
    @Column(name = "planificacion_frecuencia")
    private String planificacion_frecuencia;

    /**
     * Fecha en la que se creó la dieta. Se almacena como un objeto <code>LocalDate</code>.
     */
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    /**
     * Conjunto de detalles específicos asociados con esta dieta.
     * Esta relación uno a muchos está mapeada por el campo <code>dieta</code> en la entidad <code>DetallesDieta</code>.
     */
    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL)
    private Set<DetallesDieta> detalles_dietas;

    /**
     * Conjunto de registros de progresión asociados con esta dieta.
     * Esta relación uno a muchos está mapeada por el campo <code>dieta</code> en la entidad <code>ProgresionDieta</code>.
     */
    @OneToMany(mappedBy = "dieta")
    private Set<ProgresionDieta> progresionDietas;

    /**
     * Constructor para crear una instancia de Dieta con los parámetros especificados.
     *
     * @param nombre Nombre de la dieta.
     * @param objetivos Objetivos de la dieta.
     * @param categoria Categoría de la dieta.
     * @param recursosMultimedia Recursos multimedia asociados con la dieta.
     * @param planificacionFrecuencia Planificación de la frecuencia de la dieta.
     * @param descripcion Descripción detallada de la dieta.
     */
    public Dieta(String nombre, String objetivos, String categoria, byte[] recursosMultimedia,
                 String planificacionFrecuencia, String descripcion) {
        this.nombre = nombre;
        this.objetivos = objetivos;
        this.categoria = categoria;
        this.recursos_multimedia = recursosMultimedia;
        this.planificacion_frecuencia = planificacionFrecuencia;
        this.descripcion = descripcion;
    }

    /**
     * Relación muchos a uno con la entidad <code>DetallesDieta</code>.
     * Representa el detalle de la dieta de contacto asociada con esta dieta.
     *
     * La relación se mapea mediante la columna <code>idcontactodieta</code> y el nombre de la clave foránea <code>Fk_contactodieta_dieta</code>.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcontactodieta", foreignKey = @ForeignKey(name = "Fk_contactodieta_dieta"))
    private DetallesDieta contactoDieta;
}
