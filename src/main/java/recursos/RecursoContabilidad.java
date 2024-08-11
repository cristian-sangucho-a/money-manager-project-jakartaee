package recursos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelo.dao.CategoriaEgresoDAO;
import modelo.dao.CategoriaIngresoDAO;
import modelo.dao.CategoriaTransferenciaDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.MovimientoDAO;
import modelo.dto.MovimientoDTO;
import modelo.entidades.Categoria;
import modelo.entidades.Cuenta;
import modelo.entidades.Movimiento;

@Path("/contabilidad")
public class RecursoContabilidad {
	
	@GET
	@Path("/cuentas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cuenta> getCuentas(){
		CuentaDAO cdao = new CuentaDAO();
		return cdao.getAll();
	}
	
	@GET
	@Path("/categorias")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Categoria> getCategorias() {
	    CategoriaEgresoDAO cedao = new CategoriaEgresoDAO();
	    CategoriaIngresoDAO cidao = new CategoriaIngresoDAO();
	    CategoriaTransferenciaDAO ctdao = new CategoriaTransferenciaDAO();
	    
	    List<Categoria> result = new ArrayList<>();	    
	    result.addAll(cedao.getAll()); 
	    result.addAll(cidao.getAll());
	    result.addAll(ctdao.getAll());
	    
	    return result;        
	}
	
	@GET
	@Path("/movimientos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MovimientoDTO> getMovimientos(){
		MovimientoDAO mdao = new MovimientoDAO();
		
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
		return mdao.getAll(calendar.getTime(), new Date());
	}
	
	

}
