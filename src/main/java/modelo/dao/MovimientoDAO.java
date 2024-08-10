package modelo .dao;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.dto.CategoriaResumenDTO;
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
     * public List<MovimientoDTO> getAllByAccount(int accountID) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
    	
    	CuentaDAO cu = new CuentaDAO();
    	
    	
    	
        List<MovimientoDTO> movimientos = null;
        try {
            // Create and execute the JPQL query
            String jpql = "SELECT m FROM Movimiento m WHERE m.DSTACCOUNT_ID = :dstID OR m.SRCACCOUNT_ID = :srcID";
            TypedQuery<MovimientoDTO> query = em.createQuery(jpql, MovimientoDTO.class);
            query.setParameter("dstID", cu.getByID(accountID));
            query.setParameter("srcID", cu.getByID(accountID));
            
            movimientos = query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    	return movimientos;
    }
     */
    
    
    /**
     * @param from 
     * @param to 
     * @return
     */
    public List<MovimientoDTO> getAllByAccount(int accountID) {
        List<MovimientoDTO> resultList = new ArrayList<>();
        EntityManager em = ManejoEntidadPersistencia.getEntityManager();
        try {
            String queryStr = "SELECT m.* " +
                              "FROM Movimiento m " +
                              "WHERE m.DSTACCOUNT_ID = ?1 OR m.SRCACCOUNT_ID = ?2 "
                              ;
            Query query = em.createNativeQuery(queryStr);
            query.setParameter(1, accountID);
            query.setParameter(2, accountID);
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {
                int id = (Integer) result[0];
                String tipoMovimiento = (String) result[1];
                String concept = (String) result[2];
                System.out.print("/////////////////////////////////"+result[3]);
                Date date = convertToDate((LocalDateTime) result[3]);
                double value = (Double) result[4];
                int dst=(result[6] == null)? 0 : (Integer) result[6];
                int src=(result[7] == null)? 0 : (Integer) result[7];
                

                MovimientoDTO mvn = new MovimientoDTO(id,src,dst,concept,date,value,tipoMovimiento);
                
                System.out.print(mvn);
                
                resultList.add(mvn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return resultList;
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
    
    private Date convertToDate(LocalDateTime localDateTime) {
        // Parse LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        
        try {
            // Convertir LocalDateTime a Date
            Date date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}