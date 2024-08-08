package controlador;

import java.io.IOException;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.dao.CategoriaEgresoDAO;
import modelo.dao.CategoriaIngresoDAO;
import modelo.dao.CategoriaTransferenciaDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.MovimientoDAO;
import modelo.dto.CategoriaResumenDTO;
import modelo.dto.MovimientoDTO;
import modelo.entidades.Cuenta;
import modelo.entidades.*;

/**
 * 
 */
@WebServlet("/ContabilidadController")
public class ContabilidadController extends HttpServlet{

    private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ruteador(req, resp);
	}
	
	//ruteador
	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "verdashboard" : req.getParameter("ruta");

	    switch (ruta) {
	        case "verdashboard":
	            this.viewDashboard(req, resp);
	            break;
	        
	    }
	}
	
	/**
     * Default constructor
     */
    public ContabilidadController() {
    }

    /**
     * @param from vienen en el cuerpo / url
     * @param to
     */
    public void viewDashboard(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        //paso 1: obtener datos
    	
    		//setear la fecha default segun la regla de negocio del caso de uso
    	
    	//Emular fechas
    	Date fechaInicio = new Date();
    	Date fechaFin = new Date();
    	
    	//paso 2: hablar con el modelo
    	CuentaDAO cuentaDAO = new CuentaDAO();
    	List<Cuenta> listaDeCuentas = cuentaDAO.getAll();
    	
    	CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
    	List<CategoriaResumenDTO> categoriasIngresoSumarized = categoriaIngresoDAO.getAllSumarized(fechaInicio, fechaFin);
    	
    	CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
    	List<CategoriaResumenDTO> categoriasEgresoSumarized = categoriaEgresoDAO.getAllSumarized(fechaInicio, fechaFin);

    	MovimientoDAO movimientoDAO = new MovimientoDAO();
    	List<MovimientoDTO> movimientos = movimientoDAO.getAll(fechaInicio, fechaFin);
    	
    	//paso 3: hablar con la vista
    	req.setAttribute("movements", movimientos);
    	req.setAttribute("accounts", listaDeCuentas);
    	req.setAttribute("incomes", categoriasIngresoSumarized);
    	req.setAttribute("expenses", categoriasEgresoSumarized);
    	
    	req.getRequestDispatcher("jsp/verdashboard.jsp").forward(req, resp);
    	//resp.sendRedirect();
    }
    
    


}