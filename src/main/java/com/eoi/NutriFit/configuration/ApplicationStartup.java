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