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
	private EntityManagerFactory emf = null;
	private EntityManager em = null;

    /**
     * Default constructor
     */
    public CategoriaTransferenciaDAO() {
    	this.emf = Persistence.createEntityManagerFactory("Contabilidad");
    	this.em = emf.createEntityManager();
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