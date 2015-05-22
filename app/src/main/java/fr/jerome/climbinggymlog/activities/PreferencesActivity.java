package fr.jerome.climbinggymlog.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
}
