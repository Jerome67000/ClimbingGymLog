package fr.jerome.climbinggymlog.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.LicensesDialogFragment;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.GnuLesserGeneralPublicLicense21;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;
import fr.jerome.climbinggymlog.R;

/**
 * Created by jerome on 03/05/15.
 */
public class PreferencesActivity extends PreferenceActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareLayout();
        addPreferencesFromResource(R.xml.preferences);
        toolBar.setTitle(getString(R.string.title_activity_preferences));
    }

    // Permetant d'avoir la Toolbar dans une PreferenceActivity
    private void prepareLayout() {
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View content = root.getChildAt(0);
        LinearLayout toolbarContainer = (LinearLayout) View.inflate(this, R.layout.activity_preferences, null);

        root.removeAllViews();
        toolbarContainer.addView(content);
        root.addView(toolbarContainer);

        toolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar_pref);
        toolBar.setTitleTextColor(Color.WHITE);
        toolBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color)));
        toolBar.setNavigationIcon(R.drawable.ic_arrow_left);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        switch (preference.getKey()) {
            case "pref_key_interface_def_val" : {

            } break;
            case "pref_key_notifications_new_event" : {

            } break;
            case "pref_key_notifications_event_imm" : {

            } break;
            case "pref_key_compte_modifier" : {

            } break;
            case "pref_key_compte_deco" : {

            } break;
            case "pref_key_infoleg_licence" : {
                showLicences();
            } break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    public void showLicences() {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("MPAndroidChart", "https://github.com/PhilJay/MPAndroidChart", "Copyright 2015 Philipp Jahoda", new ApacheSoftwareLicense20()));

        new LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }
}
