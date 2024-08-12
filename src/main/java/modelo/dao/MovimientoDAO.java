package modelo.dao;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.dto.CategoriaResumenDTO;
import modelo.dto.MovimientoDTO;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;
import modelo.entidades.Ingreso;
import modelo.entidades.Movimiento;
import modelo.entidades.Transferencia;
import modelo.entidades.Movimiento;

public class MovimientoDAO {

	public MovimientoDAO() {
	}

	public void update(MovimientoDTO movement, int categoryID) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		int movementToUpdate = movement.getId();
		Movimiento oldMovement = getMovementById(movementToUpdate);
		CuentaDAO cdao = new CuentaDAO();
		Object movUpdated = typeOfMovementToUpdate(oldMovement, cdao, movement, categoryID);

		em.getTransaction().begin();
		try {
			if (movUpdated != null) {
				em.merge(movUpdated);
				em.getTransaction().commit();
			}
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public void update(Egreso movement, double value, String concept, int srcAccountID, Date date,
			int movementID, int categoryID) {

		EgresoDAO egresoDAO = new EgresoDAO();
		egresoDAO.update(movement, value, concept, srcAccountID, date, categoryID);
	}
	
	public void update(Ingreso movement, double value, String concept, int dstAccountID, Date date,
			int movementID, int categoryID) {

		IngresoDAO ingresoDAO = new IngresoDAO();
		ingresoDAO.update(movement, value, concept, dstAccountID, date, categoryID);
	}
	
	public void update(Transferencia movement, double value, String concept,int srcAccountID, int dstAccountID, Date date,
			int movementID, int categoryID) {

		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		transferenciaDAO.update(movement, value, concept,srcAccountID, dstAccountID, date, categoryID);
	}

	public void update(Movimiento movement, double value, String concept, int srcAccountID, int dstAccountID, Date date,
			int movementID, int categoryID) throws Exception {
		
		if(movement instanceof Egreso) {
			update((Egreso) movement, value, concept, srcAccountID, date, movementID, categoryID);
			return;
		}
		
		if(movement instanceof Ingreso) {
			update((Ingreso) movement, value, concept, dstAccountID, date, movementID, categoryID);
			return;
		}
		
		if(movement instanceof Transferencia) {
			update((Transferencia) movement, value, concept, srcAccountID, dstAccountID, date, movementID, categoryID);
			return;
		}

		throw new Exception("No valio");
	}

	public void delete(Movimiento movement) {
		CuentaDAO cdao = new CuentaDAO();
		double value = movement.getValue();
		if (movement instanceof Egreso) {
			Egreso egreso = (Egreso) movement;
			Cuenta srcAccount = egreso.getSrcAccount();
			cdao.updateBalance(-value, srcAccount.getId());

		} else if (movement instanceof Ingreso) {
			Ingreso ingreso = (Ingreso) movement;
			Cuenta dstAccount = ingreso.getDstAccount();
			cdao.updateBalance(-value, dstAccount.getId());

		} else if (movement instanceof Transferencia) {
			Transferencia transferencia = (Transferencia) movement;
			Cuenta srcAccount = transferencia.getSrcAccount();
			Cuenta dstAccount = transferencia.getDstAccount();
			cdao.updateBalance(value, srcAccount.getId());
			cdao.updateBalance(-value, dstAccount.getId());
		}

		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		em.getTransaction().begin();
		try {
			em.remove(em.find(Movimiento.class, movement.getId()));
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}
	}

	public List<MovimientoDTO> getAllByAccount(int accountID) {
		List<MovimientoDTO> resultList = new ArrayList<>();
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		try {
			String queryStr = "SELECT m.* " + "FROM Movimiento m "
					+ "WHERE m.DSTACCOUNT_ID = ?1 OR m.SRCACCOUNT_ID = ?2 ";
			Query query = em.createNativeQuery(queryStr);
			query.setParameter(1, accountID);
			query.setParameter(2, accountID);
			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				int id = (Integer) result[0];
				String tipoMovimiento = (String) result[1];
				String concept = (String) result[2];
				System.out.print("/////////////////////////////////" + result[3]);
				Date date = convertToDate((LocalDateTime) result[3]);
				double value = (Double) result[4];
				int dst = (result[6] == null) ? 0 : (Integer) result[6];
				int src = (result[7] == null) ? 0 : (Integer) result[7];

				MovimientoDTO mvn = new MovimientoDTO(id, src, dst, concept, date, value, tipoMovimiento);

				System.out.print(mvn);

				resultList.add(mvn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return resultList;
	}

	public List<MovimientoDTO> getAll(Date from, Date to) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		List<MovimientoDTO> movimientos = new ArrayList<>();
		try {
			String sql = "SELECT ID, tipo_movimiento, CONCEPT, FECHA, VALOR, Categoria_ID, DSTACCOUNT_ID, SRCACCOUNT_ID "
					+ "FROM Movimiento WHERE FECHA BETWEEN ?1 AND ?2";
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, from);
			query.setParameter(2, to);
			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				int id = (Integer) result[0];
				String tipoMovimiento = (String) result[1];
				String concept = (String) result[2];
				Date date = convertToDate((LocalDateTime) result[3]);
				double value = (Double) result[4];
				Integer categoriaId = (result[5] != null) ? (Integer) result[5] : 0;
				Integer dstAccountId = (result[6] != null) ? (Integer) result[6] : 0;
				Integer srcAccountId = (result[7] != null) ? (Integer) result[7] : 0;
				MovimientoDTO dto = new MovimientoDTO(id, srcAccountId, dstAccountId, concept, date, value,
						tipoMovimiento);
				movimientos.add(dto);
			}
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return movimientos;
	}

	public MovimientoDTO getMovementDTOById(int movimientoDTOID) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		MovimientoDTO movimientoDTO = new MovimientoDTO();
		try {
			String sql = "SELECT ID, tipo_movimiento, CONCEPT, FECHA, VALOR, Categoria_ID, DSTACCOUNT_ID, SRCACCOUNT_ID "
					+ "FROM Movimiento WHERE ID = ?1";
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, movimientoDTOID);
			Object[] result = (Object[]) query.getSingleResult();
			if (result != null) {
				Date date = convertToDate((LocalDateTime) result[3]);
				Integer categoriaId = (result[5] != null) ? (Integer) result[5] : 0;
				Integer dstAccountId = (result[6] != null) ? (Integer) result[6] : 0;
				Integer srcAccountId = (result[7] != null) ? (Integer) result[7] : 0;

				movimientoDTO.setId((Integer) result[0]);
				movimientoDTO.setTipo_movimiento((String) result[1]);
				movimientoDTO.setConcept((String) result[2]);
				movimientoDTO.setDate(date);
				movimientoDTO.setValue((Double) result[4]);
				movimientoDTO.setDstAccount(dstAccountId);
				movimientoDTO.setSrcAccount(srcAccountId);
			}
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return movimientoDTO;
	}

	public Movimiento getMovementById(int movementID) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		Movimiento movimiento = null;
		em.getTransaction().begin();
		try {
			movimiento = em.find(Movimiento.class, movementID);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		return movimiento;
	}

	private Date convertToDate(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		ZoneId defaultZoneId = ZoneId.systemDefault();
		try {
			Date date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object typeOfMovementToUpdate(Movimiento oldMovement, CuentaDAO cdao, MovimientoDTO movementDTO,
			int categoryID) {
		if (oldMovement instanceof Egreso) {
			CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
			Egreso egreso = (Egreso) oldMovement;
			Cuenta srcAccount = egreso.getSrcAccount();
			egreso.setCategoria(categoriaEgresoDAO.getCategoryById(categoryID));
			egreso.setConcept(movementDTO.getConcept());
			egreso.setDate(movementDTO.getDate());
			egreso.setSrcAccount(cdao.getByID(movementDTO.getSrcAccount()));
			cdao.updateBalance(-egreso.getValue(), srcAccount.getId()); // eliminar el anterior

			egreso.setValue(movementDTO.getValue());
			cdao.updateBalance(movementDTO.getValue(), srcAccount.getId()); // actualizar con el nuevo

			return egreso;

		} else if (oldMovement instanceof Ingreso) {
			CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
			Ingreso ingreso = (Ingreso) oldMovement;
			Cuenta dstAccount = ingreso.getDstAccount();
			ingreso.setCategoria(categoriaIngresoDAO.getCategoryById(categoryID));
			ingreso.setConcept(movementDTO.getConcept());
			ingreso.setDate(movementDTO.getDate());
			ingreso.setDstAccount(cdao.getByID(movementDTO.getDstAccount()));
			cdao.updateBalance(-ingreso.getValue(), dstAccount.getId()); // eliminar el anterior

			ingreso.setValue(movementDTO.getValue());
			cdao.updateBalance(movementDTO.getValue(), dstAccount.getId());// actualizar con el nuevo
			return ingreso;

		} else if (oldMovement instanceof Transferencia) {
			CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
			Transferencia transferencia = (Transferencia) oldMovement;
			Cuenta srcAccount = transferencia.getSrcAccount();
			Cuenta dstAccount = transferencia.getDstAccount();
			transferencia.setCategoria(categoriaTransferenciaDAO.getCategoryById(categoryID));
			transferencia.setConcept(movementDTO.getConcept());
			transferencia.setDate(movementDTO.getDate());
			transferencia.setSrcAccount(cdao.getByID(movementDTO.getSrcAccount()));
			transferencia.setDstAccount(cdao.getByID(movementDTO.getDstAccount()));
			cdao.updateBalance(transferencia.getValue(), srcAccount.getId()); // eliminar el anterior
			cdao.updateBalance(-transferencia.getValue(), dstAccount.getId()); // eliminar el anterior
			transferencia.setValue(movementDTO.getValue());
			cdao.updateBalance(-movementDTO.getValue(), srcAccount.getId()); // actualizar con el nuevo
			cdao.updateBalance(movementDTO.getValue(), dstAccount.getId()); // actualizar con el nuevo
			return transferencia;
		}
		return null;
	}
}