let stompClient = null;

function connect() {
    let socket = new SockJS('/chat-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        // Suscribirse al canal de notificaciones para el usuario actual
        stompClient.subscribe('/topic/notifications/' + currentUserId, function(notification) {
            let notif = JSON.parse(notification.body);
            showNotification(notif);
        });
    });
}

function sendMessage() {
    let messageInput = document.getElementById('message-input');
    let message = messageInput.value;
    if (message && stompClient && roomId) {
        let chatMessage = {
            message: message,
            user: currentUserId
        };
        stompClient.send("/app/chat/message/" + roomId, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}


function showMessage(message) {
    let messageElement = document.createElement('div');
    messageElement.innerHTML = message.user + ': ' + message.message;
    document.getElementById('chat-messages').appendChild(messageElement);
}

function connectToChatRoom(room) {
    roomId = room; // Actualizar la variable roomId
    stompClient.subscribe('/topic/' + roomId, function(chatMessage) {
        showMessage(JSON.parse(chatMessage.body));
    });
}

function searchConversation(currentUserId, recipientId) {
    // Crear una conversación con el usuario seleccionado
    var conversation = {
        'senderId': currentUserId,
        'recipientId': recipientId
    };

    // Enviar la solicitud de conversación al servidor
    stompClient.send('/app/startConversation', {}, JSON.stringify(conversation));

    // Mostrar el contenedor de conversación
    document.getElementById('chat-container').style.display = 'block';
}

function createNewConversation(currentUserId, recipientId) {
    // Realiza la solicitud AJAX para crear una nueva conversación
    $.ajax({
        type: "POST",
        url: "/api/createConversation",
        data: { userId: currentUserId, otherUserId: recipientId },
        success: function(room) {
            console.log(`Created new chat room ${room}`);
            // Conecta al usuario a la nueva sala de chat
            connectToChatRoom(room);
        },
        error: function(xhr, status, error) {
            console.error("Error al crear conversación:", error);
        }
    });
}

// Mostrar notificaciones
function showNotification(notification) {
    const notificationList = document.getElementById('notification-list');
    const listItem = document.createElement('li');
    listItem.className = 'list-group-item';
    listItem.textContent = `${notification.senderName}: ${notification.message}`;
    notificationList.appendChild(listItem);
}

// Conectar al WebSocket cuando se carga la página
window.onload = connect;
