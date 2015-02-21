package fr.jerome.climbinggymlog.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ToggleButton;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.AppManager;
import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.database.VoieDB;
import fr.jerome.climbinggymlog.model.Cotation;
import fr.jerome.climbinggymlog.model.Seance;
import fr.jerome.climbinggymlog.model.StyleVoie;
import fr.jerome.climbinggymlog.model.TypeEsc;
import fr.jerome.climbinggymlog.model.Voie;

/**
 * Created by jerome on 17/02/15.
 */
public class AddVoieDialog extends DialogFragment {

    private NumberPicker cotationPicker;
    private NumberPicker typeEscPicker;
    private NumberPicker styleVoiePicker;

    private long idSeance;

    public AddVoieDialog(long idSeance) {

        this.idSeance = idSeance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        final View dialogView = inflater.inflate(R.layout.dialog_add_voie, null);

        builder.setMessage("Nouvelle voie");
        builder.setView(dialogView);

        initPickers(dialogView);
//        initEditText(dialogView);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Insertion de la séance dans la BDD
                createNewVoie(dialogView);

                // Ouverture de la liste des voies concernant cette séance
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // La Dialog se ferme, on retourne sur la liste des séances
            }
        });

        return builder.create();
    }

    /**
     * Initialisation du custom date picker
     * @param dialogView la View représentant la Dialog
     */
    private void initPickers(View dialogView) {

        /** Cotations Picker */
        cotationPicker = (NumberPicker) dialogView.findViewById(R.id.cotation_picker);
        ArrayList<Cotation> cotations = (ArrayList<Cotation>) AppManager.cotations;

        String[] cotationValues = new String[cotations.size()];

        for (Cotation c : cotations) {

            int index = (int) (c.getId() - 1);
            cotationValues[index] = c.getDifficulte();
        }

        cotationPicker.setMaxValue(cotationValues.length - 1);
        cotationPicker.setMinValue(0);
        cotationPicker.setDisplayedValues(cotationValues);
        cotationPicker.setValue(30);
        cotationPicker.setWrapSelectorWheel(false);


        /** Type escalade Picker */
        typeEscPicker = (NumberPicker) dialogView.findViewById(R.id.type_escalade_picker);
        ArrayList<TypeEsc> typesEsc = (ArrayList<TypeEsc>) AppManager.typesEsc;

        String[] typeEscValues = new String[typesEsc.size()];

        for (TypeEsc t : typesEsc) {

            int index = (int) (t.getId() - 1);
            typeEscValues[index] = t.getType();
        }

        typeEscPicker.setMaxValue(typeEscValues.length - 1);
        typeEscPicker.setMinValue(0);
        typeEscPicker.setDisplayedValues(typeEscValues);
        typeEscPicker.setWrapSelectorWheel(false);


        /** Type escalade Picker */
        styleVoiePicker = (NumberPicker) dialogView.findViewById(R.id.style_voie_picker);
        ArrayList<StyleVoie> stylesVoie = (ArrayList<StyleVoie>) AppManager.styleVoies;

        String[] styleVoieValues = new String[stylesVoie.size()];

        for (StyleVoie s : stylesVoie) {

            int index = (int) (s.getId() - 1);
            styleVoieValues[index] = s.getStyle();
        }

        styleVoiePicker.setMaxValue(styleVoieValues.length - 1);
        styleVoiePicker.setMinValue(0);
        styleVoiePicker.setDisplayedValues(styleVoieValues);
        styleVoiePicker.setWrapSelectorWheel(false);
    }

    /**
     * Récupère les info de la Dialog pour créer un objet Voie et l'inserer dans la DB
     * @param dialogView
     */
    private void createNewVoie(View dialogView) {

        VoieDB voieDB = new VoieDB(dialogView.getContext());

        Cotation cotation = AppManager.cotations.get(cotationPicker.getValue());
        TypeEsc typeEsc = AppManager.typesEsc.get(typeEscPicker.getValue());
        StyleVoie styleVoie = AppManager.styleVoies.get(styleVoiePicker.getValue());

        ToggleButton tgVoieReussi = (ToggleButton) dialogView.findViewById(R.id.voie_reussi);
        ToggleButton tgVoieAVue= (ToggleButton) dialogView.findViewById(R.id.voie_a_vue);

        // FIXME numéro de voie
        String titre = cotation.getDifficulte() + " #01 "+ styleVoie.getStyle();


        String note = ((EditText) dialogView.findViewById(R.id.note_new_voie)).getText().toString();

        // FIXME passer le numéro de séance dans l'intent
        Voie newVoie = new Voie(idSeance, titre, cotation, typeEsc, styleVoie, tgVoieReussi.isChecked(), tgVoieAVue.isChecked(), note, null);
        voieDB.insert(newVoie);
        voieDB.close();
    }
}
