package fr.jerome.climbinggymlog.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.sql.Date;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.activities.SeanceDetailActivity;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.models.Seance;

/**
 * Created by jerome on 10/02/15.
 */
public class AddSeanceDialog extends DialogFragment {

    public static final String KEY_PREFIX = "fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog.";
    public static final String ARG_IDSEANCE_KEY = KEY_PREFIX + "idseance-key";

    private int seanceId;
    private SeanceDB seanceDB;
    private AddSeanceDialogListener listener;
    private Seance newSeance;

    public interface AddSeanceDialogListener {
        void onFinishAddSeanceDialog(Seance newSeance);

    }

    public static AddSeanceDialog newInstance(int seanceId) {
        AddSeanceDialog fragment = new AddSeanceDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_IDSEANCE_KEY, seanceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seanceId = getArguments().getInt(ARG_IDSEANCE_KEY, -1);
    }

    public void setListener(AddSeanceDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        final View dialogView = inflater.inflate(R.layout.dialog_add_seance, null);

        seanceDB = new SeanceDB(dialogView.getContext());

        builder.setMessage("Nouvelle séance");
        builder.setView(dialogView);

        initPicker(dialogView);
        initEditText(dialogView);

        seanceDB.close();

        /** OK */
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Insertion de la séance dans la BDD
                createNewSeance(dialogView);

                // refrech listview
                listener.onFinishAddSeanceDialog(newSeance);

                // Ouverture de la liste des voies concernant cette séance
                Intent intent = new Intent(getActivity(), SeanceDetailActivity.class);
                intent.putExtra(ARG_IDSEANCE_KEY, seanceId);
                startActivity(intent);
            }
        });

        /** CANCEL */
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    private void createNewSeance(View dialogView) {

        EditText newTitre = (EditText) dialogView.findViewById(R.id.titre_new_seance);
        String titre;

        if (newTitre.getText().toString().isEmpty())
            titre = newTitre.getHint().toString();
        else
            titre = newTitre.getText().toString();

        String salle = ((EditText) dialogView.findViewById(R.id.lieu_new_seance)).getText().toString();
        String note = ((EditText) dialogView.findViewById(R.id.note_new_voie)).getText().toString();

        int dateSelected = ((NumberPicker) dialogView.findViewById(R.id.date_seance_picker)).getValue();
        long date = 0;
        switch(dateSelected) {
            case 0 : date = 0; break;
            case 1 : date = 86400000; break;
            case 2 : date = 86400000*2; break;
        }

        seanceDB = new SeanceDB(dialogView.getContext());
        newSeance = new Seance(titre, new Date(AppManager.sysTime - date), salle, note, AppManager.client);
        seanceDB.insert(newSeance);
        seanceDB.putSeanceOnWebDB(newSeance);
        seanceDB.close();
    }

    /**
     * Initialisation des EditText
     * @param dialogView la View représentant la Dialog
     */
    private void initEditText(View dialogView) {

        EditText titreSeance = (EditText) dialogView.findViewById(R.id.titre_new_seance);
        EditText salleSeance = (EditText) dialogView.findViewById(R.id.lieu_new_seance);

        ArrayList<Seance> seances = (ArrayList<Seance>) seanceDB.getAllSeances();

        long seanceNumber = 1;
        // Définit le numéro de la prochaine séance si déjà des séances
        if (seances != null && !seances.isEmpty())
            seanceNumber = seances.get(seances.size() - 1).getId() + 1;

        titreSeance.setHint("Séance #" + seanceNumber);
        salleSeance.setText(AppManager.nomSalleClient);
    }

    /**
     * Initialisation du custom date picker
     * @param dialogView la View représentant la Dialog
     */
    private void initPicker(View dialogView) {

        final NumberPicker datePicker = (NumberPicker) dialogView.findViewById(R.id.date_seance_picker);

        String[] values = {"Aujourd'hui", "Hier", "Avant-hier"};

        datePicker.setMaxValue(values.length-1);
        datePicker.setMinValue(0);
        datePicker.setDisplayedValues(values);
    }
}