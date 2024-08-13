package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "from")
    private String from;

    @Column(name = "text")
    private String text;

    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now(); // Almacena la hora en que se envió el mensaje
}
