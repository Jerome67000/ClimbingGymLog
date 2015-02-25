package fr.jerome.climbinggymlog.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ToggleButton;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.AppManager;
import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.database.VoieDB;
import fr.jerome.climbinggymlog.model.Cotation;
import fr.jerome.climbinggymlog.model.StyleVoie;
import fr.jerome.climbinggymlog.model.TypeEsc;
import fr.jerome.climbinggymlog.model.Voie;

/**
 * Created by jerome on 17/02/15.
 */
public class AddVoieDialog extends DialogFragment {

    public static final String KEY_PREFIX = "fr.jerome.climbinggymlog.view.dialog.AddVoieDialog.";
    public static final String ARG_IDSEANCE_KEY = KEY_PREFIX + "idseance-key";
    public static final String ARG_NBVOIES_KEY = KEY_PREFIX + "nbvoies-key";

    private View dialogView;
    private NumberPicker cotationPicker;
    private NumberPicker typeEscPicker;
    private NumberPicker styleVoiePicker;

    private int seanceId;
    private int nextVoieNumber;
    private Voie newVoie;

    public interface AddVoieDialogListener {
        void onFinishAddVoieDialog(Voie newVoie);
    }

    public static AddVoieDialog newInstance(int seanceId, int nbVoies) {
        AddVoieDialog fragment = new AddVoieDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_IDSEANCE_KEY, seanceId);
        args.putInt(ARG_NBVOIES_KEY, nbVoies);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seanceId = getArguments().getInt(ARG_IDSEANCE_KEY, -1);
        nextVoieNumber = getArguments().getInt(ARG_NBVOIES_KEY, 0) + 1;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate and set the layout for the dialog
        dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_voie, null);
        // Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Nouvelle voie");
        builder.setView(dialogView);

        initPickers();

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Insertion de la séance dans la BDD
                createNewVoie();
                // refrech listview
                AddVoieDialogListener listener = (AddVoieDialogListener) getActivity();
                listener.onFinishAddVoieDialog(newVoie);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    /**
     * Initialisation des NumberPicker
     */
    private void initPickers() {

        /** Cotations Picker */
        cotationPicker = (NumberPicker) dialogView.findViewById(R.id.cotation_picker);
        ArrayList<Cotation> cotations = (ArrayList<Cotation>) AppManager.cotations;
        String[] cotationValues = new String[cotations.size()];

        for (Cotation c : cotations) {

            int index = (int) (c.getId() - 1);
            cotationValues[index] = c.getNom();
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
     */
    private void createNewVoie() {

        VoieDB voieDB = new VoieDB(dialogView.getContext());

        Cotation cotation = AppManager.cotations.get(cotationPicker.getValue());
        TypeEsc typeEsc = AppManager.typesEsc.get(typeEscPicker.getValue());
        StyleVoie styleVoie = AppManager.styleVoies.get(styleVoiePicker.getValue());

        ToggleButton tgVoieReussi = (ToggleButton) dialogView.findViewById(R.id.voie_reussi);
        ToggleButton tgVoieAVue= (ToggleButton) dialogView.findViewById(R.id.voie_a_vue);

        String titre = cotation.getNom() + " #" + nextVoieNumber + " " + styleVoie.getStyle();
        String note = ((EditText) dialogView.findViewById(R.id.note_new_voie)).getText().toString();

        newVoie = voieDB.insert(new Voie(titre, cotation, typeEsc, styleVoie, tgVoieReussi.isChecked(), tgVoieAVue.isChecked(), note, seanceId));
        voieDB.close();
    }
}
