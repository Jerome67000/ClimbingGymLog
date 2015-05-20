<?php
	// Connexion BDD
	include "pdo.php";

	// Requete
	$numClient = $_GET['num'];
	if(empty($numClient))
		$query = "SELECT * FROM Clients";
	else
		$query = "SELECT * FROM Clients WHERE num=$numClient";

	// Affichage du JSON
	$first = true ;
	echo "{\"Clients\":[" ;
	foreach ($pdo->query($query) as $r) {
		if(!$first) echo(",") ;
		echo "{" ;
		echo "\"_id\":\"" . $r['_id'] . "\",";
		echo "\"nom\":\"" . $r['nom'] ."\",";
		echo "\"prenom\":\"" . $r['prenom'] ."\",";
		echo "\"num\":\"" . $r['num'] ."\",";
		echo "\"email\":\"" . $r['email'] ."\",";
		echo "\"salle_id\":\"" . $r['salle_id'] ."\",";
		echo "\"date_aj\":\"" . $r['date_aj'] ."\"";
		echo "}" ;
		$first = false;
	}
	echo "]}";
?>
