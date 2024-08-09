package modelo .dao;

import java.util.*;

import jakarta.persistence.TypedQuery;
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
            String queryStr = "SELECT new modelo.dto.CategoriaResumenDTO(c.name, " +
                              "(SELECT SUM(m.valor) FROM Movimiento m WHERE m.categoria.id = c.id AND m.fecha BETWEEN :from AND :to AND m.tipoMovimiento = 'egreso')) " +
                              "FROM CategoriaEgreso c";
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