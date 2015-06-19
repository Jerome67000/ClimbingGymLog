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

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.StyleVoie;
import fr.jerome.climbinggymlog.models.TypeEsc;
import fr.jerome.climbinggymlog.models.Voie;
import fr.jerome.climbinggymlog.view.custom.MyNumberPicker;

/**
 * Created by rcdsm06 on 16/04/2015.
 */
public class EditVoieDialog extends DialogFragment {

    public static final String KEY_PREFIX = "fr.jerome.climbinggymlog.view.dialog.AddVoieDialog.";
    public static final String ARG_IDVOIE_KEY = KEY_PREFIX + "idvoie-key";
    public static final String ARG_POSITION_KEY = KEY_PREFIX + "position-key";

    private View dialogView;
    private NumberPicker cotationPicker;
    private MyNumberPicker typeEscPicker;
    private MyNumberPicker styleVoiePicker;

    private Voie voieToEdit;
    private int positionAAfficher;
    private String[] cotationValues;
    private String[] cotationValuesWithPlus;
    int currentPickerPosition;

    boolean isPlusCotation = false;
    private VoieDB voieDB;
    private ToggleButton plusToggleButton;
    private ToggleButton reussiToggleButton;
    private ToggleButton aVueToggleButton;
    private EditText note;

    public interface EditVoieDialogListener {
        void onFinishEditVoieDialog(Voie seanceId);
    }

    public static EditVoieDialog newInstance(int seanceId, int position) {
        EditVoieDialog fragment = new EditVoieDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_IDVOIE_KEY, seanceId);
        args.putInt(ARG_POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        voieDB = new VoieDB(getActivity());
        voieToEdit = voieDB.select(getArguments().getInt(ARG_IDVOIE_KEY));
        positionAAfficher = getArguments().getInt(ARG_POSITION_KEY) + 1;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate and set the layout for the dialog
        dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_voie, null);
        // Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Modifier voie");
        builder.setView(dialogView);

        initPickers();
        setCurrentData();

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Update de la séance dans la BDD
                updateVoie();
                // refrech listview
                EditVoieDialogListener listener = (EditVoieDialogListener) getActivity();
                listener.onFinishEditVoieDialog(voieToEdit);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    private void setCurrentData() {

        int valueStyleVoie = (int) voieToEdit.getStyle().getId()-1;
        int valueTypeEsc = (int) voieToEdit.getTypeEscalade().getId()-1;

        styleVoiePicker.setValue(valueStyleVoie);
        typeEscPicker.setValue(valueTypeEsc);

        if (voieToEdit.getCotation().isPlus()) {
            plusToggleButton.setChecked(true);
            isPlusCotation = true;
            currentPickerPosition = ((int) (voieToEdit.getCotation().getId()-1))/2;
        }
        else {
            plusToggleButton.setChecked(false);
            isPlusCotation = false;
            currentPickerPosition = ((int) (voieToEdit.getCotation().getId()-1))/2;
        }
        togglePlusCotationPicker();
        reussiToggleButton.setChecked(voieToEdit.isReussi());
        aVueToggleButton.setChecked(voieToEdit.isAVue());
    }

    /**
     * Initialisation des NumberPicker
     */
    private void initPickers() {

        plusToggleButton = (ToggleButton) dialogView.findViewById(R.id.cotation_plus);
        reussiToggleButton = (ToggleButton) dialogView.findViewById(R.id.voie_reussi);
        aVueToggleButton = (ToggleButton) dialogView.findViewById(R.id.voie_a_vue);

        plusToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plusToggleButton.isChecked())
                    isPlusCotation = true;
                else
                    isPlusCotation = false;

                togglePlusCotationPicker();
            }
        });

        /** Cotations Picker */
        cotationPicker = (NumberPicker) dialogView.findViewById(R.id.cotation_picker);
        final ArrayList<Cotation> cotations = (ArrayList<Cotation>) AppManager.cotations;
        int size = cotations.size()/2;
        cotationValues = new String[size];
        cotationValuesWithPlus = new String[size];

        int indexNoPlus = 0;
        int indexWithPlus = 0;
        for (Cotation c : cotations) {
            int indexCotation = (int) (c.getId() - 1);
            if (indexCotation % 2 == 0) {
                cotationValues[indexNoPlus] = c.getNom();
                indexNoPlus++;
            }
            else {
                cotationValuesWithPlus[indexWithPlus] = c.getNom();
                indexWithPlus++;
            }
        }

        cotationPicker.setMinValue(0);
        cotationPicker.setWrapSelectorWheel(false);

        if (voieToEdit.getCotation().isPlus())
            cotationPicker.setValue((int) (voieToEdit.getCotation().getId() - 1));
        else
            cotationPicker.setValue(((int) (voieToEdit.getCotation().getId() - 1)/2));

        cotationPicker.setWrapSelectorWheel(false);

        togglePlusCotationPicker();

        cotationPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentPickerPosition = cotationPicker.getValue();
            }
        });

        /** Type escalade Picker */
        typeEscPicker = (MyNumberPicker) dialogView.findViewById(R.id.type_escalade_picker);
        ArrayList<TypeEsc> typesEsc = (ArrayList<TypeEsc>) AppManager.typesEsc;
        String[] typeEscValues = new String[typesEsc.size()];

        for (TypeEsc t : typesEsc) {
            int index = (int) (t.getId() - 1);
            typeEscValues[index] = t.getType();
        }

        typeEscPicker.setMinValue(0);
        typeEscPicker.setMaxValue(typeEscValues.length - 1);
        typeEscPicker.setDisplayedValues(typeEscValues);
        typeEscPicker.setWrapSelectorWheel(false);

        /** Type escalade Picker */
        styleVoiePicker = (MyNumberPicker) dialogView.findViewById(R.id.style_voie_picker);
        ArrayList<StyleVoie> stylesVoie = (ArrayList<StyleVoie>) AppManager.styleVoies;
        String[] styleVoieValues = new String[stylesVoie.size()];

        for (StyleVoie s : stylesVoie) {

            int index = (int) (s.getId() - 1);
            styleVoieValues[index] = s.getStyle();
        }

        styleVoiePicker.setMinValue(0);
        styleVoiePicker.setMaxValue(styleVoieValues.length - 1);
        styleVoiePicker.setDisplayedValues(styleVoieValues);
        styleVoiePicker.setWrapSelectorWheel(false);
    }

    /**
     * Raffraichi l'affichage du CotationPicker avec les cotation "+" ou non
     * exemple : si isPlusCoation = true alors "5c+" est affiché, sinon "5c" est affiché
     */
    private void togglePlusCotationPicker() {

        if (isPlusCotation) {
            cotationPicker.setMaxValue(cotationValuesWithPlus.length - 1);
            cotationPicker.setDisplayedValues(cotationValuesWithPlus);
        }
        else {
            cotationPicker.setMaxValue(cotationValues.length - 1);
            cotationPicker.setDisplayedValues(cotationValues);
        }
        cotationPicker.setValue(currentPickerPosition);
        cotationPicker.setWrapSelectorWheel(false);
    }

    /**
     * Récupère les info de la Dialog pour créer un objet Voie et l'inserer dans la DB
     */
    private void updateVoie() {

        Cotation cotation;
        if (isPlusCotation) {
            int value = cotationPicker.getValue() * 2 + 1;
            cotation = AppManager.cotations.get(value);
        }
        else {
            int value = cotationPicker.getValue() * 2;
            cotation = AppManager.cotations.get(value);
        }

        TypeEsc typeEsc = AppManager.typesEsc.get(typeEscPicker.getValue());
        StyleVoie styleVoie = AppManager.styleVoies.get(styleVoiePicker.getValue());

        String titre = cotation.getNom() + " #" + positionAAfficher + " " + styleVoie.getStyle();
        note = (EditText) dialogView.findViewById(R.id.note_new_voie);

        Voie voieEdited = new Voie(voieToEdit.getId(), titre, cotation, typeEsc, styleVoie, reussiToggleButton.isChecked(), aVueToggleButton.isChecked(), note.getText().toString(), voieToEdit.getSeanceId(), AppManager.client.getId());

        voieDB.update(voieEdited);
        voieDB.close();
    }
}