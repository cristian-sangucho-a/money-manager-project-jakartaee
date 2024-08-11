package modelo .dao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import modelo.dto.MovimientoDTO;
import modelo.entidades.Categoria;
import modelo.entidades.CategoriaEgreso;

public class CategoriaDAO {

    public CategoriaDAO() {
    }

    public void getName() {
        // TODO implement here
    }

    public void getIncomeCategories() {
        // TODO implement here
    }

	public void updateBalance(double value) {
		// TODO Auto-generated method stub
		
	}

	public List<MovimientoDTO> getMovementsByCategory(int categoryID, Date from, Date to) {
		List<MovimientoDTO> resultList = new ArrayList<>();
		EntityManager em = ManejoEntidadPersistencia.getEntityManager();
		try {
			String queryStr = "SELECT m.* " + "FROM Movimiento m "
					+ "WHERE m.Categoria_ID = ?1 ";
			Query query = em.createNativeQuery(queryStr);
			query.setParameter(1, categoryID);
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
		// TODO Auto-generated method stub
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

}