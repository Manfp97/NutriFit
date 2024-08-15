let stompClient = null;

function connect() {
    let socket = new SockJS('/chat-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        // Suscripción al canal de chat
        stompClient.subscribe('/topic/chat/' + roomId, function(chatMessage) {
            let message = JSON.parse(chatMessage.body);
            showMessage(message);
        });

        // Suscripción al canal de notificaciones
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

function showNotification(notification) {
    const notificationList = document.getElementById('notification-list');
    const listItem = document.createElement('li');
    listItem.className = 'list-group-item';
    listItem.textContent = `${notification.senderName}: ${notification.message}`;
    notificationList.appendChild(listItem);
}

// Conectar al WebSocket cuando se carga la página
window.onload = function() {
    connect();
};
