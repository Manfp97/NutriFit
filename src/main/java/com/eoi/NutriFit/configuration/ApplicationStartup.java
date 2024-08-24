package com.eoi.NutriFit.configuration;

import com.eoi.NutriFit.Entidades.*;
import com.eoi.NutriFit.Repositorios.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javassist.bytecode.StackMapTable.NULL;

/**
 * Clase que se ejecuta al iniciar la aplicación. Implementa {@link ApplicationListener}
 * para escuchar el evento {@link ApplicationReadyEvent}, indicando que la aplicación
 * está lista para recibir solicitudes.
 *
 * <p>
 * Al implementar {@link ApplicationListener}, esta clase puede reaccionar a eventos específicos
 * del ciclo de vida de la aplicación. En este caso, estamos escuchando el evento
 * {@link ApplicationReadyEvent}, que se dispara cuando la aplicación ha completado el
 * proceso de arranque y está lista para servir peticiones.
 * </p>
 *
 * <p>
 * Esta clase se utiliza para inicializar datos en la base de datos, como la creación de un usuario
 * predeterminado al inicio de la aplicación.
 * </p>
 *
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final UsuarioRepository usuarioRepository;

    private final RolesRepo rolesRepo;

    private final Environment env;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EntrenamientoRepo entrenamientoRepo;
    private final ProductoRepo productoRepo;
    private final ProveedoresRepo proveedoresRepo;
    private final DietaRepo dietaRepo;

    /**
     * Constructor de la clase que recibe un {@link UsuarioRepository} para interactuar con la base de datos.
     *
     * @param userRepository el repositorio de usuarios que se utilizará para guardar los datos del usuario.
     * @param rolesRepo
     * @param env
     * @param entrenamientoRepo
     * @param productoRepo
     * @param proveedoresRepo
     * @param dietaRepo
     */
    public ApplicationStartup(UsuarioRepository userRepository, RolesRepo rolesRepo, Environment env, BCryptPasswordEncoder bCryptPasswordEncoder, EntrenamientoRepo entrenamientoRepo, ProductoRepo productoRepo, ProveedoresRepo proveedoresRepo, DietaRepo dietaRepo) {
        this.usuarioRepository = userRepository;
        this.rolesRepo = rolesRepo;
        this.env = env;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.entrenamientoRepo = entrenamientoRepo;
        this.productoRepo = productoRepo;
        this.proveedoresRepo = proveedoresRepo;
        this.dietaRepo = dietaRepo;
    }

    /**
     * Este método se ejecuta tan pronto como sea posible para indicar que
     * la aplicación está lista para atender solicitudes.
     *
     * <p>
     * En este método se crea un usuario predeterminado y se guarda en la base de datos
     * utilizando el {@link UsuarioRepository}. Este enfoque permite inicializar datos críticos
     * o de prueba en la base de datos automáticamente cuando la aplicación se inicia por primera vez.
     * </p>
     *
     * @param event el evento que indica que la aplicación está lista.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        //Hay que cargar datos ??
        String db_initialize = env.getProperty("execution.mode");
        //Carga de datos
        if (Objects.equals(db_initialize, "1")){
            altaRoles();
            altaUsuarios();
            altaEntrenamientos();
            altaProductos();
            altaProveedores();
            altaDietas();
        }
    }

    //Metodo para cargar roles
    public  void altaRoles(){
        Roles roles = new Roles();
        roles.setNombreRol("ROLE_ADMIN");
        Roles srole = rolesRepo.save(roles);

        Roles roles1 = new Roles();
        roles1.setNombreRol("ROLE_ANONIMOUS");
        Roles srole1 = rolesRepo.save(roles1);

        Roles roles2 = new Roles();
        roles2.setNombreRol("ROLE_USER");
        Roles srole2 = rolesRepo.save(roles2);

        Roles roles3 = new Roles();
        roles3.setNombreRol("ROLE_EMPLEADO");
        Roles srole3 = rolesRepo.save(roles3);

        Roles roles4 = new Roles();
        roles4.setNombreRol("ROLE_ENTRENADOR");
        Roles srole4 = rolesRepo.save(roles4);

        Roles roles5 = new Roles();
        roles5.setNombreRol("ROLE_NUTRICIONISTA");
        Roles srole5 = rolesRepo.save(roles5);

    }
    //Metodo para cargar usuarios
    public  void altaUsuarios(){
        // Crea un usuario
        Usuario usuario = new Usuario();
        usuario.setUsername("administrador");
        usuario.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuario.setActivo(true);
        usuario.setRol(rolesRepo.findByNombreRol("ROLE_ADMIN"));

        // Crea un detalle de usuario
        DetalleUsuario detalleUsuario = new DetalleUsuario();
        detalleUsuario.setNombre("Nombre del usuario");
        detalleUsuario.setApellidos("Apellidos del usuario");
        detalleUsuario.setDireccion("Direccion del usuario");
        detalleUsuario.setDni("DNI del usuario".substring(0, 10));
        detalleUsuario.setEmail("Email del usuario");

        // Establece la relación bidireccional
        detalleUsuario.setUsuario(usuario);
        usuario.setDetalleUsuario(detalleUsuario);

        // Guarda el usuario (esto debería guardar también el detalle usuario si tienes la cascada configurada)
        usuarioRepository.save(usuario);


        // Crea un usuario con rol ROLE_NUTRICIONISTA
        Usuario usuarioNutricionista = new Usuario();
        usuarioNutricionista.setUsername("nutricionista");
        usuarioNutricionista.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista.setActivo(true);
        usuarioNutricionista.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        // Crea un detalle de usuario para ROLE_NUTRICIONISTA
        DetalleUsuario detalleUsuarioNutricionista = new DetalleUsuario();
        detalleUsuarioNutricionista.setNombre("Francisco josé");
        detalleUsuarioNutricionista.setApellidos("Conejo Barranco");
        detalleUsuarioNutricionista.setDireccion("Antequera");
        detalleUsuarioNutricionista.setDni("0987654321");
        detalleUsuarioNutricionista.setEmail("nutricionista@example.com");

        // Establece la relación bidireccional para ROLE_NUTRICIONISTA
        detalleUsuarioNutricionista.setUsuario(usuarioNutricionista);
        usuarioNutricionista.setDetalleUsuario(detalleUsuarioNutricionista);

        // Guarda el usuario con rol ROLE_NUTRICIONISTA
        usuarioRepository.save(usuarioNutricionista);


        // Crear 15 nutricionistas con rol ROLE_NUTRICIONISTA

// Nutricionista 1
        Usuario usuarioNutricionista1 = new Usuario();
        usuarioNutricionista1.setUsername("nutricionista1");
        usuarioNutricionista1.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista1.setActivo(true);
        usuarioNutricionista1.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista1 = new DetalleUsuario();
        detalleUsuarioNutricionista1.setNombre("Nombre del nutricionista 1");
        detalleUsuarioNutricionista1.setApellidos("Apellidos del nutricionista 1");
        detalleUsuarioNutricionista1.setDireccion("Direccion del nutricionista 1");
        detalleUsuarioNutricionista1.setDni("098765431");
        detalleUsuarioNutricionista1.setEmail("nutricionista1@example.com");

        detalleUsuarioNutricionista1.setUsuario(usuarioNutricionista1);
        usuarioNutricionista1.setDetalleUsuario(detalleUsuarioNutricionista1);

        usuarioRepository.save(usuarioNutricionista1);

// Nutricionista 2
        Usuario usuarioNutricionista2 = new Usuario();
        usuarioNutricionista2.setUsername("nutricionista2");
        usuarioNutricionista2.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista2.setActivo(true);
        usuarioNutricionista2.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista2 = new DetalleUsuario();
        detalleUsuarioNutricionista2.setNombre("Nombre del nutricionista 2");
        detalleUsuarioNutricionista2.setApellidos("Apellidos del nutricionista 2");
        detalleUsuarioNutricionista2.setDireccion("Direccion del nutricionista 2");
        detalleUsuarioNutricionista2.setDni("098765432");
        detalleUsuarioNutricionista2.setEmail("nutricionista2@example.com");

        detalleUsuarioNutricionista2.setUsuario(usuarioNutricionista2);
        usuarioNutricionista2.setDetalleUsuario(detalleUsuarioNutricionista2);

        usuarioRepository.save(usuarioNutricionista2);

// Nutricionista 3
        Usuario usuarioNutricionista3 = new Usuario();
        usuarioNutricionista3.setUsername("nutricionista3");
        usuarioNutricionista3.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista3.setActivo(true);
        usuarioNutricionista3.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista3 = new DetalleUsuario();
        detalleUsuarioNutricionista3.setNombre("Nombre del nutricionista 3");
        detalleUsuarioNutricionista3.setApellidos("Apellidos del nutricionista 3");
        detalleUsuarioNutricionista3.setDireccion("Direccion del nutricionista 3");
        detalleUsuarioNutricionista3.setDni("098765433");
        detalleUsuarioNutricionista3.setEmail("nutricionista3@example.com");

        detalleUsuarioNutricionista3.setUsuario(usuarioNutricionista3);
        usuarioNutricionista3.setDetalleUsuario(detalleUsuarioNutricionista3);

        usuarioRepository.save(usuarioNutricionista3);

// Nutricionista 4
        Usuario usuarioNutricionista4 = new Usuario();
        usuarioNutricionista4.setUsername("nutricionista4");
        usuarioNutricionista4.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista4.setActivo(true);
        usuarioNutricionista4.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista4 = new DetalleUsuario();
        detalleUsuarioNutricionista4.setNombre("Nombre del nutricionista 4");
        detalleUsuarioNutricionista4.setApellidos("Apellidos del nutricionista 4");
        detalleUsuarioNutricionista4.setDireccion("Direccion del nutricionista 4");
        detalleUsuarioNutricionista4.setDni("098765434");
        detalleUsuarioNutricionista4.setEmail("nutricionista4@example.com");

        detalleUsuarioNutricionista4.setUsuario(usuarioNutricionista4);
        usuarioNutricionista4.setDetalleUsuario(detalleUsuarioNutricionista4);

        usuarioRepository.save(usuarioNutricionista4);

// Nutricionista 5
        Usuario usuarioNutricionista5 = new Usuario();
        usuarioNutricionista5.setUsername("nutricionista5");
        usuarioNutricionista5.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista5.setActivo(true);
        usuarioNutricionista5.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista5 = new DetalleUsuario();
        detalleUsuarioNutricionista5.setNombre("Nombre del nutricionista 5");
        detalleUsuarioNutricionista5.setApellidos("Apellidos del nutricionista 5");
        detalleUsuarioNutricionista5.setDireccion("Direccion del nutricionista 5");
        detalleUsuarioNutricionista5.setDni("098765435");
        detalleUsuarioNutricionista5.setEmail("nutricionista5@example.com");

        detalleUsuarioNutricionista5.setUsuario(usuarioNutricionista5);
        usuarioNutricionista5.setDetalleUsuario(detalleUsuarioNutricionista5);

        usuarioRepository.save(usuarioNutricionista5);

// Nutricionista 6
        Usuario usuarioNutricionista6 = new Usuario();
        usuarioNutricionista6.setUsername("nutricionista6");
        usuarioNutricionista6.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista6.setActivo(true);
        usuarioNutricionista6.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista6 = new DetalleUsuario();
        detalleUsuarioNutricionista6.setNombre("Nombre del nutricionista 6");
        detalleUsuarioNutricionista6.setApellidos("Apellidos del nutricionista 6");
        detalleUsuarioNutricionista6.setDireccion("Direccion del nutricionista 6");
        detalleUsuarioNutricionista6.setDni("098765436");
        detalleUsuarioNutricionista6.setEmail("nutricionista6@example.com");

        detalleUsuarioNutricionista6.setUsuario(usuarioNutricionista6);
        usuarioNutricionista6.setDetalleUsuario(detalleUsuarioNutricionista6);

        usuarioRepository.save(usuarioNutricionista6);

// Nutricionista 7
        Usuario usuarioNutricionista7 = new Usuario();
        usuarioNutricionista7.setUsername("nutricionista7");
        usuarioNutricionista7.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista7.setActivo(true);
        usuarioNutricionista7.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista7 = new DetalleUsuario();
        detalleUsuarioNutricionista7.setNombre("Nombre del nutricionista 7");
        detalleUsuarioNutricionista7.setApellidos("Apellidos del nutricionista 7");
        detalleUsuarioNutricionista7.setDireccion("Direccion del nutricionista 7");
        detalleUsuarioNutricionista7.setDni("098765437");
        detalleUsuarioNutricionista7.setEmail("nutricionista7@example.com");

        detalleUsuarioNutricionista7.setUsuario(usuarioNutricionista7);
        usuarioNutricionista7.setDetalleUsuario(detalleUsuarioNutricionista7);

        usuarioRepository.save(usuarioNutricionista7);

// Nutricionista 8
        Usuario usuarioNutricionista8 = new Usuario();
        usuarioNutricionista8.setUsername("nutricionista8");
        usuarioNutricionista8.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista8.setActivo(true);
        usuarioNutricionista8.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista8 = new DetalleUsuario();
        detalleUsuarioNutricionista8.setNombre("Nombre del nutricionista 8");
        detalleUsuarioNutricionista8.setApellidos("Apellidos del nutricionista 8");
        detalleUsuarioNutricionista8.setDireccion("Direccion del nutricionista 8");
        detalleUsuarioNutricionista8.setDni("098765438");
        detalleUsuarioNutricionista8.setEmail("nutricionista8@example.com");

        detalleUsuarioNutricionista8.setUsuario(usuarioNutricionista8);
        usuarioNutricionista8.setDetalleUsuario(detalleUsuarioNutricionista8);

        usuarioRepository.save(usuarioNutricionista8);

// Nutricionista 9
        Usuario usuarioNutricionista9 = new Usuario();
        usuarioNutricionista9.setUsername("nutricionista9");
        usuarioNutricionista9.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista9.setActivo(true);
        usuarioNutricionista9.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista9 = new DetalleUsuario();
        detalleUsuarioNutricionista9.setNombre("Nombre del nutricionista 9");
        detalleUsuarioNutricionista9.setApellidos("Apellidos del nutricionista 9");
        detalleUsuarioNutricionista9.setDireccion("Direccion del nutricionista 9");
        detalleUsuarioNutricionista9.setDni("098765439");
        detalleUsuarioNutricionista9.setEmail("nutricionista9@example.com");

        detalleUsuarioNutricionista9.setUsuario(usuarioNutricionista9);
        usuarioNutricionista9.setDetalleUsuario(detalleUsuarioNutricionista9);

        usuarioRepository.save(usuarioNutricionista9);

// Nutricionista 10
        Usuario usuarioNutricionista10 = new Usuario();
        usuarioNutricionista10.setUsername("nutricionista10");
        usuarioNutricionista10.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista10.setActivo(true);
        usuarioNutricionista10.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista10 = new DetalleUsuario();
        detalleUsuarioNutricionista10.setNombre("Nombre del nutricionista 10");
        detalleUsuarioNutricionista10.setApellidos("Apellidos del nutricionista 10");
        detalleUsuarioNutricionista10.setDireccion("Direccion del nutricionista 10");
        detalleUsuarioNutricionista10.setDni("098765440");
        detalleUsuarioNutricionista10.setEmail("nutricionista10@example.com");

        detalleUsuarioNutricionista10.setUsuario(usuarioNutricionista10);
        usuarioNutricionista10.setDetalleUsuario(detalleUsuarioNutricionista10);

        usuarioRepository.save(usuarioNutricionista10);

// Nutricionista 11
        Usuario usuarioNutricionista11 = new Usuario();
        usuarioNutricionista11.setUsername("nutricionista11");
        usuarioNutricionista11.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista11.setActivo(true);
        usuarioNutricionista11.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista11 = new DetalleUsuario();
        detalleUsuarioNutricionista11.setNombre("Nombre del nutricionista 11");
        detalleUsuarioNutricionista11.setApellidos("Apellidos del nutricionista 11");
        detalleUsuarioNutricionista11.setDireccion("Direccion del nutricionista 11");
        detalleUsuarioNutricionista11.setDni("098765441");
        detalleUsuarioNutricionista11.setEmail("nutricionista11@example.com");

        detalleUsuarioNutricionista11.setUsuario(usuarioNutricionista11);
        usuarioNutricionista11.setDetalleUsuario(detalleUsuarioNutricionista11);

        usuarioRepository.save(usuarioNutricionista11);

// Nutricionista 12
        Usuario usuarioNutricionista12 = new Usuario();
        usuarioNutricionista12.setUsername("nutricionista12");
        usuarioNutricionista12.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista12.setActivo(true);
        usuarioNutricionista12.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista12 = new DetalleUsuario();
        detalleUsuarioNutricionista12.setNombre("Nombre del nutricionista 12");
        detalleUsuarioNutricionista12.setApellidos("Apellidos del nutricionista 12");
        detalleUsuarioNutricionista12.setDireccion("Direccion del nutricionista 12");
        detalleUsuarioNutricionista12.setDni("098765442");
        detalleUsuarioNutricionista12.setEmail("nutricionista12@example.com");

        detalleUsuarioNutricionista12.setUsuario(usuarioNutricionista12);
        usuarioNutricionista12.setDetalleUsuario(detalleUsuarioNutricionista12);

        usuarioRepository.save(usuarioNutricionista12);

// Nutricionista 13
        Usuario usuarioNutricionista13 = new Usuario();
        usuarioNutricionista13.setUsername("nutricionista13");
        usuarioNutricionista13.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista13.setActivo(true);
        usuarioNutricionista13.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista13 = new DetalleUsuario();
        detalleUsuarioNutricionista13.setNombre("Nombre del nutricionista 13");
        detalleUsuarioNutricionista13.setApellidos("Apellidos del nutricionista 13");
        detalleUsuarioNutricionista13.setDireccion("Direccion del nutricionista 13");
        detalleUsuarioNutricionista13.setDni("098765443");
        detalleUsuarioNutricionista13.setEmail("nutricionista13@example.com");

        detalleUsuarioNutricionista13.setUsuario(usuarioNutricionista13);
        usuarioNutricionista13.setDetalleUsuario(detalleUsuarioNutricionista13);

        usuarioRepository.save(usuarioNutricionista13);

// Nutricionista 14
        Usuario usuarioNutricionista14 = new Usuario();
        usuarioNutricionista14.setUsername("nutricionista14");
        usuarioNutricionista14.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista14.setActivo(true);
        usuarioNutricionista14.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista14 = new DetalleUsuario();
        detalleUsuarioNutricionista14.setNombre("Nombre del nutricionista 14");
        detalleUsuarioNutricionista14.setApellidos("Apellidos del nutricionista 14");
        detalleUsuarioNutricionista14.setDireccion("Direccion del nutricionista 14");
        detalleUsuarioNutricionista14.setDni("098765444");
        detalleUsuarioNutricionista14.setEmail("nutricionista14@example.com");

        detalleUsuarioNutricionista14.setUsuario(usuarioNutricionista14);
        usuarioNutricionista14.setDetalleUsuario(detalleUsuarioNutricionista14);

        usuarioRepository.save(usuarioNutricionista14);

// Nutricionista 15
        Usuario usuarioNutricionista15 = new Usuario();
        usuarioNutricionista15.setUsername("nutricionista15");
        usuarioNutricionista15.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioNutricionista15.setActivo(true);
        usuarioNutricionista15.setRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"));

        DetalleUsuario detalleUsuarioNutricionista15 = new DetalleUsuario();
        detalleUsuarioNutricionista15.setNombre("Nombre del nutricionista 15");
        detalleUsuarioNutricionista15.setApellidos("Apellidos del nutricionista 15");
        detalleUsuarioNutricionista15.setDireccion("Direccion del nutricionista 15");
        detalleUsuarioNutricionista15.setDni("098765445");
        detalleUsuarioNutricionista15.setEmail("nutricionista15@example.com");

        detalleUsuarioNutricionista15.setUsuario(usuarioNutricionista15);
        usuarioNutricionista15.setDetalleUsuario(detalleUsuarioNutricionista15);

        usuarioRepository.save(usuarioNutricionista15);


        ////////////////////////////////////////////////////////


        // Crea un usuario con rol ROLE_ENTRENADOR
        Usuario usuarioEntrenador = new Usuario();
        usuarioEntrenador.setUsername("entrenador");
        usuarioEntrenador.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioEntrenador.setActivo(true);
        usuarioEntrenador.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        // Crea un detalle de usuario para ROLE_ENTRENADOR
        DetalleUsuario detalleUsuarioEntrenador = new DetalleUsuario();
        detalleUsuarioEntrenador.setNombre("Nombre del entrenador");
        detalleUsuarioEntrenador.setApellidos("Apellidos del entrenador");
        detalleUsuarioEntrenador.setDireccion("Direccion del entrenador");
        detalleUsuarioEntrenador.setDni("1122334455");
        detalleUsuarioEntrenador.setEmail("entrenador@example.com");

        // Establece la relación bidireccional para ROLE_ENTRENADOR
        detalleUsuarioEntrenador.setUsuario(usuarioEntrenador);
        usuarioEntrenador.setDetalleUsuario(detalleUsuarioEntrenador);

        // Guarda el usuario con rol ROLE_ENTRENADOR
        usuarioRepository.save(usuarioEntrenador);


        // Usuario 1
        Usuario usuarioEntrenadorA = new Usuario();
        usuarioEntrenadorA.setUsername("entrenador1");
        usuarioEntrenadorA.setPassword(bCryptPasswordEncoder.encode("password1"));
        usuarioEntrenadorA.setActivo(true);
        usuarioEntrenadorA.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorA = new DetalleUsuario();
        detalleUsuarioEntrenadorA.setNombre("Juan");
        detalleUsuarioEntrenadorA.setApellidos("Perez");
        detalleUsuarioEntrenadorA.setDireccion("Calle 1");
        detalleUsuarioEntrenadorA.setDni("1234567890");
        detalleUsuarioEntrenadorA.setEmail("juan.perez@example.com");

        detalleUsuarioEntrenadorA.setUsuario(usuarioEntrenadorA);
        usuarioEntrenadorA.setDetalleUsuario(detalleUsuarioEntrenadorA);

        usuarioRepository.save(usuarioEntrenadorA);

        // Usuario 2
        Usuario usuarioEntrenadorB = new Usuario();
        usuarioEntrenadorB.setUsername("entrenador2");
        usuarioEntrenadorB.setPassword(bCryptPasswordEncoder.encode("password2"));
        usuarioEntrenadorB.setActivo(true);
        usuarioEntrenadorB.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorB = new DetalleUsuario();
        detalleUsuarioEntrenadorB.setNombre("Maria");
        detalleUsuarioEntrenadorB.setApellidos("Lopez");
        detalleUsuarioEntrenadorB.setDireccion("Calle 2");
        detalleUsuarioEntrenadorB.setDni("2345678901");
        detalleUsuarioEntrenadorB.setEmail("maria.lopez@example.com");

        detalleUsuarioEntrenadorB.setUsuario(usuarioEntrenadorB);
        usuarioEntrenadorB.setDetalleUsuario(detalleUsuarioEntrenadorB);

        usuarioRepository.save(usuarioEntrenadorB);

// Usuario 3
        Usuario usuarioEntrenadorC = new Usuario();
        usuarioEntrenadorC.setUsername("entrenador3");
        usuarioEntrenadorC.setPassword(bCryptPasswordEncoder.encode("password3"));
        usuarioEntrenadorC.setActivo(true);
        usuarioEntrenadorC.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorC = new DetalleUsuario();
        detalleUsuarioEntrenadorC.setNombre("Carlos");
        detalleUsuarioEntrenadorC.setApellidos("Garcia");
        detalleUsuarioEntrenadorC.setDireccion("Calle 3");
        detalleUsuarioEntrenadorC.setDni("3456789012");
        detalleUsuarioEntrenadorC.setEmail("carlos.garcia@example.com");

        detalleUsuarioEntrenadorC.setUsuario(usuarioEntrenadorC);
        usuarioEntrenadorC.setDetalleUsuario(detalleUsuarioEntrenadorC);

        usuarioRepository.save(usuarioEntrenadorC);

// Usuario 4
        Usuario usuarioEntrenadorD = new Usuario();
        usuarioEntrenadorD.setUsername("entrenador4");
        usuarioEntrenadorD.setPassword(bCryptPasswordEncoder.encode("password4"));
        usuarioEntrenadorD.setActivo(true);
        usuarioEntrenadorD.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorD = new DetalleUsuario();
        detalleUsuarioEntrenadorD.setNombre("Laura");
        detalleUsuarioEntrenadorD.setApellidos("Martinez");
        detalleUsuarioEntrenadorD.setDireccion("Calle 4");
        detalleUsuarioEntrenadorD.setDni("4567890123");
        detalleUsuarioEntrenadorD.setEmail("laura.martinez@example.com");

        detalleUsuarioEntrenadorD.setUsuario(usuarioEntrenadorD);
        usuarioEntrenadorD.setDetalleUsuario(detalleUsuarioEntrenadorD);

        usuarioRepository.save(usuarioEntrenadorD);

// Usuario 5
        Usuario usuarioEntrenadorE = new Usuario();
        usuarioEntrenadorE.setUsername("entrenador5");
        usuarioEntrenadorE.setPassword(bCryptPasswordEncoder.encode("password5"));
        usuarioEntrenadorE.setActivo(true);
        usuarioEntrenadorE.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorE = new DetalleUsuario();
        detalleUsuarioEntrenadorE.setNombre("Luis");
        detalleUsuarioEntrenadorE.setApellidos("Hernandez");
        detalleUsuarioEntrenadorE.setDireccion("Calle 5");
        detalleUsuarioEntrenadorE.setDni("5678901234");
        detalleUsuarioEntrenadorE.setEmail("luis.hernandez@example.com");

        detalleUsuarioEntrenadorE.setUsuario(usuarioEntrenadorE);
        usuarioEntrenadorE.setDetalleUsuario(detalleUsuarioEntrenadorE);

        usuarioRepository.save(usuarioEntrenadorE);

// Usuario 6
        Usuario usuarioEntrenadorF = new Usuario();
        usuarioEntrenadorF.setUsername("entrenador6");
        usuarioEntrenadorF.setPassword(bCryptPasswordEncoder.encode("password6"));
        usuarioEntrenadorF.setActivo(true);
        usuarioEntrenadorF.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorF = new DetalleUsuario();
        detalleUsuarioEntrenadorF.setNombre("Ana");
        detalleUsuarioEntrenadorF.setApellidos("Fernandez");
        detalleUsuarioEntrenadorF.setDireccion("Calle 6");
        detalleUsuarioEntrenadorF.setDni("6789012345");
        detalleUsuarioEntrenadorF.setEmail("ana.fernandez@example.com");

        detalleUsuarioEntrenadorF.setUsuario(usuarioEntrenadorF);
        usuarioEntrenadorF.setDetalleUsuario(detalleUsuarioEntrenadorF);

        usuarioRepository.save(usuarioEntrenadorF);

// Usuario 7
        Usuario usuarioEntrenadorG = new Usuario();
        usuarioEntrenadorG.setUsername("entrenador7");
        usuarioEntrenadorG.setPassword(bCryptPasswordEncoder.encode("password7"));
        usuarioEntrenadorG.setActivo(true);
        usuarioEntrenadorG.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorG = new DetalleUsuario();
        detalleUsuarioEntrenadorG.setNombre("David");
        detalleUsuarioEntrenadorG.setApellidos("Ruiz");
        detalleUsuarioEntrenadorG.setDireccion("Calle 7");
        detalleUsuarioEntrenadorG.setDni("7890123456");
        detalleUsuarioEntrenadorG.setEmail("david.ruiz@example.com");

        detalleUsuarioEntrenadorG.setUsuario(usuarioEntrenadorG);
        usuarioEntrenadorG.setDetalleUsuario(detalleUsuarioEntrenadorG);

        usuarioRepository.save(usuarioEntrenadorG);

// Usuario 8
        Usuario usuarioEntrenadorH = new Usuario();
        usuarioEntrenadorH.setUsername("entrenador8");
        usuarioEntrenadorH.setPassword(bCryptPasswordEncoder.encode("password8"));
        usuarioEntrenadorH.setActivo(true);
        usuarioEntrenadorH.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorH = new DetalleUsuario();
        detalleUsuarioEntrenadorH.setNombre("Sofia");
        detalleUsuarioEntrenadorH.setApellidos("Gutierrez");
        detalleUsuarioEntrenadorH.setDireccion("Calle 8");
        detalleUsuarioEntrenadorH.setDni("8901234567");
        detalleUsuarioEntrenadorH.setEmail("sofia.gutierrez@example.com");

        detalleUsuarioEntrenadorH.setUsuario(usuarioEntrenadorH);
        usuarioEntrenadorH.setDetalleUsuario(detalleUsuarioEntrenadorH);

        usuarioRepository.save(usuarioEntrenadorH);

// Usuario 9
        Usuario usuarioEntrenadorI = new Usuario();
        usuarioEntrenadorI.setUsername("entrenador9");
        usuarioEntrenadorI.setPassword(bCryptPasswordEncoder.encode("password9"));
        usuarioEntrenadorI.setActivo(true);
        usuarioEntrenadorI.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenadorI = new DetalleUsuario();
        detalleUsuarioEntrenadorI.setNombre("Jorge");
        detalleUsuarioEntrenadorI.setApellidos("Diaz");
        detalleUsuarioEntrenadorI.setDireccion("Calle 9");
        detalleUsuarioEntrenadorI.setDni("9012345678");
        detalleUsuarioEntrenadorI.setEmail("jorge.diaz@example.com");

        detalleUsuarioEntrenadorI.setUsuario(usuarioEntrenadorI);
        usuarioEntrenadorI.setDetalleUsuario(detalleUsuarioEntrenadorI);

        usuarioRepository.save(usuarioEntrenadorI);


        // Usuario 1
        Usuario usuarioEntrenador1 = new Usuario();
        usuarioEntrenador1.setUsername("entrenador1");
        usuarioEntrenador1.setPassword(bCryptPasswordEncoder.encode("password1"));
        usuarioEntrenador1.setActivo(true);
        usuarioEntrenador1.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador1 = new DetalleUsuario();
        detalleUsuarioEntrenador1.setNombre("Juan");
        detalleUsuarioEntrenador1.setApellidos("Perez");
        detalleUsuarioEntrenador1.setDireccion("Calle 1");
        detalleUsuarioEntrenador1.setDni("1234567890");
        detalleUsuarioEntrenador1.setEmail("juan.perez@example.com");

        detalleUsuarioEntrenador1.setUsuario(usuarioEntrenador1);
        usuarioEntrenador1.setDetalleUsuario(detalleUsuarioEntrenador1);

        usuarioRepository.save(usuarioEntrenador1);

        // Usuario 2
        Usuario usuarioEntrenador2 = new Usuario();
        usuarioEntrenador2.setUsername("entrenador2");
        usuarioEntrenador2.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuarioEntrenador2.setActivo(true);
        usuarioEntrenador2.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador2 = new DetalleUsuario();
        detalleUsuarioEntrenador2.setNombre("Maria");
        detalleUsuarioEntrenador2.setApellidos("Lopez");
        detalleUsuarioEntrenador2.setDireccion("Calle 2");
        detalleUsuarioEntrenador2.setDni("2345678901");
        detalleUsuarioEntrenador2.setEmail("maria.lopez@example.com");

        detalleUsuarioEntrenador2.setUsuario(usuarioEntrenador2);
        usuarioEntrenador2.setDetalleUsuario(detalleUsuarioEntrenador2);

        usuarioRepository.save(usuarioEntrenador2);

        // Usuario 3
        Usuario usuarioEntrenador3 = new Usuario();
        usuarioEntrenador3.setUsername("entrenador3");
        usuarioEntrenador3.setPassword(bCryptPasswordEncoder.encode("password3"));
        usuarioEntrenador3.setActivo(true);
        usuarioEntrenador3.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador3 = new DetalleUsuario();
        detalleUsuarioEntrenador3.setNombre("Carlos");
        detalleUsuarioEntrenador3.setApellidos("Garcia");
        detalleUsuarioEntrenador3.setDireccion("Calle 3");
        detalleUsuarioEntrenador3.setDni("3456789012");
        detalleUsuarioEntrenador3.setEmail("carlos.garcia@example.com");

        detalleUsuarioEntrenador3.setUsuario(usuarioEntrenador3);
        usuarioEntrenador3.setDetalleUsuario(detalleUsuarioEntrenador3);

        usuarioRepository.save(usuarioEntrenador3);

        // Usuario 4
        Usuario usuarioEntrenador4 = new Usuario();
        usuarioEntrenador4.setUsername("entrenador4");
        usuarioEntrenador4.setPassword(bCryptPasswordEncoder.encode("password4"));
        usuarioEntrenador4.setActivo(true);
        usuarioEntrenador4.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador4 = new DetalleUsuario();
        detalleUsuarioEntrenador4.setNombre("Laura");
        detalleUsuarioEntrenador4.setApellidos("Martinez");
        detalleUsuarioEntrenador4.setDireccion("Calle 4");
        detalleUsuarioEntrenador4.setDni("4567890123");
        detalleUsuarioEntrenador4.setEmail("laura.martinez@example.com");

        detalleUsuarioEntrenador4.setUsuario(usuarioEntrenador4);
        usuarioEntrenador4.setDetalleUsuario(detalleUsuarioEntrenador4);

        usuarioRepository.save(usuarioEntrenador4);

        // Usuario 5
        Usuario usuarioEntrenador5 = new Usuario();
        usuarioEntrenador5.setUsername("entrenador5");
        usuarioEntrenador5.setPassword(bCryptPasswordEncoder.encode("password5"));
        usuarioEntrenador5.setActivo(true);
        usuarioEntrenador5.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador5 = new DetalleUsuario();
        detalleUsuarioEntrenador5.setNombre("Luis");
        detalleUsuarioEntrenador5.setApellidos("Hernandez");
        detalleUsuarioEntrenador5.setDireccion("Calle 5");
        detalleUsuarioEntrenador5.setDni("5678901234");
        detalleUsuarioEntrenador5.setEmail("luis.hernandez@example.com");

        detalleUsuarioEntrenador5.setUsuario(usuarioEntrenador5);
        usuarioEntrenador5.setDetalleUsuario(detalleUsuarioEntrenador5);

        usuarioRepository.save(usuarioEntrenador5);

        // Usuario 6
        Usuario usuarioEntrenador6 = new Usuario();
        usuarioEntrenador6.setUsername("entrenador6");
        usuarioEntrenador6.setPassword(bCryptPasswordEncoder.encode("password6"));
        usuarioEntrenador6.setActivo(true);
        usuarioEntrenador6.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador6 = new DetalleUsuario();
        detalleUsuarioEntrenador6.setNombre("Ana");
        detalleUsuarioEntrenador6.setApellidos("Fernandez");
        detalleUsuarioEntrenador6.setDireccion("Calle 6");
        detalleUsuarioEntrenador6.setDni("6789012345");
        detalleUsuarioEntrenador6.setEmail("ana.fernandez@example.com");

        detalleUsuarioEntrenador6.setUsuario(usuarioEntrenador6);
        usuarioEntrenador6.setDetalleUsuario(detalleUsuarioEntrenador6);

        usuarioRepository.save(usuarioEntrenador6);

        // Usuario 7
        Usuario usuarioEntrenador7 = new Usuario();
        usuarioEntrenador7.setUsername("entrenador7");
        usuarioEntrenador7.setPassword(bCryptPasswordEncoder.encode("password7"));
        usuarioEntrenador7.setActivo(true);
        usuarioEntrenador7.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador7 = new DetalleUsuario();
        detalleUsuarioEntrenador7.setNombre("David");
        detalleUsuarioEntrenador7.setApellidos("Ruiz");
        detalleUsuarioEntrenador7.setDireccion("Calle 7");
        detalleUsuarioEntrenador7.setDni("7890123456");
        detalleUsuarioEntrenador7.setEmail("david.ruiz@example.com");

        detalleUsuarioEntrenador7.setUsuario(usuarioEntrenador7);
        usuarioEntrenador7.setDetalleUsuario(detalleUsuarioEntrenador7);

        usuarioRepository.save(usuarioEntrenador7);

        // Usuario 8
        Usuario usuarioEntrenador8 = new Usuario();
        usuarioEntrenador8.setUsername("entrenador8");
        usuarioEntrenador8.setPassword(bCryptPasswordEncoder.encode("password8"));
        usuarioEntrenador8.setActivo(true);
        usuarioEntrenador8.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador8 = new DetalleUsuario();
        detalleUsuarioEntrenador8.setNombre("Sofia");
        detalleUsuarioEntrenador8.setApellidos("Gutierrez");
        detalleUsuarioEntrenador8.setDireccion("Calle 8");
        detalleUsuarioEntrenador8.setDni("8901234567");
        detalleUsuarioEntrenador8.setEmail("sofia.gutierrez@example.com");

        detalleUsuarioEntrenador8.setUsuario(usuarioEntrenador8);
        usuarioEntrenador8.setDetalleUsuario(detalleUsuarioEntrenador8);

        usuarioRepository.save(usuarioEntrenador8);

        // Usuario 9
        Usuario usuarioEntrenador9 = new Usuario();
        usuarioEntrenador9.setUsername("entrenador9");
        usuarioEntrenador9.setPassword(bCryptPasswordEncoder.encode("password9"));
        usuarioEntrenador9.setActivo(true);
        usuarioEntrenador9.setRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"));

        DetalleUsuario detalleUsuarioEntrenador9 = new DetalleUsuario();
        detalleUsuarioEntrenador9.setNombre("Jorge");
        detalleUsuarioEntrenador9.setApellidos("Diaz");
        detalleUsuarioEntrenador9.setDireccion("Calle 9");
        detalleUsuarioEntrenador9.setDni("9012345678");
        detalleUsuarioEntrenador9.setEmail("jorge.diaz@example.com");

        detalleUsuarioEntrenador9.setUsuario(usuarioEntrenador9);
        usuarioEntrenador9.setDetalleUsuario(detalleUsuarioEntrenador9);

        usuarioRepository.save(usuarioEntrenador9);

        ////////////////////////////////////////////////////////

        Usuario usuario1 = new Usuario();
        usuario1.setUsername("anonimo");
        usuario1.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuario1.setActivo(true);
        usuario1.setRol(rolesRepo.findByNombreRol("ROLE_ANONIMOUS"));
        Usuario usuarioguardado1 = usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("entrenador");
        usuario2.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuario2.setActivo(true);
        usuario2.setRol(rolesRepo.findByNombreRol("ROLE_EMPLEADO"));
        Usuario usuarioguardado2 = usuarioRepository.save(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setUsername("deportista");
        usuario3.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuario3.setActivo(true);
        usuario3.setRol(rolesRepo.findByNombreRol("ROLE_USER"));
        Usuario usuarioguardado3 = usuarioRepository.save(usuario3);
    }


    public void altaEntrenamientos() {
        List<Entrenamiento> entrenamientos = new ArrayList<>();

        entrenamientos.add(new Entrenamiento(
                "Entrenamiento de Pecho Avanzado",
                "musculacion",
                null, // recursos_multimedia
                "pecho",
                "aumentar masa muscular",
                "avanzado", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Mantenimiento de Piernas",
                "mantenimiento",
                null,
                "piernas",
                "mantener tono muscular",
                "intermedio", // dificultad
                "2 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Abdomen Pesocorporal",
                "pesocorporal",
                null,
                "abdomen",
                "reducir grasa abdominal",
                "intermedio", // dificultad
                "4 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Cardio Completo",
                "cardio",
                null,
                "cuerpo completo",
                "mejorar resistencia",
                "principiante", // dificultad
                "5 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Fuerza de Espalda",
                "musculacion",
                null,
                "espalda",
                "aumentar fuerza",
                "avanzado", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Definición de Brazos",
                "mantenimiento",
                null,
                "brazos",
                "mantener definición",
                "intermedio", // dificultad
                "2 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Tonificación de Piernas",
                "pesocorporal",
                null,
                "piernas",
                "tonificar",
                "intermedio", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Cardio Cardiovascular",
                "cardio",
                null,
                "corazón",
                "mejorar salud cardiovascular",
                "principiante", // dificultad
                "6 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Masa Muscular Hombros",
                "musculacion",
                null,
                "hombros",
                "aumentar masa muscular",
                "avanzado", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Mantenimiento Completo",
                "mantenimiento",
                null,
                "cuerpo completo",
                "mantener forma física",
                "intermedio", // dificultad
                "2 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Tonificación de Glúteos",
                "pesocorporal",
                null,
                "glúteos",
                "tonificar y levantar",
                "intermedio", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Cardio de Velocidad",
                "cardio",
                null,
                "piernas",
                "mejorar velocidad",
                "principiante", // dificultad
                "4 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Volumen de Tríceps",
                "musculacion",
                null,
                "tríceps",
                "aumentar volumen",
                "avanzado", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Tono Abdominal",
                "mantenimiento",
                null,
                "abdomen",
                "mantener tono abdominal",
                "intermedio", // dificultad
                "2 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Reducción Grasa Corporal",
                "pesocorporal",
                null,
                "cuerpo completo",
                "reducir grasa corporal",
                "intermedio", // dificultad
                "5 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Capacidad Pulmonar",
                "cardio",
                null,
                "pulmones",
                "mejorar capacidad pulmonar",
                "principiante", // dificultad
                "5 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Masa de Bíceps",
                "musculacion",
                null,
                "bíceps",
                "aumentar masa muscular",
                "avanzado", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Fuerza de Espalda Mantenimiento",
                "mantenimiento",
                null,
                "espalda",
                "mantener fuerza",
                "intermedio", // dificultad
                "2 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Tonificación de Pecho",
                "pesocorporal",
                null,
                "pecho",
                "tonificar",
                "intermedio", // dificultad
                "3 veces por semana"
        ));

        entrenamientos.add(new Entrenamiento(
                "Resistencia General",
                "cardio",
                null,
                "cuerpo completo",
                "mejorar resistencia general",
                "principiante", // dificultad
                "6 veces por semana"
        ));

        // Guardar todos los entrenamientos en la base de datos
        entrenamientoRepo.saveAll(entrenamientos);
    }


    public void altaProductos() {
        List<Producto> productos = new ArrayList<>();

        productos.add(new Producto(
                "P005", // codigo
                "Proteína Caseína", // nombre
                "Proteína de digestión lenta", // descripcion
                "proteina", // categoria
                29.99, // precio
                80.0, // stock
                proveedoresRepo.findById(1).orElse(null) // proveedor_id
        ));

        productos.add(new Producto(
                "P006",
                "Proteína Vegana",
                "Proteína vegetal de alta calidad",
                "proteina",
                27.99,
                90.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P007",
                "Creatina HCL",
                "Creatina de rápida absorción",
                "creatina",
                22.99,
                60.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P008",
                "Creatina Micronizada",
                "Creatina con mejor solubilidad",
                "creatina",
                21.99,
                55.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P009",
                "Snack de Almendras",
                "Barra saludable con almendras",
                "snacks",
                1.99,
                150.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P010",
                "Snack de Avena",
                "Barra energética de avena",
                "snacks",
                2.49,
                160.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P011",
                "Vitaminas B12",
                "Suplemento de vitamina B12",
                "vitaminas",
                9.99,
                70.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P012",
                "Vitamina D3",
                "Suplemento de vitamina D3",
                "vitaminas",
                12.99,
                65.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P013",
                "Proteína Hidrolizada",
                "Proteína de suero hidrolizada",
                "proteina",
                32.99,
                85.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P014",
                "Proteína de Huevo",
                "Proteína de clara de huevo",
                "proteina",
                28.99,
                95.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P015",
                "Creatina Citrato",
                "Creatina con mejor absorción",
                "creatina",
                24.99,
                45.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P016",
                "Creatina Etil Ester",
                "Creatina mejorada para la absorción",
                "creatina",
                23.99,
                50.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P017",
                "Snack de Nueces",
                "Barra energética con nueces",
                "snacks",
                2.29,
                140.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P018",
                "Snack de Coco",
                "Barra de coco y chocolate",
                "snacks",
                2.79,
                130.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P019",
                "Vitamina C",
                "Suplemento de vitamina C",
                "vitaminas",
                8.99,
                85.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P020",
                "Vitamina E",
                "Suplemento de vitamina E",
                "vitaminas",
                10.99,
                80.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P021",
                "Proteína de Soja",
                "Proteína vegetal de soja",
                "proteina",
                26.99,
                70.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P022",
                "Proteína de Arroz",
                "Proteína vegetal de arroz",
                "proteina",
                27.49,
                75.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P023",
                "Creatina Nitrato",
                "Creatina con nitrato para mejor rendimiento",
                "creatina",
                25.99,
                40.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P024",
                "Creatina AKG",
                "Creatina alfa-cetoglutarato",
                "creatina",
                24.49,
                60.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P025",
                "Snack de Frutas",
                "Barra con frutas deshidratadas",
                "snacks",
                1.79,
                170.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P026",
                "Snack de Chocolate",
                "Barra de chocolate negro",
                "snacks",
                2.99,
                180.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P027",
                "Vitamina K2",
                "Suplemento de vitamina K2",
                "vitaminas",
                13.99,
                55.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P028",
                "Vitamina B6",
                "Suplemento de vitamina B6",
                "vitaminas",
                7.99,
                65.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P029",
                "Proteína de Cáñamo",
                "Proteína vegetal de cáñamo",
                "proteina",
                31.99,
                50.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P030",
                "Proteína de Guisante",
                "Proteína vegetal de guisante",
                "proteina",
                29.49,
                60.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P031",
                "Creatina Magnesio Quelato",
                "Creatina con magnesio",
                "creatina",
                26.99,
                35.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P032",
                "Creatina Phosphate",
                "Creatina fosfato",
                "creatina",
                25.49,
                45.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P033",
                "Snack de Semillas",
                "Barra de semillas y miel",
                "snacks",
                2.49,
                190.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P034",
                "Snack de Arándanos",
                "Barra con arándanos y avena",
                "snacks",
                2.99,
                160.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P035",
                "Vitamina A",
                "Suplemento de vitamina A",
                "vitaminas",
                9.49,
                75.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P036",
                "Vitamina B Complex",
                "Complejo de vitaminas B",
                "vitaminas",
                11.99,
                70.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P037",
                "Proteína de Chía",
                "Proteína vegetal de chía",
                "proteina",
                30.99,
                55.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P038",
                "Proteína de Altramuz",
                "Proteína vegetal de altramuz",
                "proteina",
                28.49,
                65.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P039",
                "Creatina Monohidrato Micronizado",
                "Creatina monohidrato micronizado",
                "creatina",
                27.99,
                50.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P040",
                "Creatina Kre-Alkalyn",
                "Creatina con pH balanceado",
                "creatina",
                26.49,
                45.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P041",
                "Snack de Cacahuetes",
                "Barra energética con cacahuetes",
                "snacks",
                1.99,
                150.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P042",
                "Snack de Mantequilla de Maní",
                "Barra de mantequilla de maní",
                "snacks",
                2.79,
                180.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P043",
                "Vitamina Zinc",
                "Suplemento de zinc",
                "vitaminas",
                10.99,
                80.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P044",
                "Vitamina Magnesio",
                "Suplemento de magnesio",
                "vitaminas",
                12.49,
                75.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P045",
                "Proteína de Algas",
                "Proteína vegetal de algas",
                "proteina",
                32.99,
                40.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P046",
                "Proteína de Amaranto",
                "Proteína vegetal de amaranto",
                "proteina",
                30.49,
                50.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P047",
                "Creatina Líquida",
                "Creatina en forma líquida",
                "creatina",
                28.99,
                30.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P048",
                "Creatina Effervescent",
                "Creatina efervescente",
                "creatina",
                27.49,
                35.0,
                proveedoresRepo.findById(2).orElse(null)
        ));

        productos.add(new Producto(
                "P049",
                "Snack de Pistachos",
                "Barra de pistachos y miel",
                "snacks",
                2.49,
                160.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P050",
                "Snack de Macadamia",
                "Barra de nueces de macadamia",
                "snacks",
                2.99,
                140.0,
                proveedoresRepo.findById(3).orElse(null)
        ));

        productos.add(new Producto(
                "P051",
                "Vitamina Omega-3",
                "Suplemento de ácidos grasos omega-3",
                "vitaminas",
                14.99,
                60.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P052",
                "Vitamina C con Zinc",
                "Suplemento de vitamina C y zinc",
                "vitaminas",
                13.49,
                70.0,
                proveedoresRepo.findById(4).orElse(null)
        ));

        productos.add(new Producto(
                "P053",
                "Proteína de Lentejas",
                "Proteína vegetal de lentejas",
                "proteina",
                27.99,
                55.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productos.add(new Producto(
                "P054",
                "Proteína de Calabaza",
                "Proteína vegetal de semillas de calabaza",
                "proteina",
                29.49,
                65.0,
                proveedoresRepo.findById(1).orElse(null)
        ));

        productoRepo.saveAll(productos);
    }


    public void altaProveedores() {
        List<Proveedores> proveedores = new ArrayList<>();

        proveedores.add(new Proveedores("Proveedor A", "contactoA@example.com", "Calle Falsa 123, Ciudad A"));
        proveedores.add(new Proveedores("Proveedor B", "contactoB@example.com", "Avenida Siempre Viva 742, Ciudad B"));
        proveedores.add(new Proveedores("Proveedor C", "contactoC@example.com", "Bulevar de los Sueños Rotos 45, Ciudad C"));
        proveedores.add(new Proveedores("Proveedor D", "contactoD@example.com", "Calle del Sol 98, Ciudad D"));
        proveedores.add(new Proveedores("Proveedor E", "contactoE@example.com", "Calle Luna 23, Ciudad E"));
        proveedores.add(new Proveedores("Proveedor F", "contactoF@example.com", "Plaza Mayor 11, Ciudad F"));
        proveedores.add(new Proveedores("Proveedor G", "contactoG@example.com", "Calle de la Esperanza 89, Ciudad G"));
        proveedores.add(new Proveedores("Proveedor H", "contactoH@example.com", "Avenida del Río 76, Ciudad H"));
        proveedores.add(new Proveedores("Proveedor I", "contactoI@example.com", "Calle del Valle 55, Ciudad I"));
        proveedores.add(new Proveedores("Proveedor J", "contactoJ@example.com", "Calle de la Alegría 34, Ciudad J"));

        proveedoresRepo.saveAll(proveedores);
    }

    public void altaDietas() {
        List<Dieta> dietas = new ArrayList<>();

        // Categoría: Aumento de Masa Muscular
        dietas.add(new Dieta("Dieta Alta en Proteínas 1", "Aumentar la masa muscular con proteínas de alta calidad.", "aumentodemasamuscular", null, "Diario", "Ideal para aquellos que buscan ganar masa muscular rápidamente."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 2", "Plan de comidas con alto contenido en proteínas y bajo en grasas.", "aumentodemasamuscular", null, "Diario", "Enfocada en la recuperación y crecimiento muscular."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 3", "Dieta rica en proteínas animales y vegetales para el aumento muscular.", "aumentodemasamuscular", null, "Diario", "Equilibrio entre proteínas animales y vegetales para maximizar el crecimiento muscular."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 4", "Dieta para atletas con alto requerimiento proteico.", "aumentodemasamuscular", null, "Diario", "Aumenta la masa muscular y la fuerza para atletas profesionales."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 5", "Plan de alimentación basado en batidos y alimentos ricos en proteínas.", "aumentodemasamuscular", null, "Diario", "Fomenta el crecimiento muscular con la combinación de alimentos y batidos."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 6", "Dieta centrada en la ingesta de huevos, carnes y legumbres.", "aumentodemasamuscular", null, "Diario", "Ingesta alta en proteínas para crecimiento muscular y recuperación."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 7", "Plan alimenticio con énfasis en proteínas y carbohidratos complejos.", "aumentodemasamuscular", null, "Diario", "Combinación ideal para el desarrollo muscular y la energía."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 8", "Dieta para el aumento de masa muscular con suplementos de proteínas.", "aumentodemasamuscular", null, "Diario", "Incorpora suplementos para maximizar la ganancia muscular."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 9", "Dieta diseñada para culturistas con alto contenido proteico.", "aumentodemasamuscular", null, "Diario", "Ajustada para culturistas que buscan definición y volumen."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 10", "Plan de comidas con proteínas para entrenamientos intensivos.", "aumentodemasamuscular", null, "Diario", "Ideal para quienes entrenan con alta intensidad y buscan recuperación muscular."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 11", "Dieta con énfasis en carnes magras y suplementos.", "aumentodemasamuscular", null, "Diario", "Enfocada en fuentes de proteínas magras para evitar grasas innecesarias."));
        dietas.add(new Dieta("Dieta Alta en Proteínas 12", "Plan alimenticio para el aumento de masa muscular en deportistas de resistencia.", "aumentodemasamuscular", null, "Diario", "Especialmente diseñado para atletas de resistencia que buscan aumentar su masa muscular."));

        // Categoría: Pérdida de Peso
        dietas.add(new Dieta("Dieta de Definición 1", "Plan de comidas para reducir grasa y mantener la masa muscular.", "perdidadepeso", null, "Semanal", "Enfocada en la pérdida de grasa sin perder músculo."));
        dietas.add(new Dieta("Dieta de Definición 2", "Dieta baja en carbohidratos para acelerar la quema de grasa.", "perdidadepeso", null, "Semanal", "Optimizada para personas que desean reducir el porcentaje de grasa corporal."));
        dietas.add(new Dieta("Dieta de Definición 3", "Dieta rica en fibras y proteínas para la pérdida de peso.", "perdidadepeso", null, "Semanal", "Combina alimentos ricos en fibra con proteínas para mantener la saciedad."));
        dietas.add(new Dieta("Dieta de Definición 4", "Plan de comidas con calorías controladas para pérdida de peso.", "perdidadepeso", null, "Semanal", "Controla las calorías para lograr una pérdida de peso eficaz."));
        dietas.add(new Dieta("Dieta de Definición 5", "Dieta intermitente para la reducción de grasa corporal.", "perdidadepeso", null, "Semanal", "Enfocada en la pérdida de peso mediante ayuno intermitente."));
        dietas.add(new Dieta("Dieta de Definición 6", "Plan alimenticio para quemar grasa con alta ingesta de proteínas.", "perdidadepeso", null, "Semanal", "Combina alta ingesta de proteínas con reducción de calorías para perder peso."));
        dietas.add(new Dieta("Dieta de Definición 7", "Dieta de bajo índice glucémico para controlar el apetito.", "perdidadepeso", null, "Semanal", "Enfocada en mantener estables los niveles de azúcar en sangre."));
        dietas.add(new Dieta("Dieta de Definición 8", "Plan de comidas para una pérdida de peso gradual y saludable.", "perdidadepeso", null, "Semanal", "Optimizada para una pérdida de peso a largo plazo sin efectos negativos."));
        dietas.add(new Dieta("Dieta de Definición 9", "Dieta rica en vegetales y proteínas para la pérdida de peso.", "perdidadepeso", null, "Semanal", "Focalizada en alimentos vegetales para apoyar la pérdida de peso."));
        dietas.add(new Dieta("Dieta de Definición 10", "Plan de comidas con énfasis en proteínas magras y grasas saludables.", "perdidadepeso", null, "Semanal", "Reduce el porcentaje de grasa corporal con un balance de proteínas y grasas saludables."));
        dietas.add(new Dieta("Dieta de Definición 11", "Dieta para pérdida de peso con comidas pequeñas y frecuentes.", "perdidadepeso", null, "Semanal", "Optimizada para mantener el metabolismo activo durante todo el día."));
        dietas.add(new Dieta("Dieta de Definición 12", "Plan alimenticio para quemar grasa y mejorar la composición corporal.", "perdidadepeso", null, "Semanal", "Focalizada en la mejora de la composición corporal y la quema de grasa."));

        // Categoría: Aumento de Peso
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 1", "Plan de comidas con alta ingesta calórica y proteínas.", "aumentodepeso", null, "Diario", "Enfocada en incrementar rápidamente el peso corporal con calorías y proteínas."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 2", "Dieta rica en carbohidratos y proteínas para ganar peso.", "aumentodepeso", null, "Diario", "Combina carbohidratos complejos y proteínas para el aumento de peso."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 3", "Plan alimenticio con batidos y snacks altos en calorías.", "aumentodepeso", null, "Diario", "Ideal para ganar peso rápidamente con la ayuda de batidos y snacks."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 4", "Dieta para ganar peso con comidas frecuentes y calóricas.", "aumentodepeso", null, "Diario", "Plan de comidas que incluye alimentos ricos en calorías y nutrientes."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 5", "Plan de comidas con énfasis en alimentos densos en calorías.", "aumentodepeso", null, "Diario", "Optimizado para aquellos que buscan ganar peso rápidamente con alimentos densos en calorías."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 6", "Dieta rica en grasas saludables y proteínas para el aumento de peso.", "aumentodepeso", null, "Diario", "Combina grasas saludables y proteínas para un incremento de peso saludable."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 7", "Plan alimenticio para incremento de peso con alto contenido en nutrientes.", "aumentodepeso", null, "Diario", "Ideal para el aumento de peso con una alta densidad nutricional."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 8", "Dieta con alto contenido en calorías y proteínas para masa muscular.", "aumentodepeso", null, "Diario", "Focalizada en la ganancia de masa muscular con calorías y proteínas."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 9", "Plan de comidas con énfasis en carnes rojas y granos.", "aumentodepeso", null, "Diario", "Aumenta el peso corporal con una dieta rica en carnes rojas y granos."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 10", "Dieta alta en calorías y batidos para aumentar el peso.", "aumentodepeso", null, "Diario", "Incorpora batidos y alimentos altos en calorías para un aumento rápido de peso."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 11", "Plan de comidas para aumentar peso con calorías líquidas y sólidas.", "aumentodepeso", null, "Diario", "Combina calorías líquidas y sólidas para facilitar el aumento de peso."));
        dietas.add(new Dieta("Dieta para Aumento de Peso Rápido 12", "Dieta con enfoque en alimentos ricos en calorías y proteínas para deportistas.", "aumentodepeso", null, "Diario", "Optimizada para deportistas que buscan aumentar peso y masa muscular."));

        // Categoría: Dieta Deportiva
        dietas.add(new Dieta("Dieta Deportiva 1", "Plan alimenticio para optimizar el rendimiento deportivo.", "dietadeportiva", null, "Diario", "Optimizada para mejorar el rendimiento y la recuperación en atletas."));
        dietas.add(new Dieta("Dieta Deportiva 2", "Dieta balanceada para apoyar el entrenamiento y la recuperación.", "dietadeportiva", null, "Diario", "Incluye una mezcla de proteínas, carbohidratos y grasas para el rendimiento deportivo."));
        dietas.add(new Dieta("Dieta Deportiva 3", "Plan de comidas para atletas de resistencia con alto contenido en carbohidratos.", "dietadeportiva", null, "Diario", "Optimizada para aumentar la resistencia con una alta ingesta de carbohidratos."));
        dietas.add(new Dieta("Dieta Deportiva 4", "Dieta con alto contenido en proteínas para fortalecer músculos.", "dietadeportiva", null, "Diario", "Enfocada en la construcción muscular y recuperación para deportistas."));
        dietas.add(new Dieta("Dieta Deportiva 5", "Plan alimenticio con enfoque en la recuperación post-entrenamiento.", "dietadeportiva", null, "Diario", "Optimizado para la recuperación rápida después de entrenamientos intensivos."));
        dietas.add(new Dieta("Dieta Deportiva 6", "Dieta rica en antioxidantes para mejorar el rendimiento y la salud.", "dietadeportiva", null, "Diario", "Incluye alimentos ricos en antioxidantes para mejorar la recuperación y el rendimiento."));
        dietas.add(new Dieta("Dieta Deportiva 7", "Plan de comidas para mejorar el rendimiento en deportes de alta intensidad.", "dietadeportiva", null, "Diario", "Optimizada para aquellos que practican deportes de alta intensidad."));
        dietas.add(new Dieta("Dieta Deportiva 8", "Dieta equilibrada para deportistas con un enfoque en la salud general.", "dietadeportiva", null, "Diario", "Incluye un balance de todos los macronutrientes para una salud óptima."));
        dietas.add(new Dieta("Dieta Deportiva 9", "Plan alimenticio para mejorar el enfoque y la energía durante el entrenamiento.", "dietadeportiva", null, "Diario", "Optimizado para mantener altos niveles de energía y concentración durante el ejercicio."));
        dietas.add(new Dieta("Dieta Deportiva 10", "Dieta centrada en la hidratación y la energía para el rendimiento deportivo.", "dietadeportiva", null, "Diario", "Focalizada en mantener una adecuada hidratación y niveles de energía."));
        dietas.add(new Dieta("Dieta Deportiva 11", "Plan alimenticio para optimizar el metabolismo y el rendimiento atlético.", "dietadeportiva", null, "Diario", "Optimizado para un metabolismo eficiente y rendimiento máximo en el deporte."));
        dietas.add(new Dieta("Dieta Deportiva 12", "Dieta con enfoque en la prevención de lesiones y mejor recuperación.", "dietadeportiva", null, "Diario", "Incluye alimentos y nutrientes para prevenir lesiones y mejorar la recuperación."));

        // Guardar todas las dietas en la base de datos
        dietaRepo.saveAll(dietas);
    }






}