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
import modelo.entidades.Movimiento;
import modelo.entidades.Transferencia;

public class IngresoDAO extends MovimientoDAO {
	
	public IngresoDAO() {
	}
	
	protected void update(Ingreso ingreso, double value, String concept, int srcAccountID, Date date,
			int categoryID) {
		CuentaDAO cuentaDAO = new CuentaDAO();
		
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		Cuenta dstAccount = ingreso.getDstAccount();
		ingreso.setCategoria(categoriaIngresoDAO.getCategoryById(categoryID));
		ingreso.setConcept(concept);
		ingreso.setDate(date);
		ingreso.setDstAccount(dstAccount);
		
		cuentaDAO.updateBalance(-ingreso.getValue(), dstAccount.getId()); //eliminar el anterior
		
		ingreso.setValue(value);

		cuentaDAO.updateBalance(value, dstAccount.getId()); //actualizar con el nuevo
		
		updateIncome(ingreso);
	}
	
	private void updateIncome(Ingreso ingreso) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
            em.merge(ingreso);
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
	}

    public void registerIncome(Date date, String concept, double value, CategoriaIngreso incomeCategory, Cuenta dstID) {
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
			em.persist(new Ingreso(concept,date,value,dstID,incomeCategory));
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
    }
    
    protected void delete(Ingreso ingreso) {
		CuentaDAO cdao = new CuentaDAO();
    	Cuenta dstAccount = ingreso.getDstAccount();
		cdao.updateBalance(-ingreso.getValue(), dstAccount.getId());
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
			em.remove(em.find(Ingreso.class, ingreso.getId()));
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}
	}
}