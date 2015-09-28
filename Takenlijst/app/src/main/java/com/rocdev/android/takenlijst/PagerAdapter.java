package com.rocdev.android.takenlijst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by piet on 28-09-15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int aantalTabs;


    public PagerAdapter(FragmentManager fm, int aantalTabs) {
        super(fm);
        this.aantalTabs = aantalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LijstPersoonlijkFragment tab1 = new LijstPersoonlijkFragment();
                return tab1;
            case 1:
                LijstZakelijkFragment tab2 = new LijstZakelijkFragment();
                return tab2;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return aantalTabs;
    }
}
