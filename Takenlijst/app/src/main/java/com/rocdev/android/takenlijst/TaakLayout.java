package com.rocdev.android.takenlijst;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by piet on 28-09-15.
 */
public class TaakLayout extends RelativeLayout implements View.OnClickListener {
    private CheckBox checkBox;
    private TextView naamTextView;
    private TextView notitieTextView;

    private Taak taak;
    private TakenlijstDB db;
    private Context context;


    public TaakLayout(Context context) { // voor Android tools
        super(context);
    }

    public TaakLayout(Context context, Taak taak) {
        super(context);
        this.context = context;
        db = new TakenlijstDB(context);

        //inflate layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_taak, this, true);

        //maak referenties naar de widgets
        checkBox = (CheckBox) findViewById(R.id.voltooidCheckBox);
        naamTextView = (TextView) findViewById(R.id.NaamTextView);
        notitieTextView = (TextView) findViewById(R.id.NotitiesTextView);

        //set Listeners
        checkBox.setOnClickListener(this);
        this.setOnClickListener(this);

        //set taak data op widgets
        setTaak(taak);
    }

    public void setTaak(Taak taak) {
        this.taak = taak;
        naamTextView.setText(taak.getNaam());
        if (taak.getNotitie().equalsIgnoreCase("")) {
            notitieTextView.setVisibility(GONE);
        } else {
            notitieTextView.setText(taak.getNotitie());
        }
        if (taak.getDatumMillisVoltooid() > 0) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voltooidCheckBox:
                if (checkBox.isChecked()) {
                    taak.setDatumMillisVoltooid(System.currentTimeMillis());
                } else {
                    taak.setDatumMillisVoltooid(0);
                }
                db.updateTaak(taak);
                break;
            default:
                //TODO EditAddActivity maken

        }

    }
}
