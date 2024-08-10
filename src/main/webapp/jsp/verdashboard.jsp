<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="jsp/stylesverdashboar.css">
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
            
            <label for="from">Desde:</label>
            <input type="date" id="from" name="from" value="<%= fromDate %>">
            
            <label for="to">Hasta:</label>
            <input type="date" id="to" name="to" value="<%= toDate %>">
            
            <button type="submit">Consultar</button>
        </form>
    </div>

    <div class="tabs">
        <a href="vercategoria.jsp" class="tab">Categorías</a>
        <a href="vermovimiento.jsp" class="tab">Movimientos</a>
    </div>

    <div class="container">
        <div class="account-container">
            <c:forEach var="account" items="${accounts}">
                <div class="account-card">
                    <h3>${account.name}</h3>
                    <div class="balance ${account.balance < 0 ? 'negative' : ''}">
                        ${account.balance}
                    </div>
                    <div class="actions">
                        <a href="ContabilidadController?ruta=registraringreso&accountID=${account.id}">INGRESO</a>
                        <a href="ContabilidadController?ruta=registraregreso&accountID=${account.id}">EGRESO</a>
                        <a href="ContabilidadController?ruta=registrartransferencia&accountID=${account.id}">TRANSF.</a>
                        <a href="ContabilidadController?ruta=vercuenta&accountID=${account.id}">MOVS.</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

</body>
</html>
