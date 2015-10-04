package com.rocdev.android.takenlijst;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    TakenlijstDB db;
    PagerAdapter adapter;
    ViewPager pager;
    int tabPos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        db = new TakenlijstDB(getApplicationContext());
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (String lijstnaam: TakenlijstDB.LIJST_NAMEN) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(lijstnaam);
            tab.setTag(lijstnaam);
            tabLayout.addTab(tab);
        }
        //tabLayout.addTab(tabLayout.newTab().setText("Zakelijk"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabPos = tabLayout.getSelectedTabPosition();
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (savedInstanceState != null) {
            int tabPos = savedInstanceState.getInt("tabPos");
            pager.setCurrentItem(tabPos);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        int tabPos = pager.getCurrentItem();
        outState.putInt("tabPos", tabPos);
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

        else if (id == R.id.menuAddTaak) {
            Intent intent = new Intent(this, AddEditActivity.class);
            intent.putExtra("tab", TakenlijstDB.LIJST_NAMEN[tabLayout.getSelectedTabPosition()]);
            startActivity(intent);
        }

        else if (id == R.id.menuDelete) {
            //verberg taken die afgerond zijn
            TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
            String tabTag = tab.getTag().toString();
            ArrayList<Taak> taken = db.getTaken(tabTag);
            for (Taak t: taken) {
                if (t.getDatumMillisVoltooid() > 0) {
                    t.setVerborgen(Taak.TRUE);
                    db.updateTaak(t);
                }
            }
            int currentIndex = pager.getCurrentItem();
            LijstFragment huidigFragment = adapter.getFragment(currentIndex);
            huidigFragment.refreshTaskList();



        }

        return super.onOptionsItemSelected(item);
    }
}
