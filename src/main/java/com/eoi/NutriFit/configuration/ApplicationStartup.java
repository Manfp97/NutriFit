package com.eoi.NutriFit.configuration;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.RolesRepo;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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

    /**
     * Constructor de la clase que recibe un {@link UsuarioRepository} para interactuar con la base de datos.
     *
     * @param userRepository el repositorio de usuarios que se utilizará para guardar los datos del usuario.
     * @param rolesRepo
     * @param env
     */
    public ApplicationStartup(UsuarioRepository userRepository, RolesRepo rolesRepo, Environment env, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = userRepository;
        this.rolesRepo = rolesRepo;
        this.env = env;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        Usuario usuario = new Usuario();
        usuario.setUsername("administrador");
        usuario.setPassword(bCryptPasswordEncoder.encode("noteladigo"));
        usuario.setActivo(true);
        usuario.setRol(rolesRepo.findByNombreRol("ROLE_ADMIN"));
        Usuario usuarioguardado = usuarioRepository.save(usuario);

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
    public void altaEntrenamientos(){
        Entrenamiento entrenamiento1 = new Entrenamiento();
        entrenamiento1.setNombre("Entrenamiento de Pecho Avanzado");
        entrenamiento1.setCategoria("musculacion");
        entrenamiento1.setGrupoMuscular("pecho");
        entrenamiento1.setObjetivos("aumentar masa muscular");
        entrenamiento1.setPlanificacionFrecuencia("3 veces por semana");
        //FALTA GRABAR
        Entrenamiento entrenamiento2 = new Entrenamiento();
        entrenamiento2.setNombre("Mantenimiento de Piernas");
        entrenamiento2.setCategoria("mantinimiento");
        entrenamiento2.setGrupoMuscular("piernas");
        entrenamiento2.setObjetivos("mantener tono muscular");
        entrenamiento2.setPlanificacionFrecuencia("2 veces por semana");
        Entrenamiento entrenamiento3 = new Entrenamiento();
        entrenamiento3.setNombre("Abdomen pesocorporal");
        entrenamiento3.setCategoria("pesocorporal");
        entrenamiento3.setGrupoMuscular("abdomen");
        entrenamiento3.setObjetivos("reducir grasa abdominal");
        entrenamiento3.setPlanificacionFrecuencia("4 veces por semana");
        Entrenamiento entrenamiento4 = new Entrenamiento();
        entrenamiento4.setNombre("Cardio Completo");
        entrenamiento4.setCategoria("cardio");
        entrenamiento4.setGrupoMuscular("cuerpo completo");
        entrenamiento4.setObjetivos("mejorar resistencia");
        entrenamiento4.setPlanificacionFrecuencia("5 veces por semana");
        Entrenamiento entrenamiento5 = new Entrenamiento();
        entrenamiento5.setNombre("Fuerza de Espalda");
        entrenamiento5.setCategoria("musculacion");
        entrenamiento5.setGrupoMuscular("espalda");
        entrenamiento5.setObjetivos("aumentar fuerza");
        entrenamiento5.setPlanificacionFrecuencia("3 veces por semana");
        /*
        entrenamiento.setNombre("Definición de Brazos");
        entrenamiento.setCategoria("mantinimiento");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("brazos");
        entrenamiento.setObjetivos("mantener definición");
        entrenamiento.setPlanificacionFrecuencia("2 veces por semana");

        entrenamiento.setNombre("Tonificación de Piernas");
        entrenamiento.setCategoria("pesocorporal");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("piernas");
        entrenamiento.setObjetivos("tonificar");
        entrenamiento.setPlanificacionFrecuencia("3 veces por semana");

        entrenamiento.setNombre("Cardio Cardiovascular");
        entrenamiento.setCategoria("cardio");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("corazón");
        entrenamiento.setObjetivos("mejorar salud cardiovascular");
        entrenamiento.setPlanificacionFrecuencia("6 veces por semana");

        entrenamiento.setNombre("Masa Muscular Hombros");
        entrenamiento.setCategoria("musculacion");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("hombros");
        entrenamiento.setObjetivos("aumentar masa muscular");
        entrenamiento.setPlanificacionFrecuencia("3 veces por semana");

        entrenamiento.setNombre("Mantenimiento Completo");
        entrenamiento.setCategoria("mantinimiento");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("cuerpo completo");
        entrenamiento.setObjetivos("mantener forma física");
        entrenamiento.setPlanificacionFrecuencia("2 veces por semana");

        entrenamiento.setNombre("Tonificación de Glúteos");
        entrenamiento.setCategoria("pesocorporal");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("glúteos");
        entrenamiento.setObjetivos("tonificar y levantar");
        entrenamiento.setPlanificacionFrecuencia("3 veces por semana");

        entrenamiento.setNombre("Cardio de Velocidad");
        entrenamiento.setCategoria("cardio");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("piernas");
        entrenamiento.setObjetivos("mejorar velocidad");
        entrenamiento.setPlanificacionFrecuencia("4 veces por semana");

        entrenamiento.setNombre("Volumen de Tríceps");
        entrenamiento.setCategoria("musculacion");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("tríceps");
        entrenamiento.setObjetivos("aumentar volumen");
        entrenamiento.setPlanificacionFrecuencia("3 veces por semana");

        entrenamiento.setNombre("Tono Abdominal");
        entrenamiento.setCategoria("mantinimiento");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("abdomen");
        entrenamiento.setObjetivos("mantener tono abdominal");
        entrenamiento.setPlanificacionFrecuencia("2 veces por semana");

        entrenamiento.setNombre("Reducción Grasa Corporal");
        entrenamiento.setCategoria("pesocorporal");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("cuerpo completo");
        entrenamiento.setObjetivos("reducir grasa corporal");
        entrenamiento.setPlanificacionFrecuencia("5 veces por semana");

        entrenamiento.setNombre("Capacidad Pulmonar");
        entrenamiento.setCategoria("cardio");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("pulmones");
        entrenamiento.setObjetivos("mejorar capacidad pulmonar");
        entrenamiento.setPlanificacionFrecuencia("5 veces por semana");

        entrenamiento.setNombre("Masa de Bíceps");
        entrenamiento.setCategoria("musculacion");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("bíceps");
        entrenamiento.setObjetivos("aumentar masa muscular");
        entrenamiento.setPlanificacionFrecuencia("3 veces por semana");

        entrenamiento.setNombre("Fuerza de Espalda Mantenimiento");
        entrenamiento.setCategoria("mantinimiento");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("espalda");
        entrenamiento.setObjetivos("mantener fuerza");
        entrenamiento.setPlanificacionFrecuencia("2 veces por semana");

        entrenamiento.setNombre("Tonificación de Pecho");
        entrenamiento.setCategoria("pesocorporal");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("pecho");
        entrenamiento.setObjetivos("tonificar");
        entrenamiento.setPlanificacionFrecuencia("3 veces por semana");

        entrenamiento.setNombre("Resistencia General");
        entrenamiento.setCategoria("cardio");
        entrenamiento.setRecursos_multimedia(NULL);
        entrenamiento.setGrupoMuscular("cuerpo completo");
        entrenamiento.setObjetivos("mejorar resistencia general");
        entrenamiento.setPlanificacionFrecuencia("6 veces por semana");

        */
    }

}