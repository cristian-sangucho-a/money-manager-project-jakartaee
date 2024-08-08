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
    public void delete(void movement) {
        // TODO implement here
    }

    /**
     * @param categoryID
     */
    public void getMovementsByCategory(void categoryID) {
        // TODO implement here
    }

    /**
     * @param from 
     * @param to
     */
    public void getMovementsByDate(void from, void to) {
        // TODO implement here
    }

    /**
     * @param accountID
     */
    public void getAllByAccount(void accountID) {
        // TODO implement here
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
    public void getMovementById(void movementID) {
        // TODO implement here
    }

}