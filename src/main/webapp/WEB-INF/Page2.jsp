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
<p>
<form method="post" action="Page2">
<p>
    <label for="Nombre">Nombre d'équipe nécessaire: </label>
    <input type = "text" id="Nombre" name="Nombre" />
    <input type = "submit" name="bouton" value="Créer les équipes" />
</form>
    <c:forEach items="${ equipes }" var="equipe" varStatus="status">
        <p>
            <c:out value="${ equipe.nom }:" />
        </p>
    </c:forEach>
</p>
<p>
<form method="post" action="Page2">
    <p>
        <input type = "text" id="ancien" name= "Ancien Nom" />
        <input type = "text" id="new" name= "Nouveau Nom" />
        <input type = "submit" name="bouton" value="Modifier le nom d'équipe" />
    </p>
</form>

</p>
<p>
    <form method="post" action="Page2">
        <p>
            <%= "Veuillez assigner un élève à une équipe" %>
        </p>
        <p>
            <label for="AssignerEq">Choisir une équipe:</label>
            <select name="AssignerEq" id="AssignerEq">
                <option value="">Sélectionner une équipe</option>
                <c:forEach items="${ equipes }" var="equipe" varStatus="status">
                    <p>
                        <option value="${ equipe.nom }">${ equipe.nom }</option>
                    </p>
                </c:forEach>
            </select>
            <label for="AssignerEl">Choisir un élève:</label>
            <select name="AssignerEl" id="AssignerEl">
                <option value="">Sélectionner un élève</option>
                <c:forEach items="${ sessionScope.eleves }" var="eleves" varStatus="status">
                    <p>
                        <option value="${ eleves.nom } ${eleves.prenom}">${ eleves.nom } ${eleves.prenom}</option>
                    </p>
                </c:forEach>
            </select>
            <input type = "submit" name="bouton" value="Valider l'assignation" />
        </p>
    </form>
</p>

<p>
    <label for="Nombre">Liste des élèves sans équipe: </label>
    <c:forEach items="${ sessionScope.eleves }" var="eleves" varStatus="status">
            <p>
                <c:out value="${ eleves.nom } ${eleves.prenom} ${eleves.equipe}" />
            </p>
    </c:forEach>
</p>
<p><label for="page1">Page pour ajouter des élèves: </label>
    <input type="button" id="page1" onclick="window.location.href = 'Page1';" value="Cliquez" /></p>
</body>
</html>
