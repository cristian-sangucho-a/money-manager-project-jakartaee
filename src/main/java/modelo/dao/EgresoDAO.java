package modelo.dao;

import java.util.Date;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;

public class EgresoDAO {

	public EgresoDAO() {

	}
	public void registerExpense(Date date, String concept, double value, CategoriaEgreso expenseCategory, Cuenta account) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
            Egreso eg = new Egreso(concept,date,-value,account,expenseCategory);
			em.persist(eg);	
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
	}

}
