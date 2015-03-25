package fr.jerome.climbinggymlog.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Date;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.data.EvenementDB;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.CotationDB;
import fr.jerome.climbinggymlog.data.StyleVoieDB;
import fr.jerome.climbinggymlog.data.TypeEscDB;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Evenement;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;
import fr.jerome.climbinggymlog.view.MyPagerAdapter;
import fr.jerome.climbinggymlog.view.fragments.EvenementsFragment;
import fr.jerome.climbinggymlog.view.googletools.SlidingTabLayout;
import fr.jerome.climbinggymlog.view.fragments.SeancesFragment;
import fr.jerome.climbinggymlog.view.fragments.StatistiquesFragment;


public class MainActivity extends ActionBarActivity {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * ActionBar
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color)));
            }
        }

        /**
         * AppManager pour les objets statiques
         **/
        AppManager.init(getBaseContext());

//        if (AppManager.client.getId() < 1) {
//            Intent i = new Intent(this, LoginActivity.class);
//            this.startActivity(i);
//            AppManager.setClient(getBaseContext());
//        }

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

        EvenementDB evenementDB = new EvenementDB(this);
        evenementDB.insert(new Evenement(1, "titreTest", "Description test", new Date(AppManager.sysTime), 19, 1));
        evenementDB.insert(new Evenement(2, "titreTest2", "Description test2", new Date(AppManager.sysTime), 1730, 1));
        evenementDB.insert(new Evenement(3, "titreTest3", "Description test3", new Date(AppManager.sysTime), 21, 1));

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
