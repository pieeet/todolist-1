package com.rocdev.android.takenlijst;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //test taak toevoegen
        TakenlijstDB db = new TakenlijstDB(this);
        Taak taak = new Taak(1, "App testen", "", "0", "0");
        long voegToeId = db.voegTaakToe(taak);
        if (voegToeId > 0) {
            sb.append("Rij toegevoegd, id:" + voegToeId + "\n");
        }

        // test taak wijzigen
        taak.setNaam("Update test");
        int wijzigCount = db.updateTaak(taak);
        if (wijzigCount == 1) {
            sb.append("Taak gewijzigd, aantal gewijzigd: " + wijzigCount + "\n");
        }

        // test taak deleten
        int deleteCount = db.deleteTaak(voegToeId);
        if (deleteCount == 1) {
            sb.append("Taak verwijderd, aantal verwijderd: " + deleteCount + "\n");
        }

        //alle taken tonen
        TextView tv1 = (TextView) findViewById(R.id.textView1);
        ArrayList<Taak> taken = db.getTaken("Persoonlijk");
        for (Taak t: taken) {
            sb.append(t.getTaakId() + "|" + t.getNaam() + "\n");
        }
        tv1.setText(sb.toString());

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
