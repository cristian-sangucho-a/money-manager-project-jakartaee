<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Actualizar Movimiento</title>
    <link rel="stylesheet" type="text/css" href="stylesactualizarmovimiento.css">
</head>
<body>

<div class="header">
    <h1>Actualizar movimiento</h1>
</div>

    <form action="ContabilidadController" method="POST">
        <input type="hidden" name="ruta" value="actualizarmovimiento">
        <input type="hidden" name="movementID" value="${movement.id}">

        <label for="concept">Concepto:</label>
        <input type="text" id="concept" name="concept" value="${movement.concept}" required>
        
        <label for="date">Fecha:</label>
        <input type="date" id="date" name="date" value="${movement.date}" required>
        
        <label for="value">Valor:</label>
        <input type="number" id="value" name="value" value="${movement.value}" step="0.01" required>
        
        <label for="category">Categoría:</label>
        <select id="category" name="categoryID" required>
            <c:forEach var="category" items="${list}">
                <option value="${category.id}">${category.categoryName}</option>
            </c:forEach>
        </select>

        <div class="controls center-button">
                <button type="submit">Registrar</button>
            </div>
    </form>

</body>
</html>