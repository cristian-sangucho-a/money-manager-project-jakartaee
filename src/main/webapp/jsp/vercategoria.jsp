<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movimientos de la Categoría</title>
    <link rel="stylesheet" href="jsp/stylesvercategoria1.css">
</head>
<body>

<div class="header">
    <h1>Movimientos de la Categoría</h1>
</div>

<div class="account-container">
    <div class="movements-list">
        <c:forEach var="movimiento" items="${movements}">
            <div class="movement-item">
                <div class="movement-description">
                    <p><strong>Concepto:</strong> ${movimiento.concept}</p>
                </div>
                <div class="movement-details">
                    <p><strong>Fecha:</strong> ${movimiento.date}</p>
                    <p><strong>Valor:</strong> <span class="${movimiento.value > 0 ? 'movement-amount positive' : 'movement-amount negative'}">${movimiento.value}</span></p>
                    <c:choose>
                        <c:when test="${movimiento.tipo_movimiento == 'INGRESO'}">
                            <p><strong>Cuenta:</strong> ${movimiento.nameDstAccount}</p>
                        </c:when>
                        <c:when test="${movimiento.tipo_movimiento == 'EGRESO'}">
                            <p><strong>Cuenta:</strong> ${movimiento.nameSrcAccount}</p>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty movements}">
            <p>No se encontraron movimientos para esta categoría en el rango de fechas especificado.</p>
        </c:if>
    </div>
</div>

<div class="center-button">
    <form action="ContabilidadController?ruta=verdashboard" method="get">
        <button type="submit" class="button-back">Volver al Dashboard</button>
    </form>
</div>

</body>
</html>
