package modelo .dao;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.dto.CategoriaResumenDTO;
import modelo.entidades.CategoriaTransferencia;

/**
 * 
 */
public class CategoriaTransferenciaDAO {
	

    /**
     * Default constructor
     */
    public CategoriaTransferenciaDAO() {
    	
    }

    /**
     * @param from 
     * @param to 
     * @return
     */
    public List<CategoriaResumenDTO> getAllSumarized(Date from, Date to) {
        // TODO implement here
        return null;
    }
    
    public CategoriaTransferencia getCategoryById(int categoryId) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
    	CategoriaTransferencia categoria = null;
	    em.getTransaction().begin();
	    try {
	        categoria = em.find(CategoriaTransferencia.class, categoryId);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    }
	    return categoria;
    }

}