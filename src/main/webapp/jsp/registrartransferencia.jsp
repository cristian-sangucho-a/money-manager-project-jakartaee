<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Transferencia</title>
    <link rel="stylesheet" type="text/css" href="jsp/stylesregistrarmovimiento.css">
</head>
<body>

<div class="header">
    <h1>Registrar Transferencia</h1>
</div>
<div class="center-form">
    <div class="account-container">
        <h3>Balance de la Cuenta Origen</h3>
        <p class="balance"><c:out value="${balance}"/></p>
    </div>

    <div class="controls">
        <form action="ContabilidadController?ruta=confirmartransferencia" method="POST">
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
                <input type="text" id="value" name="amount" class="large-input" required>
            </div>
            <div class="controls vertical-center">
                <label for="originAccount" class="left-label">Cuenta Origen:</label>
                <input type="hidden" id="value" name="srcAccountID" value = "${srcAccount.id}" class="large-input" required>
                <input type="text" id="cuenta" name="cuentaOrigen" value = "${srcAccount.name}" class="large-input" readonly>
            </div>
            <div class="controls vertical-center">
                <label for="destinationAccount" class="left-label">Cuenta Destino:</label>
                <select id="destinationAccount" name="dstAccountID" class="select-account" required>
                    <c:forEach var="account" items="${accounts}">
                        <option value="${account.id}">${account.id} y ${account.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="controls vertical-center">
					<label for="category" class="left-label">Categoría:</label> 
					<select id="category" name="categoryID" class="select-category" required>
						<c:forEach var="category" items="${categories}">
							<option value="${category.id}">${category.name}</option>
						</c:forEach>
					</select>
				</div>
            <input type="hidden" id="account" name="accountID" value="${account}">
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

</body>
</html>
