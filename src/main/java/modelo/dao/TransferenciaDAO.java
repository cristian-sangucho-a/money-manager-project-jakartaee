package modelo.dao;

import java.io.*;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.dto.MovimientoDTO;
import modelo.entidades.CategoriaTransferencia;
import modelo.entidades.Cuenta;
import modelo.entidades.Ingreso;
import modelo.entidades.Transferencia;

public class TransferenciaDAO extends MovimientoDAO {

    public TransferenciaDAO() {
    }
    
    
    public void update(Transferencia transfer, double value, String concept, int srcAccountID, int dstAccountID,
			Date date, int categoryID) {
		CuentaDAO cuentaDAO = new CuentaDAO();
		
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		Cuenta srcAccount = transfer.getSrcAccount();
		Cuenta dstAccount = transfer.getDstAccount();
		transfer.setCategoria(categoriaTransferenciaDAO.getCategoryById(categoryID));
		transfer.setConcept(concept);
		transfer.setDate(date);
		transfer.setDstAccount(dstAccount);
		
		cuentaDAO.updateBalance(-transfer.getValue(), dstAccount.getId()); //eliminar el anterior
		cuentaDAO.updateBalance(transfer.getValue(), srcAccount.getId()); //eliminar el anterior
		
		transfer.setValue(value);

		cuentaDAO.updateBalance(value, dstAccount.getId()); //actualizar con el nuevo
		cuentaDAO.updateBalance(-value, srcAccount.getId()); //actualizar con el nuevo
		
		updateIncome(transfer);
	}
    
    private void updateIncome(Transferencia transferencia) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
            em.merge(transferencia);
			em.getTransaction().commit();
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
	}

    public void transfer(double amount, Cuenta dstAccount, Cuenta srcAccount, Date date, String concept, CategoriaTransferencia transferCategory) {
    	CuentaDAO cdao = new CuentaDAO();
    	cdao.updateBalance(amount, dstAccount.getId());
    	cdao.updateBalance(-amount, srcAccount.getId());
    	EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
            Transferencia transferencia = new Transferencia(concept,date,amount,srcAccount,dstAccount, transferCategory);
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