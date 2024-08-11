<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link rel="stylesheet" type="text/css"
	href="jsp/stylesverdashboard1.css">
<script src="https://kit.fontawesome.com/1a501b4a16.js"
	crossorigin="anonymous"></script>
</head>
<body>

	<div class="header">
		<h1>Dashboard</h1>
	</div>

	<div class="controls">
		<form action="ContabilidadController" method="GET">
			<input type="hidden" name="ruta" value="verdashboard" />

			<%
			// Obtener la fecha actual
			java.time.LocalDate today = java.time.LocalDate.now();
			// Calcular el primer día del mes actual
			java.time.LocalDate firstDayOfMonth = today.withDayOfMonth(1);
			// Formatear las fechas para el input type="date"
			String fromDate = firstDayOfMonth.toString();
			String toDate = today.toString();
			%>

			<label for="from">Desde:</label> <input type="date" id="from"
				name="from" value="<%=fromDate%>"> <label for="to">Hasta:</label>
			<input type="date" id="to" name="to" value="<%=toDate%>">

			<button type="submit">Consultar</button>
		</form>
	</div>

	<div class="tabs">
		<a class="tab">Cuentas</a>
	</div>

	<div class="container">
		<div class="account-container">
			<c:forEach var="account" items="${accounts}">
				<div class="account-card">
					<h3>${account.name}</h3>
					<div class="balance ${account.balance < 0 ? 'negative' : ''}">
						${account.balance}</div>
					<div class="actions">
						<a
							href="ContabilidadController?ruta=registraringreso&accountID=${account.id}">
							<i class="fa-solid fa-money-bill-trend-up"></i> Ingreso
						</a> <a
							href="ContabilidadController?ruta=registraregreso&accountID=${account.id}">
							<i class="fa-solid fa-arrow-down"></i> Egreso
						</a> <a
							href="ContabilidadController?ruta=registrartransferencia&accountID=${account.id}">
							<i class="fa-solid fa-money-bill-transfer"></i> Transf.
						</a> <a
							href="ContabilidadController?ruta=vercuenta&accountID=${account.id}">
							<i class="fa-solid fa-eye"></i> Movs.
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div class="tabs">
		<a class="tab">Categorías</a>
	</div>

	<div class="container">
		<div class="category-container">
			<h2>Ingresos</h2>
			<c:forEach var="incomeCategory" items="${incomes}">
				<div class="category-card">
					<h3>${incomeCategory.categoryName}</h3>
					<div class="balance">${incomeCategory.sumarized}</div>
					<div class="actions">
						<a
							href="ContabilidadController?ruta=vercategoria&categoryID=${incomeCategory.id}">
							<i class="fa-solid fa-hand-holding-dollar"></i> Ver
						</a>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="category-container">
			<h2>Egresos</h2>
			<c:forEach var="expenseCategory" items="${expenses}">
				<div class="category-card">
					<h3>${expenseCategory.categoryName}</h3>
					<div class="balance">${expenseCategory.sumarized}</div>
					<div class="actions">
						<a
							href="ContabilidadController?ruta=vercategoria&categoryID=${expenseCategory.id}">
							<i class="fa-solid fa-hand-holding-dollar"></i> Ver
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div class="tabs">
		<a class="tab">Movimientos</a>
	</div>

	<div class="container">
		<div class="movements-container">
			<c:forEach var="movement" items="${movements}">
				<div class="movement-card ${movement.tipo_movimiento}">
					<div class="movement-details">
						<span class="date">${movement.date}</span> <span
							class="description">${movement.concept}</span>
						<c:choose>
							<c:when test="${movement.tipo_movimiento == 'TRANSFERENCIA'}">
								<span class="details">Origen: ${movement.nameSrcAccount}
									- Destino: ${movement.nameDstAccount}</span>
							</c:when>
						</c:choose>
					</div>
					<div class="amount ${movement.value < 0 ? 'negative' : 'positive'}">
						${movement.value}</div>
					<div class="movement-type">
						<c:choose>
							<c:when test="${movement.tipo_movimiento == 'INGRESO'}">
								<span>Ingreso</span>
							</c:when>
							<c:when test="${movement.tipo_movimiento == 'EGRESO'}">
								<span>Egreso</span>
							</c:when>
							<c:when test="${movement.tipo_movimiento == 'TRANSFERENCIA'}">
								<span>Transferencia</span>
							</c:when>
						</c:choose>
					</div>
					<div class="actions">
						<a
							href="ContabilidadController?ruta=actualizarmovimiento&movementID=${movement.id}">
							<i class="fa-solid fa-pen-to-square"></i> Editar
						</a> <a
							href="ContabilidadController?ruta=eliminarmovimiento&movementID=${movement.id}">
							<i class="fa-solid fa-trash"></i> Eliminar
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

</body>
</html>
