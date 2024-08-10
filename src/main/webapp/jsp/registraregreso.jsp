<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Egreso</title>
    <link rel="stylesheet" type="text/css" href="jsp/stylesregistrarmovimiento.css">
</head>
<body>

<div class="header">
    <h1>Registrar Egreso</h1>
</div>
<div class="center-balance">
    <div class="account-card">
        <h3>Balance</h3>
        <p class="balance"><c:out value="${balance}"/></p>
    </div>
</div>
<div class="center-form">
    <div class="account-container">
    </div>

    <div class="controls">
        <form action="ContabilidadController?ruta=confirmarregistroegreso" method="POST">
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
                <input type="text" id="value" name="value" class="large-input" required>
            </div>
            <div class="controls vertical-center">
                <label for="category" class="left-label">Categoría:</label>
                <select id="category" name="categoryID" class="select-category" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}">${category.name}</option>
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
