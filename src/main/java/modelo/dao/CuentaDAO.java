package modelo .dao;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Cuenta;


public class CuentaDAO {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	
    public CuentaDAO() {
    	this.emf = Persistence.createEntityManagerFactory("Contabilidad");
    	this.em = emf.createEntityManager();
    }

 
    public List<Cuenta> getAll() {
        List<Cuenta> cuentas = null;
        em.getTransaction().begin();
        try {
            
            Query query = em.createQuery("Select c from Cuenta c", Cuenta.class);
            
            
            cuentas = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return cuentas;
    }

 
    public Cuenta getByID(int accountID) {
        
        return null;
    }

  
    public double getBalance() {
        
        return 0;
    }

  
    public void updateBalance(double value) {
        
    }

}