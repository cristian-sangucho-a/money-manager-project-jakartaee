package modelo .dao;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.dto.MovimientoDTO;
import modelo.entidades.Movimiento;
import modelo.entidades.Movimiento;

/**
 * 
 */
public class MovimientoDAO {
    /**
     * Default constructor
     */
    public MovimientoDAO() {
    }

    /**
     * @param movement 
     * @return
     */
    public void update(Movimiento movement) {
        // TODO implement here
    }

    /**
     * @param movement
     */
    public void delete(Movimiento movement) {
        // TODO implement here
    }

    /**
     * @param categoryID
     */
    public void getMovementsByCategory(int categoryID) {
        // TODO implement here
    }

    /**
     * @param from 
     * @param to
     */
    public void getMovementsByDate(Date from, Date to) {
        // TODO implement here
    }

    /**
     * @param accountID
     */
    public List<MovimientoDTO> getAllByAccount(int accountID) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
        List<MovimientoDTO> movimientos = null;
        try {
            // Create and execute the JPQL query
            String jpql = "SELECT m FROM Movimiento m WHERE m.DSTACCOUNT_ID = :dstID OR m.SRCACCOUNT_ID = :srcID";
            TypedQuery<MovimientoDTO> query = em.createQuery(jpql, MovimientoDTO.class);
            query.setParameter("dstID", accountID);
            query.setParameter("srcID", accountID);
            
            movimientos = query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    	return movimientos;
    }


    /**
     * @param from 
     * @param to
     */
    public List<MovimientoDTO> getAll(Date from, Date to) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
        
        List<MovimientoDTO> movimientos = null;
        try {
            // Create and execute the JPQL query
            String jpql = "SELECT m FROM Movimiento m WHERE m.fecha BETWEEN :from AND :to";
            TypedQuery<MovimientoDTO> query = em.createQuery(jpql, MovimientoDTO.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            
            movimientos = query.getResultList();
        } finally {
            // Ensure that the EntityManager and EntityManagerFactory are closed
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    	return movimientos;
    }

    /**
     * @param movementID
     */
    public Movimiento getMovementById(int movementID) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
    	Movimiento Movimiento = null;
	    em.getTransaction().begin();
	    try {
	        Movimiento = em.find(Movimiento.class, movementID);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    }
	    return Movimiento;
    }
}