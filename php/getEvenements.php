<?php
	// Connexion BDD
	include "pdo.php";

	// Requete
	$salleId = $_GET['salleid'];
	$lastEventId = $_GET['lasteventid'];
	$query = "SELECT * FROM Evenements WHERE salle_id = $salleId AND _id > $lastEventId";

	// Affichage du JSON
	$first = true ;
	echo "{\"Evenements\":[" ;
	foreach ($pdo->query($query) as $r) {
		if(!$first)
			echo(",") ;
		echo "{" ;
		echo "\"_id\":\"" . $r['_id'] . "\"," ;
		echo "\"titre\":\"" . $r['titre'] ."\"," ;
		echo "\"description\":\"" . $r['description'] ."\"," ;
		echo "\"date\":\"" . $r['date'] ."\"," ;
		echo "\"horaire\":\"" . $r['horaire'] ."\"," ;
		echo "\"salle_id\":\"" . $r['salle_id'] ."\"" ;
		echo "}" ;
		$first = false;
	}
	echo "]}";
?>
