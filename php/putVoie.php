<?php
	// Connexion BDD
	include "pdo.php";

	// requete
	$local_id = $_POST['localid'];
    $nom = $_POST['nom'];
    $cotationId = $_POST['cotation_id'];
    $typeEscId = $_POST['type_escalade_id'];
    $styleVoieId = $_POST['style_voie_id'];
    $reussi = $_POST['reussie'];
    $aVue = $_POST['a_vue'];
    $note = $_POST['note'];
    $seanceId = $_POST['seance_id'];
    $photoNom = $_POST['photo_nom'];

	$query = "INSERT INTO Voies (nom, cotation_id, type_escalade_id, style_voie_id, reussie, a_vue, note, seance_id, photo_nom)
						values( '$nom', '$cotationId', '$typeEscId', '$styleVoieId', '$reussi', '$aVue', '$note', '$seanceId', '$photoNom')";

	$pdo->query($query);

	// renvoi l'id de l'élément inséré
	echo $pdo->lastInsertId();
?>
