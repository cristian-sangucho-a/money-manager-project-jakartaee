<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movimientos de la Categoría</title>
    <link rel="stylesheet" href="stylesvercategoria.css">
</head>
<body>

<div class="header">
        <h1>Movimientos de la Categoría</h1>
</div>

<div class="movimientos">
    <c:forEach var="movimiento" items="${movements}">
        <div class="movimiento">
            <p><strong>Concepto:</strong> ${movimiento.concept}</p>
            <p><strong>Fecha:</strong> ${movimiento.date}</p>
            <p><strong>Valor:</strong> ${movimiento.value}</p>
            <c:choose>
                <c:when test="${movimiento.movementType == 'INGRESO'}">
                    <span class="details">Cuenta: ${movimiento.nameDstAccount}</span>
                </c:when>
                <c:when test="${movimiento.movementType == 'EGRESO'}">
                    <span class="details">Cuenta: ${movimiento.nameSrcAccount}</span>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>
    
    <c:if test="${empty movements}">
        <p>No se encontraron movimientos para esta categoría en el rango de fechas especificado.</p>
    </c:if>
</div>

<div class="center-button">
            <form action="ContabilidadController?ruta=verdashboard" method="get">
                <button type="submit" class="button-back">Volver al Dashboard</button>
            </form>
</div>

</body>
</html>
