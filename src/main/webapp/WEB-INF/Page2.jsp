<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14/02/2023
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page 2</title>
</head>
<body>
<p>
    <label for="Nombre">Nombre d'équipe nécessaire: </label>
    <input type = "text" id="Nombre" name="Nombre" />
    <input type = "submit" value="envoyer" />
</p>
<p><label for="page1">Page pour ajouter des élèves: </label>
    <input type="button" id="page1" onclick="window.location.href = 'Page1';" value="Cliquez" /></p>
</body>
</html>
