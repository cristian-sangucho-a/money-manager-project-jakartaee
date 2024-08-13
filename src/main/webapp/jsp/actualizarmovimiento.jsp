<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Actualizar Movimiento</title>
    <link rel="stylesheet" type="text/css" href="jsp/stylesactualizarmovimiento.css">
    <style>
        .popup {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }
        .popup-content {
            background-color: white;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            text-align: center;
        }
        .close-btn {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
        }
        /* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type=number] {
  -moz-appearance: textfield;
}
    </style>
</head>
<body>

<div class="header">
    <h1>Actualizar movimiento</h1>
</div>

<form action="ContabilidadController?ruta=confirmaractualizarmovimiento" method="POST">
    <input type="hidden" name="ruta" value="actualizarmovimiento">
    <input type="hidden" name="srcAccountID" value="${movement.srcAccount}">
    <input type="hidden" name="dstAccountID" value="${movement.dstAccount}">
    <input type="hidden" name="movementType" value="${movement.tipo_movimiento}">
    <input type="hidden" name="movementID" value="${movement.id}">

    <label for="concept">Concepto:</label>
    <input type="text" id="concept" name="concept" value="${movement.concept}" required>
    
    <label for="date">Fecha:</label>
    <input type="date" id="date" name="date" value="${movement.stringDate}" required>
    
    <label for="value">Valor:</label>
    <input type="number" id="value" name="value" value="${movement.value}" step="0.01" required>
    
    <label for="category">Categoría:</label>
    <select id="category" name="categoryID" required>
        <c:forEach var="category" items="${movementsCategories}">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select>

    <div class="controls center-button">
        <button type="submit">Registrar</button>
        <form action="ContabilidadController?ruta=cancelar" method="GET" style="display:inline;">
            <button type="submit">Cancelar</button>
        </form>
    </div>
</form>
<div id="popup" class="popup">
    <div class="popup-content">
        <p>El movimiento no puede ser aprobado porque el valor supera el balance disponible o ingresó un valor negativo (Ingrese el valor positivo cuando se registre este se guardara de manera correcta).</p>
        <button class="close-btn" onclick="closePopup()">Cerrar</button>
    </div>
</div>

<script>
    function closePopup() {
        document.getElementById("popup").style.display = "none";
    }

    <c:choose>
        <c:when test="${not empty approveExpense}">
            <c:if test="${not approveExpense}">
                document.getElementById("popup").style.display = "block";
            </c:if>
        </c:when>
    </c:choose>
</script>

</body>
</html>
