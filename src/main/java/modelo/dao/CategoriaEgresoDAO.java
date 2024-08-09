package modelo .dao;

import java.util.*;

import jakarta.persistence.*;
import modelo.dto.CategoriaResumenDTO;

/**
 * 
 */
public class CategoriaEgresoDAO extends CategoriaDAO {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;

    public CategoriaEgresoDAO() {
    	this.emf = Persistence.createEntityManagerFactory("Contabilidad");
    	this.em = emf.createEntityManager();
    }

    /**
     * @param from 
     * @param to 
     * @return
     */
    public List<CategoriaResumenDTO> getAllSumarized(Date from, Date to) {
    	List<CategoriaResumenDTO> resultList = new ArrayList<>();
        try {
            String queryStr = "SELECT new modelo.dto.CategoriaResumenDTO(c.name, SUM(e.value)) " +
                              "FROM CategoriaEgreso c JOIN Egreso e ON c.id = e.idCategoriaEgreso " +
                              "WHERE e.date BETWEEN :from AND :to " +
                              "GROUP BY c.name";
            TypedQuery<CategoriaResumenDTO> query = em.createQuery(queryStr, CategoriaResumenDTO.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            resultList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return resultList;
    }

}