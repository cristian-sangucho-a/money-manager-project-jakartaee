package modelo .dao;

import java.util.*;

import jakarta.persistence.Query;
import jakarta.persistence.*;
import modelo.dto.CategoriaResumenDTO;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.Cuenta;

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
            String queryStr = "SELECT c.name, SUM(m.valor) " +
                              "FROM Movimiento m, CategoriaIngreso c " +
                              "WHERE m.fecha BETWEEN ?1 AND ?2 " +
                              "AND m.tipo_movimiento = 'egreso' " +
                              "AND m.Categoria_ID = c.id " +
                              "GROUP BY c.name";
            Query query = em.createNativeQuery(queryStr);
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {n
                String name = (String) result[0];
                Double sum = (Double) result[1];
                resultList.add(new CategoriaResumenDTO(name, sum));
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
    
    public List<CategoriaEgreso> getAll() {
    	List<CategoriaEgreso> categorias = null;
        em.getTransaction().begin();
        try {
            
            Query query = em.createQuery("Select c from CategoriaEgreso c", CategoriaEgreso.class);
            
         
            categorias = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return categorias;
    }
    
    public CategoriaEgreso getCategoryById(int categoryId) {
    	CategoriaEgreso categoria = null;
	    em.getTransaction().begin();
	    try {
	        categoria = em.find(CategoriaEgreso.class, categoryId);
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