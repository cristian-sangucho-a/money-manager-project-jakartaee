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
        <label for="from">Desde:</label>
        <input type="date" id="from" name="from" value="${from}">
        <label for="to">Hasta:</label>
        <input type="date" id="to" name="to" value="${to}">
        <button type="submit">Consultar</button>
    </div>

    <div class="tabs">
        <a href="cuentas.jsp" class="tab">Cuentas</a>
        <a href="categorias.jsp" class="tab">Categorías</a>
        <a href="movimientos.jsp" class="tab">Movimientos</a>
    </div>

    <div class="container">
        <div class="account-container">
            <c:forEach var="account" items="${accounts}">
                <div class="account-card">
                    <h3>${account.nombre}</h3>
                    <div class="balance ${account.balance < 0 ? 'negative' : ''}">
                        ${account.balance}
                    </div>
                    <div class="actions">
                        <a href="ContabilidadController?ruta=vercuenta&accountID=${cuenta.id}">INGRESO</a>
                        <a href="ContabilidadController?ruta=vercuenta&accountID=${cuenta.id}">EGRESO</a>
                        <a href="ContabilidadController?ruta=vercuenta&accountID=${cuenta.id}">TRANSF.</a>
                        <a href="ContabilidadController?ruta=vercuenta&accountID=${cuenta.id}">MOVS.</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

</body>
</html>
