package modelo.dao;

import java.util.Date;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;

public class EgresoDAO {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	public EgresoDAO() {
		this.emf = Persistence.createEntityManagerFactory("Contabilidad");
    	this.em = emf.createEntityManager();
	}
	public void registerExpense(Date date, String concept, double value, CategoriaEgreso expenseCategory, Cuenta account) {
		em.getTransaction().begin();
		try {
			em.persist(new Egreso(concept,date,-value,account,expenseCategory));
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

}
