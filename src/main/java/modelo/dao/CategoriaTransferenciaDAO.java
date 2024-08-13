package modelo .dao;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.dto.CategoriaResumenDTO;
import modelo.entidades.Categoria;
import modelo.entidades.CategoriaIngreso;
import modelo.entidades.CategoriaTransferencia;

public class CategoriaTransferenciaDAO {
	
    public CategoriaTransferenciaDAO() {
    	
    }

    public CategoriaTransferencia getCategoryById(int categoryID) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
    	CategoriaTransferencia categoria = null;
	    em.getTransaction().begin();
	    try {
	        categoria = em.find(CategoriaTransferencia.class, categoryID);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    }
	    return categoria;
    }
    public List<CategoriaTransferencia> getAll() {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
    	List<CategoriaTransferencia> categorias = null;
        em.getTransaction().begin();
        try {
            
            Query query = em.createQuery("Select c from CategoriaTransferencia c", CategoriaTransferencia.class);
            
         
            categorias = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return categorias;
    }

}