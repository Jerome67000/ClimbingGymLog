package fr.jerome.climbinggymlog.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.data.ClientDB;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.models.Client;
import fr.jerome.climbinggymlog.view.MyPagerAdapter;
import fr.jerome.climbinggymlog.view.fragments.EvenementsFragment;
import fr.jerome.climbinggymlog.view.fragments.ResumeSeanceFragment;
import fr.jerome.climbinggymlog.view.googletools.SlidingTabLayout;
import fr.jerome.climbinggymlog.view.fragments.SeancesFragment;
import fr.jerome.climbinggymlog.view.fragments.StatistiquesFragment;


public class MainActivity extends AppCompatActivity {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private MyPagerAdapter myPagerAdapter;

    public static final String SHARED_PREF_NAME = "MySharedPref";
    public static final String PREFKEY_SHOW_LOG_ACT = "ShowLoginActivity";
    public static final String PREFKEY_NUM_CLIENT = "NumClient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * SharedPreferences
         **/
        initSharedPref();

        /**
         * Toolbar
         **/
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_main);
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_launcher);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color)));
            }
        }

        /**
         * AppManager
         **/
        AppManager.init(this);

        /**
         * Affichage de LoginActivity si aucun compte associé dans les SharedPreferences
         * sinon, initialisation et lancement de l'interface principale
         **/
        if (AppManager.showLoginActivity) {
            Intent i = new Intent(this, LoginActivity.class);
            this.startActivityForResult(i, LoginActivity.ACT_ID);
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, 0);
            String numClient = sharedPreferences.getString(PREFKEY_NUM_CLIENT, "client inconnu");
            Client client = new ClientDB(this).select(numClient);
            AppManager.setClient(client);
            initApp();
        }
    }

    /**
     * Initialisation des SharedPreferences
     */
    private void initSharedPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!sharedPreferences.contains(PREFKEY_SHOW_LOG_ACT))
            editor.putBoolean(PREFKEY_SHOW_LOG_ACT, true);
        editor.commit();
        AppManager.showLoginActivity = sharedPreferences.getBoolean(PREFKEY_SHOW_LOG_ACT, true);
    }

    /**
     * Initialisation et lancement de l'interface principale
     */
    private void initApp() {
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add(new SeancesFragment());
        fragments.add(new StatistiquesFragment());
        fragments.add(new EvenementsFragment());

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myPagerAdapter);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    if (fragments.get(1) != null) {
                        SeanceDB seanceDB = new SeanceDB(MainActivity.this);
                        ResumeSeanceFragment resumeSeanceFragment = ((StatistiquesFragment) fragments.get(position)).getResumeSeanceFragment();
                        resumeSeanceFragment.setSeanceId(seanceDB.getLastSeanceId());
                        resumeSeanceFragment.refreshView();
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Appelé au retour de LoginActivity
        if (requestCode == LoginActivity.ACT_ID) {
            if (resultCode == LoginActivity.CLIENT_FINDED) {
                initApp();
            }
            else if (resultCode == LoginActivity.CLIENT_NOT_FIND)
                Toast.makeText(this, "Client non trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, PreferencesActivity.class);
            this.startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
