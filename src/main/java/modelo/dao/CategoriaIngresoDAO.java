package modelo .dao;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.dto.CategoriaResumenDTO;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.CategoriaIngreso;



/**
 * 
 */
public class CategoriaIngresoDAO extends CategoriaDAO {

	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	
    public CategoriaIngresoDAO() {
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
                              "AND m.tipo_movimiento = 'ingreso' " +
                              "AND m.Categoria_ID = c.id " +
                              "GROUP BY c.name";
            Query query = em.createNativeQuery(queryStr);
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {
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

    public List<CategoriaIngreso> getAll() {
    	List<CategoriaIngreso> categorias = null;
        em.getTransaction().begin();
        try {
            
            Query query = em.createQuery("Select c from CategoriaIngreso c", CategoriaIngreso.class);
            
         
            categorias = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return categorias;
    }

    public CategoriaIngreso getCategoryById(int categoryId) {
    	CategoriaIngreso categoria = null;
	    em.getTransaction().begin();
	    try {
	        categoria = em.find(CategoriaIngreso.class, categoryId);
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