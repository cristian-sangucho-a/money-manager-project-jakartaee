<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="stylesverdashboard.css">
</head>
<body>

    <div class="header">
        <h1>Dashboard</h1>
    </div>

    <div class="controls">
        <label for="desde">Desde:</label>
        <input type="date" id="desde" name="desde" value="${param.desde}">
        <label for="hasta">Hasta:</label>
        <input type="date" id="hasta" name="hasta" value="${param.hasta}">
        <button type="submit">Consultar</button>
    </div>

    <div class="tabs">
        <a href="cuentas.jsp" class="tab">Cuentas</a>
        <a href="categorias.jsp" class="tab">Categorías</a>
        <a href="frecuentes.jsp" class="tab">Frecuentes</a>
        <a href="movimientos.jsp" class="tab">Movimientos</a>
    </div>

    <div class="container">
        <div class="account-container">
            <c:forEach var="account" items="${accounts}">
                <div class="account-card">
                    <h3>${cuenta.nombre}</h3>
                    <div class="balance ${cuenta.balance < 0 ? 'negative' : ''}">
                        ${cuenta.balance}
                    </div>
                    <div class="actions">
                        <a href="#">INGRESO</a>
                        <a href="#">EGRESO</a>
                        <a href="#">TRANSF.</a>
                        <a href="#">MOVS.</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

</body>
</html>
