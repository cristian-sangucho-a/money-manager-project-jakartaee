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
	public void registerExpense(Date date, String concept, double value, CategoriaEgreso expenseCategory, Cuenta accountID) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
            Egreso eg = new Egreso(concept,date,-value,accountID,expenseCategory);
			em.persist(eg);	
			System.out.print("////////////"+eg.getSrcAccount().getId()+ "Take me back to the night we met");
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
	}

}
