package controlador;

import java.io.IOException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.dao.CategoriaDAO;
import modelo.dao.CategoriaEgresoDAO;
import modelo.dao.CategoriaIngresoDAO;
import modelo.dao.CategoriaTransferenciaDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.EgresoDAO;
import modelo.dao.IngresoDAO;
import modelo.dao.MovimientoDAO;
import modelo.dao.TransferenciaDAO;
import modelo.dto.CategoriaResumenDTO;
import modelo.dto.MovimientoDTO;
import modelo.entidades.*;
import java.time.LocalDate;

@WebServlet("/ContabilidadController")
public class ContabilidadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String fromDefault = java.time.LocalDate.now().withDayOfMonth(1).toString();;
	private final String toDefault = java.time.LocalDate.now().toString();

	public ContabilidadController() {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ruteador(req, resp);
	}

	// ruteador
	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "verdashboard" : req.getParameter("ruta");

		switch (ruta) {
		case "eliminarmovimiento":
			this.deleteMovement(req, resp);
			break;
		case "actualizarmovimiento":
			this.updateMovement(req, resp);
			break;
		case "confirmaractualizarmovimiento":
			this.confirmUpdateOfMovement(req, resp);
			break;
		case "verdashboard":
			this.viewDashboard(req, resp);
			break;
		case "vercuenta":
			this.viewAccount(req, resp);
			break;
		case "registraregreso":
			this.registerExpense(req, resp);
			break;
		case "confirmarregistroegreso":
			this.confirmRegisterExpense(req, resp);
			break;
		case "registraringreso":
			this.registerIncome(req, resp);
			break;
		case "confirmarregistroingreso":
			this.confirmRegisterIncome(req, resp);
			break;
		case "registrartransferencia":
			this.transfer(req, resp);
			break;
		case "confirmartransferencia":
			this.confirmTransfer(req, resp);
			break;
		case "vercategoria":
			this.viewCategory(req, resp);
			break;
		case "cancelar":
			this.cancel(req, resp);
			break;
		default:
			this.error(req, resp);
			break;
		}
	}

	private void confirmUpdateOfMovement(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			MovimientoDAO movimientoDAO = new MovimientoDAO();
			CuentaDAO cuentaDAO = new CuentaDAO();
			


			// 1. Obtener datos
			double value = Double.parseDouble(req.getParameter("value"));
			String concept = req.getParameter("concept");
			int srcAccountID = (req.getParameter("srcAccountID") == null) ? 0
					: Integer.parseInt(req.getParameter("srcAccountID"));
			int dstAccountID = (req.getParameter("dstAccountID") == null) ? 0
					: Integer.parseInt(req.getParameter("dstAccountID"));
			Date date = convertToDate(req.getParameter("date"));
			int movementID = Integer.parseInt(req.getParameter("movementID"));
			int categoryID = Integer.parseInt(req.getParameter("categoryID"));
			String movementType = req.getParameter("movementType");


			Cuenta cuentaSrc = cuentaDAO.getByID(srcAccountID);
			Cuenta cuentaDst = cuentaDAO.getByID(dstAccountID);
			
			Movimiento movement = movimientoDAO.getMovementById(movementID);
			
			boolean approveExpense = false;
			
			if(movementType.equals("INGRESO")) {
				approveExpense = value > 0;
			}
			
			if(movementType.equals("TRANSFERENCIA")) {
				approveExpense = value <= cuentaSrc.getBalance() + movement.getValue() && value > 0;
			}
			
			if(movementType.equals("EGRESO")) {
				approveExpense = value < 0 && -value <= cuentaSrc.getBalance() - movement.getValue();
			}
			
			

			if (!approveExpense) {
				req.setAttribute("approveExpense", approveExpense);
				this.updateMovement(req, resp);
				return;
			}
			
			// 2. Hablar con el modelo
			

			movimientoDAO.update(movement, value, concept, srcAccountID, dstAccountID, date, movementID, categoryID);

			// 3. Hablar con la vista
			this.viewDashboard(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateMovement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		// 1. Obtener datos
		int movementID = Integer.parseInt(req.getParameter("movementID"));
		// 2. Hablar con el modelo
		Movimiento movimiento = movimientoDAO.getMovementById(movementID);

		MovimientoDTO movementDTO = movimientoDAO.getMovementDTOById(movementID); // Considerar el metodo en diseno
		Object movementsCategories = null;
		if (movimiento instanceof Egreso) {
			CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
			movementsCategories = categoriaEgresoDAO.getAll();
		} else if (movimiento instanceof Ingreso) {
			CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
			movementsCategories = categoriaIngresoDAO.getAll();
		} else if (movimiento instanceof Transferencia) {
			CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
			movementsCategories = categoriaTransferenciaDAO.getAll();
		}
		// 3. Hablar con la vista
		req.setAttribute("movement", movementDTO);
		req.setAttribute("movementsCategories", movementsCategories);
		req.getRequestDispatcher("jsp/actualizarmovimiento.jsp").forward(req, resp);
	}

	private void deleteMovement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			MovimientoDAO movimientoDAO = new MovimientoDAO();
			// 1. Obtener datos
			int movementID = Integer.parseInt(req.getParameter("movementID"));
			// 2. Hablar con el modelo
			Movimiento mov = movimientoDAO.getMovementById(movementID);
			movimientoDAO.delete(mov);
			// 3. Hablar con la vista
			this.viewDashboard(req, resp);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private void viewCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		// 1. Obtener datos
		Date from;
		Date to;
		String fromString = (String) req.getAttribute("from");
		String toString = (String) req.getAttribute("to");
		if (fromString == null || toString == null) {
			from = convertToDate(fromDefault);
			to = convertToDate(toDefault);
		} else {
			from = convertToDate(fromString);
			to = convertToDate(toString);
		}
		if (!isAValidRangeOfDates(from, to)) {
			from = convertToDate(fromDefault);
			to = convertToDate(toDefault);
		}
		int categoryID = Integer.parseInt(req.getParameter("categoryID"));

		// 2. Hablar con el modelo
		List<MovimientoDTO> movimientos = categoriaDAO.getMovementsByCategory(categoryID, from, to);

		// 3. Hablar con la vista
		req.setAttribute("movements", movimientos);
		req.getRequestDispatcher("jsp/vercategoria.jsp").forward(req, resp);

	}

	private void error(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("jsp/error.jsp");
	}

	private void confirmTransfer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		// 1. Obtener datos
		double amount = Double.parseDouble(req.getParameter("amount"));
		int dstAccountID = Integer.parseInt(req.getParameter("dstAccountID"));
		int srcAccountID = Integer.parseInt(req.getParameter("srcAccountID"));
		Date date = convertToDate(req.getParameter("date"));
		String concept = req.getParameter("concept");
		int categoriaTransferenciaID = Integer.parseInt(req.getParameter("categoryID"));

		CategoriaTransferencia category = categoriaTransferenciaDAO.getCategoryById(categoriaTransferenciaID);
		Cuenta dstAccount = cuentaDAO.getByID(dstAccountID);
		Cuenta srcAccount = cuentaDAO.getByID(srcAccountID);
		double balance = cuentaDAO.getBalance(srcAccountID);
		// 2. Hablar con el modelo
		boolean approveExpense = amount <= balance && amount > 0;

		if (!approveExpense) {
			req.setAttribute("approveExpense", approveExpense);
			this.registerExpense(req, resp);
			return;
		}
		
		transferenciaDAO.transfer(amount, dstAccount, srcAccount, date, concept, category);

		req.setAttribute("accountID", srcAccount.getId());

		// 3. Hablar con la vista
		this.viewAccount(req, resp);
	}

	private void transfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		// 1. Obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));

		// 2. Hablar con el modelo
		Cuenta srcAccount = cuentaDAO.getByID(accountID);
		List<Cuenta> accounts = cuentaDAO.getAll();
		accounts.remove(srcAccount);

		double balance = cuentaDAO.getBalance(accountID);
		List<CategoriaTransferencia> transferCategories = categoriaTransferenciaDAO.getAll();

		// 3. Hablar con la vista
		req.setAttribute("srcAccount", srcAccount);
		req.setAttribute("accounts", accounts);
		req.setAttribute("balance", balance);
		req.setAttribute("categories", transferCategories);
		req.getRequestDispatcher("jsp/registrartransferencia.jsp").forward(req, resp);
	}

	private void confirmRegisterIncome(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();
		// 1. Obtener datos
		Date date = convertToDate(req.getParameter("date"));
		String concept = req.getParameter("concept");
		double value = Double.parseDouble(req.getParameter("value"));
		int categoryID = Integer.parseInt(req.getParameter("categoryID"));
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		// 2. Hablar con el modelo
		boolean approveIncome = value > 0;

		if (!approveIncome) {
			req.setAttribute("approveExpense", approveIncome);
			this.registerExpense(req, resp);
			return;
		}
		
		CategoriaIngreso incomeCategory = categoriaIngresoDAO.getCategoryById(categoryID);
		Cuenta account = cuentaDAO.getByID(accountID);
		ingresoDAO.registerIncome(date, concept, value, incomeCategory, account);
		cuentaDAO.updateBalance(value, accountID);
		// 3. Hablar con la vista
		this.viewAccount(req, resp);
	}

	private void registerIncome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CuentaDAO cuentaDAO = new CuentaDAO();
		// 1. Obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		// 2. Hablar con el modelo
		Cuenta account = cuentaDAO.getByID(accountID);
		double balance = cuentaDAO.getBalance(accountID);
		List<CategoriaIngreso> categories = categoriaIngresoDAO.getAll();
		// 3. Hablar con la vista
		req.setAttribute("balance", balance);
		req.setAttribute("categories", categories);
		req.setAttribute("accountID", account.getId());
		req.getRequestDispatcher("jsp/registraringreso.jsp").forward(req, resp);

	}

	private void cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.viewDashboard(req, resp);
	}

	private void confirmRegisterExpense(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// DAOs necesarios
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		// 1. Obtener datos
		Date date = convertToDate(req.getParameter("date"));
		String concept = (String) req.getParameter("concept");
		double value = Double.parseDouble(req.getParameter("value"));
		int categoryID = Integer.parseInt(req.getParameter("categoryID"));
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		// 2. Hablar con el modelo
		Cuenta account = cuentaDAO.getByID(accountID);
		CategoriaEgreso expenseCategory = categoriaEgresoDAO.getCategoryById(categoryID);
		// Cuestion de implementacion/validacion
		double balance = cuentaDAO.getBalance(accountID);
		boolean approveExpense = value <= balance && value > 0;

		if (!approveExpense) {
			req.setAttribute("approveExpense", approveExpense);
			this.registerExpense(req, resp);
			return;
		}
		egresoDAO.registerExpense(date, concept, value, expenseCategory, account);
		cuentaDAO.updateBalance(-value, accountID);
		// 3. Hablar con la vista
		this.viewAccount(req, resp);
	}

	private void registerExpense(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		// paso 1: obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		// paso 2: hablar con el modelo
		Cuenta account = cuentaDAO.getByID(accountID);
		double balance = cuentaDAO.getBalance(accountID);
		List<CategoriaEgreso> expensesCategories = categoriaEgresoDAO.getAll();
		// paso 3: hablar con la vista
		req.setAttribute("balance", balance);
		req.setAttribute("categories", expensesCategories);
		req.setAttribute("accountID", account.getId());
		req.getRequestDispatcher("jsp/registraregreso.jsp").forward(req, resp);
	}

	private void viewAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		CuentaDAO cuentaDAO = new CuentaDAO();
		// paso 1: obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		// paso 2: hablar con el modelo
		Cuenta account = cuentaDAO.getByID(accountID);
		List<MovimientoDTO> movements = movimientoDAO.getAllByAccount(accountID);

		// paso 3: hablar con la vista
		req.setAttribute("account", account);
		req.setAttribute("movements", movements);
		req.getRequestDispatcher("jsp/vercuenta.jsp").forward(req, resp);
	}

	public void viewDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		Date from;
		Date to;
		// paso 1: obtener datos
		// setear la fecha default segun la regla de negocio del caso de uso
		String fromString = (String) req.getParameter("from");
		String toString = (String) req.getParameter("to");

		if (fromString == null || toString == null) {
			from = convertToDate(fromDefault);
			fromString = fromDefault;
			to = convertToDate(toDefault);
			toString = toDefault;
		} else {
			from = convertToDate(fromString);
			to = convertToDate(toString);
		}

		if (!isAValidRangeOfDates(from, to)) {
			from = convertToDate(fromDefault);
			fromString = fromDefault;
			to = convertToDate(toDefault);
			toString = toDefault;
		}

		// paso 2: hablar con el modelo
		List<Cuenta> accounts = cuentaDAO.getAll();
		List<CategoriaResumenDTO> incomeCategoriesSumarized = categoriaIngresoDAO.getAllSumarized(fromString, toString);
		List<CategoriaResumenDTO> expenseCategoriesSumarized = categoriaEgresoDAO.getAllSumarized(fromString, toString);
		List<MovimientoDTO> movements = movimientoDAO.getAll(fromString, toString);

		// paso 3: hablar con la vista
		req.setAttribute("movements", movements);
		req.setAttribute("accounts", accounts);
		req.setAttribute("incomes", incomeCategoriesSumarized);
		req.setAttribute("expenses", expenseCategoriesSumarized);
		req.setAttribute("from", fromString);
		req.setAttribute("to", toString);

		req.getRequestDispatcher("jsp/verdashboard.jsp").forward(req, resp);

	}

	private boolean isAValidRangeOfDates(Date from, Date to) {
		if(from.compareTo(to) == 0 || from.compareTo(to) < 0) return true;
		return false;
	}

	private Date convertToDate(String dateString) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    ZoneId defaultZoneId = ZoneId.systemDefault();
	    LocalDate localDate;

	    try {
	        // Parsear la fecha sin horas
	        localDate = LocalDate.parse(dateString, formatter);
	        // Obtener la hora actual
	        LocalTime currentTime = LocalTime.now();
	        // Combinar la fecha con la hora actual
	        LocalDateTime localDateTime = LocalDateTime.of(localDate, currentTime);
	        // Convertir a Date
	        Date date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
	        return date;
	    } catch (DateTimeParseException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}