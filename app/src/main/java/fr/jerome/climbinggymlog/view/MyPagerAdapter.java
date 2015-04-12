package fr.jerome.climbinggymlog.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.view.fragments.StatistiquesFragment;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    public static final String UI_TAB_SEANCES = "SEANCES";
    public static final String UI_TAB_STATS = "STATS";
    public static final String UI_TAB_EVENEMENTS = "EVENEMENTS";

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){

        super(fm);
        this.fragments = fragments;
    }



    @Override
    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0 : return UI_TAB_SEANCES;
            case 1 : return UI_TAB_STATS;
            case 2 : return UI_TAB_EVENEMENTS;
        }
        return null;
    }
}

