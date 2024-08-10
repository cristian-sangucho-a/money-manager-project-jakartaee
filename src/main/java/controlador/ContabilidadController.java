package controlador;

import java.io.IOException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

/**
 * 
 */
@WebServlet("/ContabilidadController")
public class ContabilidadController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final String fromDefault = "2024-08-01";
	private final String toDefault = "2024-08-31";

	/**
	 * Default constructor
	 */
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
		case "cancelar":
			this.cancel(req, resp);
			break;
		default:
			this.viewDashboard(req, resp);
			break;
		}
	}

	private void confirmTransfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		//1. Obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		int dstAccountID = Integer.parseInt(req.getParameter("dstAccountID"));
		Cuenta dstAccount =cuentaDAO.getByID(dstAccountID);
		int srcAccountID = Integer.parseInt(req.getParameter("srcAccountID"));
		Cuenta srcAccount = cuentaDAO.getByID(srcAccountID);
		Date date = convertToDate(req.getParameter("date"));
		String concept = req.getParameter("concept");
		double balance = cuentaDAO.getBalance(accountID);
		//2. Hablar con el modelo
		boolean approveExpense = amount > balance;
		
		if(approveExpense) {
			req.setAttribute("aproveExpense", approveExpense);
			registerExpense(req, resp);
			return;
		}
		
		CategoriaTransferencia categoryID = categoriaTransferenciaDAO.getCategoryById(1);
		transferenciaDAO.transfer(amount, dstAccount, srcAccount, date, concept, categoryID);
		
		//3. Hablar con la vista
		viewDashboard(req, resp);
	}

	private void transfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		// 1. Obtener datos
		int accountIDV = Integer.parseInt(req.getParameter("accountID"));

		// 2. Hablar con el modelo
		Cuenta srcAccount = cuentaDAO.getByID(accountIDV);
		List<Cuenta> accounts = cuentaDAO.getAll();

		// 3. Hablar con la vista
		req.setAttribute("srcAccount", srcAccount);
		req.setAttribute("accounts", accounts);
		req.getRequestDispatcher("jsp/registrartransferencia.jsp").forward(req, resp);
	}

	private void confirmRegisterIncome(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. Obtener datos
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();

		Date date = convertToDate(req.getParameter("date"));
		String concept = req.getParameter("concept");
		double value = Double.parseDouble(req.getParameter("value"));
		int categoryID = Integer.parseInt(req.getParameter("categoryID"));
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		Cuenta account = cuentaDAO.getByID(accountID);
		// 2. Hablar con el modelo
		CategoriaIngreso incomeCategory = categoriaIngresoDAO.getCategoryById(categoryID);
		ingresoDAO.registerIncome(date, concept, value, incomeCategory, account);
		cuentaDAO.updateBalance(value, account.getId());
		// 3. Hablar con la vista
		viewDashboard(req, resp);

	}

	private void registerIncome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CuentaDAO cuentaDAO = new CuentaDAO();
		// 1. Obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		Cuenta account = cuentaDAO.getByID(accountID);
		// 2. Hablar con el modelo
		double balance = account.getBalance();
		List<CategoriaIngreso> categories = categoriaIngresoDAO.getAll();

		// 3. Hablar con la vista
		req.setAttribute("balance", balance);
		req.setAttribute("categories", categories);
		req.setAttribute("account", account.getId());
		req.getRequestDispatcher("jsp/registraringreso.jsp").forward(req, resp);

	}

	private void cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("jsp/vercuenta");
	}

	private void confirmRegisterExpense(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// DAOs necesarios
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		// paso 1: obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		Date date = convertToDate(req.getParameter("date"));
		String concept = (String) req.getParameter("concept");
		double value = Double.parseDouble(req.getParameter("value"));
		int categoryID = Integer.parseInt(req.getParameter("categoryID"));
		double balance = cuentaDAO.getBalance(accountID);
		boolean approveExpense = value > balance;

		if (approveExpense) {
			req.setAttribute("aproveExpense", approveExpense);
			registerExpense(req, resp);
			return;
		}
		// 2
		// 2.1
		CategoriaEgreso expenseCategory = categoriaEgresoDAO.getCategoryById(categoryID);
		Cuenta cuenta = cuentaDAO.getByID(accountID);
		cuentaDAO.updateBalance(-value, accountID);
		// paso 2: hablar con el modelo
		// 2.2 Date date, String concept, double value, CategoriaEgreso expenseCategory,
		// Cuenta account
		egresoDAO.registerExpense(date, concept, value, expenseCategory, cuentaDAO.getByID(accountID));
		// 2.2 y 2.3
		// paso 3: hablar con la vista
		viewDashboard(req, resp);
	}

	// paso 1: obtener datos
	// paso 2: hablar con el modelo
	// paso 3: hablar con la vista

	private void registerExpense(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		// paso 1: obtener datos
		// 1
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		Cuenta account = cuentaDAO.getByID(accountID);
		// paso 2: hablar con el modelo
		// 1.1 y 1.2
		double balance = account.getBalance();
		List<CategoriaEgreso> expensesCategories = categoriaEgresoDAO.getAll();

		// paso 3: hablar con la vista
		// 1.3
		req.setAttribute("balance", balance);
		req.setAttribute("categories", expensesCategories);
		req.setAttribute("account", account.getId());
		req.getRequestDispatcher("jsp/registraregreso.jsp").forward(req, resp);
	}

	private void viewAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		CuentaDAO cuentaDAO = new CuentaDAO();
		// paso 1: obtener datos
		int accountID = Integer.parseInt(req.getParameter("accountID"));
		// paso 2: hablar con el modelo
		List<MovimientoDTO> movements = movimientoDAO.getAllByAccount(accountID);
		Cuenta account = cuentaDAO.getByID(accountID);

		// paso 3: hablar con la vista
		req.setAttribute("movements", movements);
		req.setAttribute("account", account);

		req.getRequestDispatcher("jsp/vercuenta.jsp").forward(req, resp);
	}

	/**
	 * @param from vienen en el cuerpo / url
	 * @param to
	 */
	public void viewDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		Date from;
		Date to;
		// paso 1: obtener datos
		// setear la fecha default segun la regla de negocio del caso de uso
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

		// paso 2: hablar con el modelo
		List<Cuenta> accounts = cuentaDAO.getAll();
		List<CategoriaResumenDTO> incomeCategoriesSumarized = categoriaIngresoDAO.getAllSumarized(from, to);
		List<CategoriaResumenDTO> expenseCategoriesSumarized = categoriaEgresoDAO.getAllSumarized(from, to);
		List<MovimientoDTO> movements = movimientoDAO.getAll(from, to);

		// paso 3: hablar con la vista
		req.setAttribute("movements", movements);
		req.setAttribute("accounts", accounts);
		req.setAttribute("incomes", incomeCategoriesSumarized);
		req.setAttribute("expenses", expenseCategoriesSumarized);

		req.getRequestDispatcher("jsp/verdashboard.jsp").forward(req, resp);

	}

	private boolean isAValidRangeOfDates(Date from, Date to) {
		if (from.after(to)) {
			return false;
		}
		return true;
	}

	private Date convertToDate(String dateString) {
		// Parse a LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		// Para convertir a DATE
		try {
			Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
			return date;
		} catch (DateTimeException e) {
			e.printStackTrace();
		}
		return null;
	}

}