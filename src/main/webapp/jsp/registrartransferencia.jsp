<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Transferencia</title>
    <link rel="stylesheet" type="text/css" href="jsp/stylesregistrarmovimiento.css">
    <script>
        function setDefaultDate() {
            var today = new Date().toISOString().split('T')[0];
            document.getElementById('date').value = today;
        }
        window.onload = setDefaultDate;
    </script>
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
        <form action="ContabilidadController?ruta=confirmarregistrotransferencia" method="POST">
            <div class="controls vertical-center">
                <label for="date" class="left-label">Fecha:</label>
                <input type="date" id="date" name="date" class="large-input" required>
            </div>
            <div class="controls vertical-center">
                <label for="concept" class="left-label">Concepto:</label>
                <input type="text" id="concept" name="concept" class="large-input" required>
            </div>
            <div class="controls vertical-center">
                <label for="value" class="left-label">Valor:</label>
                <input type="text" id="value" name="value" class="large-input" required>
            </div>
            <div class="controls vertical-center">
                <label for="originAccount" class="left-label">Cuenta Origen:</label>
                <select id="originAccount" name="srcAccountID" class="select-account" required>
                    <c:forEach var="account" items="${accounts}">
                        <option value="${account.id}">${account.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="controls vertical-center">
                <label for="destinationAccount" class="left-label">Cuenta Destino:</label>
                <select id="destinationAccount" name="dstAccountID" class="select-account" required>
                    <c:forEach var="account" items="${accounts}">
                        <option value="${account.id}">${account.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="controls center-button">
                <button type="submit">Registrar</button>
                <button type="button" onclick="window.location.href='ContabilidadController?ruta=cancelar'">Cancelar</button>
            </div>
        </form>
        <div class="controls center-button">
            <form action="ContabilidadController?ruta=verdashboard" method="GET">
                <button type="submit">Volver al Dashboard</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
