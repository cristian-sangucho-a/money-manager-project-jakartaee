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

	
	
	public void update(Movimiento movement, double value, String concept, int srcAccountID, int dstAccountID, Date date,
			int movementID, int categoryID) throws Exception {

		if (movement instanceof Egreso) {
			EgresoDAO egresoDAO = new EgresoDAO();
			egresoDAO.update((Egreso) movement, value, concept, srcAccountID, date, categoryID);
			return;
		}

		if (movement instanceof Ingreso) {
			IngresoDAO ingresoDAO = new IngresoDAO();
			ingresoDAO.update((Ingreso) movement, value, concept, dstAccountID, date, categoryID);
			return;
		}

		if (movement instanceof Transferencia) {
			TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
			transferenciaDAO.update((Transferencia) movement, value, concept, srcAccountID, dstAccountID, date,
					 categoryID);
			return;
		}

		throw new Exception("No delegó la tarea de actualizar a los hijos");
	}

	public void delete(Movimiento movement) throws Exception {

		if (movement instanceof Egreso) {
			EgresoDAO egresoDAO = new EgresoDAO();
			egresoDAO.delete((Egreso) movement);
			return;
		}

		if (movement instanceof Ingreso) {
			IngresoDAO ingresoDAO = new IngresoDAO();
			ingresoDAO.delete((Ingreso) movement);
			return;
		}

		if (movement instanceof Transferencia) {
			TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
			transferenciaDAO.delete((Transferencia) movement);
			return;
		}

		throw new Exception("No delegó la tarea borrar a los hijos");
	}

	public List<MovimientoDTO> getAllByAccount(int accountID) {
		List<MovimientoDTO> resultList = new ArrayList<>();
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		try {
			String queryStr = "SELECT m.* " +
	                  "FROM Movimiento m " +
	                  "WHERE m.DSTACCOUNT_ID = ?1 " +
	                  "OR m.SRCACCOUNT_ID = ?2";

			Query query = em.createNativeQuery(queryStr);
			query.setParameter(1, accountID);
			query.setParameter(2, accountID);
			List<Object[]> results = query.getResultList();
			
			for (Object[] result : results) {
				int id = (Integer) result[0];
				String tipoMovimiento = (String) result[1];
				String concept = (String) result[2];
				Date date = convertToDate((LocalDateTime) result[3]);
				double value = (Double) result[4];
				int dst = (result[6] == null) ? 0 : (Integer) result[6];
				int src = (result[7] == null) ? 0 : (Integer) result[7];

				MovimientoDTO mvn = new MovimientoDTO(id, src, dst, concept, date, value, tipoMovimiento);

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

	public List<MovimientoDTO> getAll(String from, String to) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		List<MovimientoDTO> movimientos = new ArrayList<>();
		try {
			String sql = "SELECT ID, tipo_movimiento, CONCEPT, FECHA, VALOR, Categoria_ID, DSTACCOUNT_ID, SRCACCOUNT_ID "
					+ "FROM Movimiento WHERE FECHA BETWEEN ?1 AND ?2";
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, from + " 00:00:00");
			query.setParameter(2, to + " 23:59:59");
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

	private Date convertToDate(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		ZoneId defaultZoneId = ZoneId.systemDefault();
		try {
			Date date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//TODO
		return null;
		//throw new Exception("Fecha enviada no válida");
	}
}