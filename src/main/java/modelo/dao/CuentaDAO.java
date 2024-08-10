package modelo .dao;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Cuenta;


public class CuentaDAO {
	
    public CuentaDAO() {
    	
    }

 
    public List<Cuenta> getAll() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Contabilidad");
    	EntityManager em  = emf.createEntityManager();
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
        em.close();
        emf.close();
        return cuentas;
    }

 

  
    public double getBalance() {
        
        return 0;
    }

  
    public void updateBalance(double value, int accountID) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Contabilidad");
    	EntityManager em  = emf.createEntityManager();
        em.getTransaction().begin();
        try {
        	Cuenta cuenta = em.find(Cuenta.class, accountID);
            if (cuenta != null) {
                cuenta.setBalance(cuenta.getBalance() + value);
                System.out.print(cuenta.getId());
                em.merge(cuenta);
                em.getTransaction().commit();
            }
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            emf.close();
        }
    }


	public Cuenta getByID(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Contabilidad");
    	EntityManager em  = emf.createEntityManager();
	    em.getTransaction().begin();
	    try {
	        Cuenta cuenta = em.find(Cuenta.class, id);
	        em.getTransaction().commit();
	        return cuenta;
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	        
	    }
	    em.close();
        emf.close();
	    return null;
	}

}