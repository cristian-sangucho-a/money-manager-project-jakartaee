package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase del manejo para las entidades
 */
public class ManejoEntidadPersistencia {
    private static final EntityManagerFactory emf;

    static {
        try {
            // Crea la EntityManagerFactory solo una vez
            emf = Persistence.createEntityManagerFactory("Contabilidad");
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar JPA", e);
        }
    }

    // Evita la instanciación de la clase
    private ManejoEntidadPersistencia() {}

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para cerrar la fábrica al detener la aplicación
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
//public class ManejoEntidadPersistencia {
//    /**
//     * Constructor privado para el manejo de la entidad de persistencia
//     */
//    private ManejoEntidadPersistencia() {
//    }
//
//    /**
//     * Método estático para obtener la instancia del Manejador de fábricas
//     * @return un objeto EntityManagerFactory con el persistence unit name especificado en el xml de persistencia
//     */
//    static EntityManagerFactory getEntityManagerFactoryInstance() {
//        return Persistence.createEntityManagerFactory("Contabilidad");
//    }
//
//    /**
//     *Método estático para obtener el Manejador de entidades
//     * @return un objeto de tipo Entity Manager
//     */
//    public static EntityManager getEntityManager() {
//        return getEntityManagerFactoryInstance().createEntityManager();
//    }
//}
