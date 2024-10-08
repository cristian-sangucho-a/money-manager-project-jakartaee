<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalles de la Cuenta</title>
<link rel="stylesheet" type="text/css" href="jsp/stylesvercuenta1.css">
<script src="https://kit.fontawesome.com/1a501b4a16.js"
	crossorigin="anonymous"></script>
</head>
<body>

	<div class="header">
		<h1>Detalles de la Cuenta</h1>
	</div>

	<div class="account-container">
		<div class="account-header">
			<div class="account-name">Nombre de la Cuenta: ${account.name}</div>
			<div class="balance ${account.balance < 0 ? 'negative' : 'positive'}">
				Saldo: $
				<c:out value="${account.balance}" />
			</div>
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
				</a>
			</div>
		</div>

		<div class="movements-list">
			<h2>Movimientos Recientes</h2>
			<c:forEach var="movement" items="${movements}">
				<div class="movement-item">
					<div>
						<div class="movement-description">
							Concepto:
							<c:out value="${movement.concept}" />
						</div>
						<div class="movement-date">
							Fecha:
							<c:out value="${movement.date}" />
						</div>
					</div>
					<div>
						<div
							class="movement-amount ${movement.value < 0 ? 'negative' : 'positive'}">
							Monto: $
							<c:out value="${movement.value}" />
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="center-button">
			<form action="ContabilidadController?ruta=verdashboard" method="get">
				<button type="submit" class="button-back">Volver al
					Dashboard</button>
			</form>
		</div>
	</div>

</body>
</html>
