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

import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.StyleVoie;
import fr.jerome.climbinggymlog.models.TypeEsc;
import fr.jerome.climbinggymlog.models.Voie;
import fr.jerome.climbinggymlog.view.custom.CotationPicker;
import fr.jerome.climbinggymlog.view.custom.PickerWithCustonFontSize;
import fr.jerome.climbinggymlog.view.custom.StyleVoiePicker;
import fr.jerome.climbinggymlog.view.custom.TypeEscaladePicker;

/**
 * Created by jerome on 17/02/15.
 */
public class AddVoieDialog extends DialogFragment {

    public static final String KEY_PREFIX = "fr.jerome.climbinggymlog.view.dialog.AddVoieDialog.";
    public static final String ARG_IDSEANCE_KEY = KEY_PREFIX + "idseance-key";
    public static final String ARG_NBVOIES_KEY = KEY_PREFIX + "nbvoies-key";

    private View dialogView;
    private CotationPicker cotationPicker;
    private TypeEscaladePicker typeEscPicker;
    private PickerWithCustonFontSize styleVoiePicker;

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
        seanceId = getArguments().getInt(ARG_IDSEANCE_KEY);
        nextVoieNumber = getArguments().getInt(ARG_NBVOIES_KEY) + 1;
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
        cotationPicker = (CotationPicker) dialogView.findViewById(R.id.cotation_picker);

        /** Type escalade Picker */
        typeEscPicker = (TypeEscaladePicker) dialogView.findViewById(R.id.type_escalade_picker);

        /** Style voie Picker */
        styleVoiePicker = (StyleVoiePicker) dialogView.findViewById(R.id.style_voie_picker);


        final ToggleButton plusToggleButton = (ToggleButton) dialogView.findViewById(R.id.cotation_plus);
        plusToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cotationPicker.setPlus(plusToggleButton.isChecked());
            }
        });
    }

    /**
     * Récupère les info de la Dialog pour créer un objet Voie et l'inserer dans la DB
     */
    private void createNewVoie() {

        VoieDB voieDB = new VoieDB(dialogView.getContext());

        Cotation cotation;
        if (cotationPicker.isPlus()) {
            int value = cotationPicker.getValue() * 2 + 1;
            cotation = AppManager.cotations.get(value);
        }
        else {
            int value = cotationPicker.getValue() * 2;
            cotation = AppManager.cotations.get(value);
        }

        TypeEsc typeEscValue = AppManager.typesEsc.get(typeEscPicker.getValue());
        StyleVoie styleVoieValue = AppManager.styleVoies.get(styleVoiePicker.getValue());

        ToggleButton tgVoieReussi = (ToggleButton) dialogView.findViewById(R.id.voie_reussi);
        ToggleButton tgVoieAVue= (ToggleButton) dialogView.findViewById(R.id.voie_a_vue);

        String titre = cotation.getNom() + " #" + nextVoieNumber + " " + styleVoieValue.getStyle();
        String note = ((EditText) dialogView.findViewById(R.id.note_new_voie)).getText().toString();

        newVoie = new Voie(titre, cotation, typeEscValue, styleVoieValue, tgVoieReussi.isChecked(), tgVoieAVue.isChecked(), note, seanceId, AppManager.client.getId());
        voieDB.insert(newVoie);
        voieDB.putVoieOnWebDB(newVoie);
        voieDB.close();
    }
}
