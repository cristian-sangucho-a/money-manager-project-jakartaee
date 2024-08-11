<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error 404 - Página No Encontrada</title>
    <link rel="stylesheet" type="text/css" href="styleserror.css">
</head>
<body>

<div class="header">
    <h1>Error 404</h1>
</div>

<div class="container">
    <h3>Página No Encontrada</h3>
    <p>Lo sentimos, la página que estás buscando no existe.</p>
	<div class="center-button">
            <form action="ContabilidadController?ruta=verdashboard" method="get">
                <button type="submit" class="button-back">Volver al Dashboard</button>
            </form>
        </div>
</div>

</body>
</html>
