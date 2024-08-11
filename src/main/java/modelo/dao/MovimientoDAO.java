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

/**
 * 
 */
public class MovimientoDAO {
	/**
	 * Default constructor
	 */
	public MovimientoDAO() {
	}

	/**
	 * @param movement
	 * @return
	 */
	public void update(MovimientoDTO movementDTO, int categoryID) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		int movementToUpdate = movementDTO.getId();
		Movimiento movement = getMovementById(movementToUpdate);
		CuentaDAO cdao = new CuentaDAO();
		Object movUpdated = typeOfMovementToUpdate(movement, cdao, movementDTO, categoryID);
		
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

	

	/**
	 * @param movement
	 */
	public void delete(Movimiento movement) {
		CuentaDAO cdao = new CuentaDAO();
		double value = movement.getValue();
		if (movement instanceof Egreso) {
			Egreso egreso = (Egreso) movement;
			Cuenta srcAccount = egreso.getSrcAccount();
			cdao.updateBalance(value, srcAccount.getId());

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

	/**
	 * @param categoryID
	 */
	public void getMovementsByCategory(int categoryID) {
		// TODO implement here
	}

	/**
	 * @param from
	 * @param to
	 */
	public void getMovementsByDate(Date from, Date to) {
		// TODO implement here
	}

	/**
	 * @param accountID public List<MovimientoDTO> getAllByAccount(int accountID) {
	 *                  EntityManager em =
	 *                  ManejoEntidadPersistencia.getEntityManager();
	 * 
	 *                  CuentaDAO cu = new CuentaDAO();
	 * 
	 * 
	 * 
	 *                  List<MovimientoDTO> movimientos = null; try { // Create and
	 *                  execute the JPQL query String jpql = "SELECT m FROM
	 *                  Movimiento m WHERE m.DSTACCOUNT_ID = :dstID OR
	 *                  m.SRCACCOUNT_ID = :srcID"; TypedQuery<MovimientoDTO> query =
	 *                  em.createQuery(jpql, MovimientoDTO.class);
	 *                  query.setParameter("dstID", cu.getByID(accountID));
	 *                  query.setParameter("srcID", cu.getByID(accountID));
	 * 
	 *                  movimientos = query.getResultList(); } finally { if (em !=
	 *                  null && em.isOpen()) { em.close(); } } return movimientos; }
	 */

	/**
	 * @param from
	 * @param to
	 * @return
	 */
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

	/**
	 * @param from
	 * @param to
	 */
	public List<MovimientoDTO> getAll(Date from, Date to) {
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		List<MovimientoDTO> movimientos = new ArrayList<>();

		try {
			// Create and execute the native SQL query
			String sql = "SELECT ID, tipo_movimiento, CONCEPT, FECHA, VALOR, Categoria_ID, DSTACCOUNT_ID, SRCACCOUNT_ID "
					+ "FROM Movimiento WHERE FECHA BETWEEN ?1 AND ?2";
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, from);
			query.setParameter(2, to);

			// Get the result list
			List<Object[]> results = query.getResultList();

			// Map each result to a MovimientoDTO
			for (Object[] result : results) {
				int id = (Integer) result[0];
				String tipoMovimiento = (String) result[1];
				String concept = (String) result[2];
				Date date = convertToDate((LocalDateTime) result[3]);
				double value = (Double) result[4];
				Integer categoriaId = (result[5] != null) ? (Integer) result[5] : 0;
	            Integer dstAccountId = (result[6] != null) ? (Integer) result[6] : 0;
	            Integer srcAccountId = (result[7] != null) ? (Integer) result[7] : 0;

				// Create a MovimientoDTO and add it to the list
				MovimientoDTO dto = new MovimientoDTO(id, srcAccountId, dstAccountId, concept, date, value,
						tipoMovimiento);
				movimientos.add(dto);
			}
		} finally {
			// Ensure that the EntityManager is closed
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
			// Create and execute the native SQL query
			String sql = "SELECT ID, tipo_movimiento, CONCEPT, FECHA, VALOR, Categoria_ID, DSTACCOUNT_ID, SRCACCOUNT_ID "
					+ "FROM Movimiento WHERE ID = ?1";
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, movimientoDTOID);

			// Get the result list
			Object[] result = (Object[]) query.getSingleResult();

			// Map each result to a MovimientoDTO
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
			// Ensure that the EntityManager is closed
			if (em != null && em.isOpen()) {
				em.close();
			}
		}

		return movimientoDTO;
	}

	/**
	 * @param movementID
	 */
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
		// Parse LocalDateTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		ZoneId defaultZoneId = ZoneId.systemDefault();

		try {
			// Convertir LocalDateTime a Date
			Date date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private Object typeOfMovementToUpdate(Movimiento movement, CuentaDAO cdao, MovimientoDTO movementDTO, int categoryID) {
		if (movement instanceof Egreso) {
			CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
			Egreso egreso = (Egreso) movement;
			Cuenta srcAccount = egreso.getSrcAccount();
			egreso.setCategoria(categoriaEgresoDAO.getCategoryById(categoryID));
			egreso.setConcept(movementDTO.getConcept());
			egreso.setDate(movementDTO.getDate());
			egreso.setSrcAccount(cdao.getByID(movementDTO.getSrcAccount()));
			cdao.updateBalance(-egreso.getValue(), srcAccount.getId()); //eliminar el anterior
			egreso.setValue(movementDTO.getValue());
			cdao.updateBalance(movementDTO.getValue(), srcAccount.getId()); //actualizar con el nuevo
			return egreso;
			
		} else if (movement instanceof Ingreso) {
			CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
			Ingreso ingreso = (Ingreso) movement;
			Cuenta dstAccount = ingreso.getDstAccount();
			ingreso.setCategoria(categoriaIngresoDAO.getCategoryById(categoryID));
			ingreso.setConcept(movementDTO.getConcept());
			ingreso.setDate(movementDTO.getDate());
			ingreso.setDstAccount(cdao.getByID(movementDTO.getDstAccount()));
			cdao.updateBalance(-ingreso.getValue(), dstAccount.getId()); //eliminar el anterior
			ingreso.setValue(movementDTO.getValue());
			cdao.updateBalance(movementDTO.getValue(), dstAccount.getId()); //actualizar con el nuevo
			return ingreso;

		} else if (movement instanceof Transferencia) {
			CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
			Transferencia transferencia = (Transferencia) movement;
			Cuenta srcAccount = transferencia.getSrcAccount();
			Cuenta dstAccount = transferencia.getDstAccount();
			transferencia.setCategoria(categoriaTransferenciaDAO.getCategoryById(categoryID));
			transferencia.setConcept(movementDTO.getConcept());
			transferencia.setDate(movementDTO.getDate());
			transferencia.setSrcAccount(cdao.getByID(movementDTO.getSrcAccount()));
			transferencia.setDstAccount(cdao.getByID(movementDTO.getDstAccount()));
			cdao.updateBalance(transferencia.getValue(), srcAccount.getId()); //eliminar el anterior
			cdao.updateBalance(-transferencia.getValue(), dstAccount.getId()); //eliminar el anterior
			transferencia.setValue(movementDTO.getValue());
			cdao.updateBalance(-movementDTO.getValue(), srcAccount.getId()); //actualizar con el nuevo
			cdao.updateBalance(movementDTO.getValue(), dstAccount.getId()); //actualizar con el nuevo
			return transferencia;
		}
		return null;
	}
}