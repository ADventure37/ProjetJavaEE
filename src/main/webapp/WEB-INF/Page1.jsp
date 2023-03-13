<%@ page pageEncoding = "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<style>
    form {
        margin-bottom: 100px;
    }
</style>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Page 1</title>
        <h1><%= "Bienvenue!" %></h1>
    </head>

    <body>
    <p style="font-weight: bold;">
        <font size="4"> Veuillez rentrer vos infrormations personnelles! </font></p>
        <form method="post" action="Page1">
        <p>
            <label for="Nom">Nom: </label>
            <input type = "text" id="Nom" name="Nom" />
        </p>
        <p>
            <label for="Prenom">Prénom: </label>
            <input type = "text" id="Prenom" name="Prenom" />
        </p>
        <p>
            <label for="genre">Genre:</label>

            <select name="genre" id="genre">
                <option value="">Sélectionner un genre</option>
                <option value="homme">Homme</option>
                <option value="femme">Femme</option>
                <option value="autre">Autre</option>
            </select>
        </p>
        <p>
            <label for="sitePrecedent">Site Précédent:</label>

            <select name="sitePrecedent" id="sitePrecedent">
                <option value="">Sélectionner un site</option>
                <option value="Angers">Angers</option>
                <option value="Dijon">Dijon</option>
                <option value="Paris">Paris</option>
            </select>
        </p>
        <p>
            <label for="formationPrecedente">Formation précédente:</label>

            <select name="formationPrecedente" id="formationPrecedente">
                <option value="">Sélectionner une formation</option>
                <option value="MPSI">MPSI</option>
                <option value="PCSI">PCSI</option>
                <option value="PTSI">PTSI</option>
                <option value="IUT">IUT</option>
                <option value="BTS">BTS</option>
                <option value="autre">Autre</option>
            </select>
        </p>
        <p><input type = "submit" name= "bouton" value="Valider" /></p>
        </form>

        <p style="font-weight: bold;">
            <font size="4"> Veuillez indiquer le document d'insertion! </font></p>
        <form method="post" action="Page1" enctype="multipart/form-data">
            <p>
                <label for="description">Description du fichier</label>
                <input type="text" name="description" id="description">
            </p>
            <p>
                <label for="fichier">Choisissez un fichier .csv:</label>
                <input type="file"
                       id="fichier" name="fichier"
                       accept=".csv" onchange="checkfile(this)">
            </p>
            <p><input type = "submit" name= "bouton" value="Valider le fichier" /></p>


        </form>

        <form method="post" action="Page1">
        <p>
            <label for="page2">Page pour gérer les équipes: </label>
            <input type="button" id="page2" onclick="window.location.href = 'Page2';" value="Cliquez" /></p>
        </form>

    </body>
</html>