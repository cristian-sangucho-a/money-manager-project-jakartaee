<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ver Categorías</title>
    <link rel="stylesheet" type="text/css" href="jsp/stylesvercategoria1.css">
</head>
<body>

    <div class="header">
        <h1>Categorías</h1>
    </div>

    <div class="container">
        <div class="category-container">
            <c:forEach var="incomeCategory" items="${incomeCategories}">
                <div class="category-card">
                    <h3>${incomeCategory.name}</h3>
                    <ul>
                        <c:forEach var="subCategory" items="${incomeCategory.subCategories}">
                            <li>${subCategory.name}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>

            <c:forEach var="expenseCategory" items="${expenseCategories}">
                <div class="category-card">
                    <h3>${expenseCategory.name}</h3>
                    <ul>
                        <c:forEach var="subCategory" items="${expenseCategory.subCategories}">
                            <li>${subCategory.name}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>

            <c:forEach var="transferCategory" items="${transferCategories}">
                <div class="category-card">
                    <h3>${transferCategory.name}</h3>
                    <ul>
                        <c:forEach var="subCategory" items="${transferCategory.subCategories}">
                            <li>${subCategory.name}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </div>
    </div>

</body>
</html>
