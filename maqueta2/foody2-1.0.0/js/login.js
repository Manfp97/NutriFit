// Función para registrar un usuario
function register(username, password) {
    // Aquí puedes implementar la lógica para almacenar el usuario y la contraseña
    // Podrías utilizar localStorage, sessionStorage, una base de datos, etc.
    // Por simplicidad, en este ejemplo solo almacenaremos el usuario y la contraseña en cookies
    
    setCookie('username', username, '/');
    setCookie('password', password, '/');
}

// Función para iniciar sesión
function login(username, password) {
    // Aquí podrías verificar las credenciales del usuario, por ejemplo, consultando una base de datos
    // Por simplicidad, en este ejemplo solo comprobaremos si las cookies coinciden con las credenciales
    
    if (getCookie('username') === username && getCookie('password') === password) {
        // Las credenciales son válidas, sesión iniciada
        return true;
    } else {
        // Las credenciales son incorrectas
        return false;
    }
}

// Función para verificar si el usuario ha iniciado sesión
function isLoggedIn() {
    // Verifica si existen cookies de sesión
    if (getCookie('username') && getCookie('password')) {
        // Cookies encontradas, sesión iniciada
        return true;
    } else {
        // Cookies no encontradas, sesión no iniciada
        return false;
    }
}

// Función para cerrar sesión
function logout() {
    // Elimina las cookies de sesión
    deleteCookie('username');
    deleteCookie('password');
}

// Función para establecer una cookie
function setCookie(name, value, path, options = {}) {
    options = {
        path: path,
        ...options
    }; 
    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value)
    for (let optionKey in options) {
        updatedCookie += "; " + optionKey
        let optionValue = options[optionKey]
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue
        }
    }
    document.cookie = updatedCookie;
}

// Función para obtener el valor de una cookie
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

// Función para eliminar una cookie
function deleteCookie(name) {
    setCookie(name, '', '/', { expires: new Date(0) });
}
