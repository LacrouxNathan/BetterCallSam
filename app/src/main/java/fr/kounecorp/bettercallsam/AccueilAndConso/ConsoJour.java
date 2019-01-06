package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fr.kounecorp.bettercallsam.R;

public class ConsoJour extends AppCompatActivity {

    ListView mListView;
    DatabaseHelper mDatabaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conso_jour);
        mDatabaseHelper = new DatabaseHelper(this);
        mListView = (ListView) findViewById(R.id.listViewConsoJour);

        Intent intent = getIntent();
        //récupération de l'ID de la conso
        final int iDC = intent.getExtras().getInt("position");
        final String idU = intent.getExtras().getString("iduser");
        //récupération de la date de la conso
        String date = mDatabaseHelper.getDateWhereID(iDC+1);
        date = date.substring(0,10);
        Log.e("TEST",date);
        Cursor data = mDatabaseHelper.getConsoWhereDateAndAlcAndUtil(date,mDatabaseHelper.getIDAlcWhereIDCONSO(iDC+1),idU);
        ArrayList<Consommer> consoJourList = new ArrayList<>();

        while (data.moveToNext()) {
            String idAlc = data.getString(1);
            int nbV = data.getInt(3);
            String heure = data.getString(4);
            int deg = mDatabaseHelper.getDegWhereID(idAlc);
            String nomAlc = mDatabaseHelper.getDataWhereId(Integer.parseInt(idAlc)).getNomAlc();

            Consommer C = new Consommer(nomAlc, nbV, heure, deg);
            consoJourList.add(C);

        }




        ConsoJourListAdapter adapter = new ConsoJourListAdapter(this,R.layout.adapter_view,consoJourList);
        mListView.setAdapter(adapter);













    }




}

