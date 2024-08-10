package modelo.dao;

import java.io.*;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.dto.MovimientoDTO;
import modelo.entidades.CategoriaTransferencia;
import modelo.entidades.Cuenta;
import modelo.entidades.Transferencia;


/**
 * 
 */
public class TransferenciaDAO extends MovimientoDAO {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;

    /**
     * Default constructor
     */
    public TransferenciaDAO() {
		this.emf = Persistence.createEntityManagerFactory("Contabilidad");
    	this.em = emf.createEntityManager();
    }

    /**
     * 
     */
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
     * @param amount 
     * @param dstAccountID 
     * @param srcAccountID 
     * @param date 
     * @param concept 
     * @param category
     */
    public void transfer(double amount, Cuenta dstAccountID, Cuenta srcAccountID, Date date, String concept, CategoriaTransferencia transferCategory) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
            Transferencia transferencia = new Transferencia(concept,date,amount,srcAccountID,dstAccountID, transferCategory);
			em.persist(transferencia);	
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
    }

}