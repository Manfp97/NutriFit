package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    // Puedes añadir métodos personalizados aquí si es necesario
}
