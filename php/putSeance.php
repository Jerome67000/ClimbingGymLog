<?php
	// Connexion BDD
	include "pdo.php";

	// requete
	$local_id = $_POST['localid'];
	$nom = $_POST['nom'];
	$dateSeance = $_POST['date_seance'];
	$nomSalle = $_POST['nom_salle'];
	$note = $_POST['note'];
	$clientId = $_POST['client_id'];

	$query = "INSERT INTO Seances (nom, date_seance, nom_salle, note, client_id)
				values('$nom', '$dateSeance', '$nomSalle', '$note', '$clientId')";

	$pdo->query($query);

	// renvoi l'id de l'élément inséré
	echo $pdo->lastInsertId();
?>
