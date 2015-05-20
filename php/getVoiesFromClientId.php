<?php
	// Connexion BDD
	include "pdo.php";

	// Requete
	$clientId = $_GET['clientid'];
	$query = "SELECT Voies.* FROM Voies INNER JOIN Seances ON Seances.local_id=Voies.seance_id WHERE Seances.client_id=$clientId";

	// // enter your sql query
	// $query = "SELECT Voies.* FROM Voies INNER JOIN Seances ON Seances.local_id=Voies.seance_id AND Seances.client_id=$clientId WHERE Seances.client_id=$clientId";

	// Affichage du JSON
	$first = true ;
	echo "{\"Voies\":[" ;
	foreach ($pdo->query($query) as $r) {
		if(!$first) echo(",") ;
		echo "{" ;
		echo "\"_id\":\"" . $r['_id'] . "\"," ;
		echo "\"nom\":\"" . $r['nom'] ."\"," ;
		echo "\"cotation_id\":\"" . $r['cotation_id'] ."\"," ;
		echo "\"type_escalade_id\":\"" . $r['type_escalade_id'] ."\"," ;
		echo "\"style_voie_id\":\"" . $r['style_voie_id'] ."\"," ;
		echo "\"reussie\":\"" . $r['reussie'] ."\"," ;
		echo "\"a_vue\":\"" . $r['a_vue'] ."\"," ;
		echo "\"note\":\"" . $r['note'] ."\"," ;
		echo "\"seance_id\":\"" . $r['seance_id'] ."\"," ;
		echo "\"photo_nom\":\"" . $r['photo_nom'] ."\"" ;
		echo "}" ;
		$first = false;
	}
	echo "]}";
?>
