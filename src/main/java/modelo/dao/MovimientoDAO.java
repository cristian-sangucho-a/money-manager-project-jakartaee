package modelo .dao;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.dto.MovimientoDTO;
import modelo.entidades.Movimiento;

/**
 * 
 */
public class MovimientoDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

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
    public List<Movimiento> getAllByAccount(int accountID) {
        // TODO implement here
    	return null;
    }

    /**
     * 
     */
    public void getAll() {
        // TODO implement here
    }

    /**
     * @param from 
     * @param to
     */
    public List<MovimientoDTO> getAll(Date from, Date to) {
        this.emf = Persistence.createEntityManagerFactory("Contabilidad");
        this.em = emf.createEntityManager();
        
        List<MovimientoDTO> movimientos = null;
        try {
            // Create and execute the JPQL query
            String jpql = "SELECT m.* FROM Movimiento m WHERE m.date BETWEEN :from AND :to";
            TypedQuery<MovimientoDTO> query = em.createQuery(jpql, MovimientoDTO.class);
            query.setParameter("from", from);
            query.setParameter("to", to);

            movimientos = query.getResultList();
        } finally {
            // Ensure that the EntityManager and EntityManagerFactory are closed
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }

    	return movimientos;
    }

    /**
     * @param movementID
     */
    public void getMovementById(int movementID) {
        // TODO implement here
    }
}