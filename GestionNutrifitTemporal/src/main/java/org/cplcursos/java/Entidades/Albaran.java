
package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="albaranes")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Albaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime fecha;
    private Float importe;

    /*
         @ManyToOne hace que JPA defina una relación de muchos a uno entre Albaran -la parte de "muchos" puesto que es
             la que incluye la anotación @ManyToOne- y Cliente -la parte de "uno".

             Esta relación indica que varios albaranes pueden tener el mismo cliente.

             El CASO DE USO sería que un cliente visita varias veces el taller y se le generan varios albaranes distintos.

         @JoinColumn indica qué columna de la parte de "muchos" se utilizará para la clave foránea. Si no se indica nada,
             JPA creará una columna del mismo nombre de la propiedad añadiendo un "_id" al final.

             Así mismo JPA crea la clave foránea (en la tabla "albaranes") apuntando a la tabla "clientes" con los campos
             "id_cliente" e "id" -clave primaria de "clientes"- respectivamente
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

}
