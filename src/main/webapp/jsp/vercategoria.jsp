<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movimientos de la Categoría</title>
    <link rel="stylesheet" href="jsp/stylesverdashboard.css">
</head>
<body>

<div class="movimientos">
    <c:forEach var="movimiento" items="${movimientos}">
        <div class="movimiento">
            <p><strong>Concepto:</strong> ${movimiento.concept}</p>
            <p><strong>Fecha:</strong> ${movimiento.date}</p>
            <p><strong>Valor:</strong> ${movimiento.value}</p>
            <p><strong>Origen:</strong> ${movimiento.srcAccount}</p>
            <p><strong>Destino:</strong> ${movimiento.dstAccount}</p>
        </div>
    </c:forEach>
    
    <c:if test="${empty movimientos}">
        <p>No se encontraron movimientos para esta categoría en el rango de fechas especificado.</p>
    </c:if>
</div>

</body>
</html>
