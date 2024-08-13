package modelo .dao;

import java.text.DecimalFormat;
import java.util.*;

import jakarta.persistence.Query;
import jakarta.persistence.*;
import modelo.dto.CategoriaResumenDTO;
import modelo.entidades.Categoria;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.Cuenta;

public class CategoriaEgresoDAO extends CategoriaDAO {
	

    public CategoriaEgresoDAO() {
    	
    }

    /**
     * @param from 
     * @param to 
     * @return
     */
    public List<CategoriaResumenDTO> getAllSumarized(String from, String to) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
        List<CategoriaResumenDTO> resultList = new ArrayList<>();
        try {
        	String queryStr = "SELECT c.name, SUM(m.valor), c.id "
            		+ "FROM categoria c "
            		+ "LEFT JOIN movimiento m ON c.id = m.Categoria_ID "
            		+ "AND m.fecha BETWEEN ?1 AND ?2 "
            		+ "WHERE c.tipo_categoria = 'CATEGRESO' "
            		+ "GROUP BY c.id;";
            Query query = em.createNativeQuery(queryStr);
            query.setParameter(1, from + " 00:00:00");
            query.setParameter(2, to + " 23:59:59");
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {
                String name = (String) result[0];
                Double sum = (result[1] == null) ? 0 : (Double) result[1];
                DecimalFormat df = new DecimalFormat("#.##");
                sum = Double.parseDouble(df.format(sum));
                int id = (Integer) result[2];
                resultList.add(new CategoriaResumenDTO(name, sum, id));
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
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
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
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
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