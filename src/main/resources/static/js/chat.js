let socket = new SockJS('http://localhost:8091/chats');
let stompClient = null;

function connect() {
    console.log('Intentando conectar...');
    socket = new SockJS('/chats');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Conexión exitosa');
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/canal1', function (greeting) {
            console.log('Mensaje recibido:', greeting);
            // Asegúrate de que el cuerpo del mensaje esté en formato JSON
            let message = JSON.parse(greeting.body);
            showGreeting(message.body);  // Mostrar el contenido correcto
        });
    }, function(error) {
        console.error('Error en la conexión:', error);
        setConnected(false);
    });
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Desconectado");
}

function sendName() {
    if (stompClient !== null && stompClient.connected) {
        stompClient.send("/app/chat", {}, JSON.stringify({'body': $("#name").val()}));
    } else {
        console.error('No conectado al servidor WebSocket');
    }
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});

// Manejo de errores adicional
window.onerror = function(message, source, lineno, colno, error) {
    console.error('Error no capturado:', message, 'en', source, 'línea', lineno);
    return true;
};