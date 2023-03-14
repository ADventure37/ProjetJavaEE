DROP TABLE IF EXISTS `eleve`;
DROP TABLE IF EXISTS `equipe`;



CREATE TABLE `equipe` (
    id INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)  
);

CREATE TABLE `eleve` (
    id INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(10) NOT NULL,
    prenom VARCHAR(10) NOT NULL,
    genre VARCHAR(10) NOT NULL,
    sitePrecedent VARCHAR(10) NOT NULL,
    formationPrecedente VARCHAR(10) NOT NULL,
    idEquipe int default null,
    PRIMARY KEY (id) ,
    FOREIGN KEY (idEquipe) REFERENCES equipe(id)
);



