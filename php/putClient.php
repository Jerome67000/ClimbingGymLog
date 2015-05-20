<?php
	// Connexion BDD
	include "pdo.php";

	// requete
	$nom = $_POST['nom'];
    $prenom = $_POST['prenom'];
    $num = $_POST['num'];
    $email = $_POST['email'];
    $salle_id = $_POST['salle_id'];

	$query = "INSERT INTO Clients (nom, prenom, num, email, salle_id, date_aj)
				values('$nom', '$prenom', '$num', '$email', '$salle_id', CURDATE())";

	$pdo->query($query);

	// renvoi l'id de l'élément inséré
	echo $pdo->lastInsertId();
?>
