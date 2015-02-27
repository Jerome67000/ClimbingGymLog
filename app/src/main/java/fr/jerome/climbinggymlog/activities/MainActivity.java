package fr.jerome.climbinggymlog.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Date;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.CotationDB;
import fr.jerome.climbinggymlog.data.StyleVoieDB;
import fr.jerome.climbinggymlog.data.TypeEscDB;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;
import fr.jerome.climbinggymlog.view.MyPagerAdapter;
import fr.jerome.climbinggymlog.view.fragments.EvenementsFragment;
import fr.jerome.climbinggymlog.view.fragments.ResumeFragment;
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
         * AppManager pour les objets statiques
         **/
        AppManager.setClient(getBaseContext());
        AppManager.setCotations(new CotationDB(this).getAllCotations());
        AppManager.setTypeEsc(new TypeEscDB(this).getAllTypes());
        AppManager.setStyleVoie(new StyleVoieDB(this).getAllStyles());

        Log.d("sysTime value", String.valueOf(AppManager.sysTime));


        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add(new ResumeFragment());
        fragments.add(new SeancesFragment());
        fragments.add(new StatistiquesFragment());
        fragments.add(new EvenementsFragment());

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),  fragments);
        viewPager.setAdapter(myPagerAdapter);
        slidingTabLayout.setViewPager(viewPager);

        SeanceDB seanceDB = new SeanceDB(this);
        Seance newSeance = new Seance("aa", new Date(AppManager.sysTime), new Date(AppManager.sysTime), "aa", "aa", AppManager.client);

        seanceDB.insert(newSeance);
        VoieDB voieDB = new VoieDB(this);

        for (Cotation c : AppManager.cotations) {
            if (!c.isPlus())
                voieDB.insert(new Voie(0, c.getNom(), c, AppManager.typesEsc.get(0), AppManager.styleVoies.get(0), true, true, "nn", 1));
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
