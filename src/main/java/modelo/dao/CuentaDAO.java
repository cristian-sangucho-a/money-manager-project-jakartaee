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
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
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

        return cuentas;
    }

 

  
    public double getBalance(int accountID) {
        
        EntityManager em = ManejoEntidadPersistencia.getEntityManager();
        em.getTransaction().begin();
        try {
        	Cuenta cuenta = em.find(Cuenta.class, accountID);
        	if(cuenta!=null) {
        		return cuenta.getBalance();
        	}
        }catch(Exception e) {
        	if(em.getTransaction().isActive()) {
        		em.getTransaction().rollback();
        	}
        }
        em.close();
        return 0;
    }
    
    public double getTotalAccounts() {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
        List<Cuenta> cuentas = null;
        double totalCuentas = 0;
        em.getTransaction().begin();
        try {
            
            Query query = em.createQuery("Select c from Cuenta c", Cuenta.class);
            cuentas = query.getResultList();
            em.getTransaction().commit();
            for (Cuenta cuenta : cuentas) {
				totalCuentas+=cuenta.getBalance();
			}
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        em.close();
        return totalCuentas;
    }

  
    public void updateBalance(double value, int accountID) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
        em.getTransaction().begin();
        try {
        	Cuenta cuenta = em.find(Cuenta.class, accountID);
            if (cuenta != null) {
                cuenta.setBalance(cuenta.getBalance() + value);
                System.out.print(cuenta.getId());
                em.getTransaction().commit();
            }
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();

        }
    }


	public Cuenta getByID(int id) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
	    em.getTransaction().begin();
	    try {
	        Cuenta cuenta = em.find(Cuenta.class, id);
	        em.getTransaction().commit();
            System.out.print(cuenta.getId());
	        return cuenta;
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	        
	    }
	    em.close();

	    return null;
	}

}