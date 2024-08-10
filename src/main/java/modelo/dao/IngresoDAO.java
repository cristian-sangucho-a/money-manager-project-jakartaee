package modelo.dao;

import java.io.*;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.dto.MovimientoDTO;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.CategoriaIngreso;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;
import modelo.entidades.Ingreso;

/**
 * 
 */
public class IngresoDAO extends MovimientoDAO {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	
	public IngresoDAO() {
		this.emf = Persistence.createEntityManagerFactory("Contabilidad");
    	this.em = emf.createEntityManager();
	}
	
    public void getAllByAccount() {
        // TODO implement here
    }

    /**
     * @param from 
     * @param to
     * @return 
     */
    public List<MovimientoDTO> getAll(Date from, Date to) {
        // TODO implement here
    	return null;
    }

    /**
     * @param date 
     * @param concept 
     * @param value 
     * @param category 
     * @param dstID
     */

    public void registerIncome(Date date, String concept, double value, CategoriaIngreso incomeCategory, Cuenta dstID) {
    	em.getTransaction().begin();
		try {
			em.persist(new Ingreso(concept,date,value,dstID,incomeCategory));
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
    }

}