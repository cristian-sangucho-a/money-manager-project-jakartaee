package modelo .dao;

import java.util.*;

import modelo.dto.MovimientoDTO;
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
    public void update(Movimiento movement) {
        // TODO implement here
    }

    /**
     * @param movement
     */
    public void delete(Movimiento movement) {
        // TODO implement here
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
     * @param accountID
     */
    public List<Movimiento> getAllByAccount(int accountID) {
        // TODO implement here
    	return null;
    }

    /**
     * 
     */
    public void getAll() {
        // TODO implement here
    }

    /**
     * @param from 
     * @param to
     */
    public List<MovimientoDTO> getAll(Date from, Date to) {
        // TODO implement here
    	return null;
    }

    /**
     * @param movementID
     */
    public void getMovementById(int movementID) {
        // TODO implement here
    }

}