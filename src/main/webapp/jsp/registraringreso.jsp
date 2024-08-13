<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registrar Ingreso</title>
	<link rel="stylesheet" type="text/css" href="jsp/stylesregistrarmovimiento.css">
</head>
<body>
	<div class="header">
		<h1>Registrar Ingreso</h1>
	</div>
	<div class="center-balance">
		<div class="account-card">
			<h3>Balance</h3>
			<p class="balance">
				<c:out value="${balance}" />
			</p>
		</div>
	</div>
	<div class="center-form">
		<div class="account-container"></div>

		<div class="controls">
			<form action="ContabilidadController?ruta=confirmarregistroingreso" method="POST">
				<%
					// Obtén la fecha actual
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String currentDate = sdf.format(new Date());
				%>
				<div class="controls vertical-center">
					<label for="date" class="left-label">Fecha:</label> 
					<input type="date" id="date" name="date" class="large-input" required value="<%= currentDate %>">
				</div>
				<div class="controls vertical-center">
					<label for="concept" class="left-label">Concepto:</label> 
					<input type="text" id="concept" name="concept" class="large-input" required>
				</div>
				<div class="controls vertical-center">
					<label for="value" class="left-label">Valor:</label> 
					<input type="number" id="value" name="value" class="large-input" step="0.01" required>
				</div>
				<div class="controls vertical-center">
					<label for="category" class="left-label">Categoría:</label> 
					<select id="category" name="categoryID" class="select-category" required>
						<c:forEach var="category" items="${categories}">
							<option value="${category.id}">${category.name}</option>
						</c:forEach>
					</select>
				</div>
				<input type="hidden" id="account" name="accountID" value="${accountID}">
				<div class="controls center-button">
					<button type="submit">Registrar</button>
				</div>
			</form>
			<div class="controls center-button">
				<form action="ContabilidadController?ruta=cancelar" method="GET">
					<button type="submit">Cancelar</button>
				</form>
			</div>
		</div>
	</div>
	<div id="popup" class="popup">
    <div class="popup-content">
        <p>El ingreso no puede ser aprobado porque el valor supera el balance disponible o ingresó un valor negativo.</p>
        <button class="close-btn" onclick="closePopup()">Cerrar</button>
    </div>
</div>

<script>
    function closePopup() {
        document.getElementById("popup").style.display = "none";
    }

    <c:choose>
        <c:when test="${not empty approveExpense}">
            <c:if test="${not approveExpense}">
                document.getElementById("popup").style.display = "block";
            </c:if>
        </c:when>
    </c:choose>
</script>
</body>
</html>
