package modelo.dao;

import java.util.Date;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;
import modelo.entidades.Movimiento;

public class EgresoDAO extends MovimientoDAO{

	public EgresoDAO() {

	}
	
	protected void update(Egreso egreso, double value, String concept, int srcAccountID, Date date,
			int categoryID) {
		CuentaDAO cuentaDAO = new CuentaDAO();
		
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		Cuenta srcAccount = egreso.getSrcAccount();
		egreso.setCategoria(categoriaEgresoDAO.getCategoryById(categoryID));
		egreso.setConcept(concept);
		egreso.setDate(date);
		egreso.setSrcAccount(srcAccount);
		
		cuentaDAO.updateBalance(-egreso.getValue(), srcAccount.getId()); //eliminar el anterior
		
		egreso.setValue(value);

		cuentaDAO.updateBalance(value, srcAccount.getId()); //actualizar con el nuevo
		
		updateExpense(egreso);
	}
	
	private void updateExpense(Egreso egreso) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
            em.merge(egreso);
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
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
