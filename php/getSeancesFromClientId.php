<?php
	// Connexion BDD
	include "pdo.php";

	// Requete
	$clientId = $_GET['clientid'];
	$query = "SELECT * FROM Seances WHERE client_id = $clientId";

	// Affichage du JSON
	$first = true ;
	echo "{\"Seances\":[" ;
	foreach ($pdo->query($query) as $r) {
		if(!$first) echo(",") ;
		echo "{" ;
		echo "\"_id\":\"" . $r['_id'] . "\"," ;
		echo "\"nom\":\"" . $r['nom'] ."\"," ;
		echo "\"date_seance\":\"" . $r['date_seance'] ."\"," ;
		echo "\"nom_salle\":\"" . $r['nom_salle'] ."\"," ;
		echo "\"note\":\"" . $r['note'] ."\"," ;
		echo "\"client_id\":\"" . $r['client_id'] ."\"" ;
		echo "}" ;
		$first = false;
	}
	echo "]}";
?>
