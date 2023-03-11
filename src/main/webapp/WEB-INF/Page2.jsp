<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14/02/2023
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding = "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Page 2</title>
</head>
<body>
<form method="post" action="Page2">
<p>
    <label for="Nombre">Nombre d'équipe nécessaire: </label>
    <input type = "text" id="Nombre" name="Nombre" />
    <input type = "submit" name="bouton" value="Créer les équipes" />
    <c:forEach items="${ equipes }" var="equipe" varStatus="status">
        <p><c:out value="${ equipe.nom }:" /> </p>
    </c:forEach>

    </p>
</form>
<p>
    <label for="Nombre">Liste des élèves sans équipe: </label>
    <c:forEach items="${ sessionScope.eleves }" var="eleves" varStatus="status">
            <p><c:out value="${ eleves.nom } ${eleves.prenom}" /> </p>
    </c:forEach>
</p>
<p><label for="page1">Page pour ajouter des élèves: </label>
    <input type="button" id="page1" onclick="window.location.href = 'Page1';" value="Cliquez" /></p>
</body>
</html>
