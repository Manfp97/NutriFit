package com.eoi.NutriFit.chat;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatRoomService {
    private Map<String, Set<String>> roomUsers = new ConcurrentHashMap<>();

    public String createChatRoomId(String user1, String user2, String currentUserId) {
        String roomId = UUID.randomUUID().toString();
        Set<String> users = new HashSet<>(Arrays.asList(user1, user2, currentUserId));
        roomUsers.put(roomId, users);
        return roomId;
    }

    public boolean hasAccess(String roomId, String user) {
        return roomUsers.containsKey(roomId) && roomUsers.get(roomId).contains(user);
    }
}
