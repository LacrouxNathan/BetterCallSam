package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import fr.kounecorp.bettercallsam.R;


public class AccueilActivity extends AppCompatActivity {

    private TextView nomuser,textJeux,deco,tvconso;

    private DatabaseHelper mDatabaseHelper;
    private Button btnAddConso, btnViewConso;

    private Utilisateur U;

    private Button setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        mDatabaseHelper = new DatabaseHelper(this);
        btnAddConso = findViewById(R.id.btnAddConso);
        btnViewConso = findViewById(R.id.btnViewConso);
        nomuser = findViewById(R.id.nomuser);
        textJeux = findViewById(R.id.textJeux);
        deco = findViewById(R.id.deco);
        setting = (Button)findViewById(R.id.btnSetting);
        tvconso = (TextView)findViewById(R.id.Tvconso);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("userName");
        final String ID = mDatabaseHelper.getIDWhereName(name);
        U = mDatabaseHelper.getUserWhereName(name);

        nomuser.setText(String.valueOf(U.getName()));
        ArrayList<Consommer> test = getConsoUtilisateur(ID);


        btnAddConso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ListDataActivity.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        btnViewConso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ConsoActivity.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        textJeux.setPaintFlags(textJeux.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        deco.setPaintFlags(deco.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textJeux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ReactTime = new Intent(AccueilActivity.this,
                        fr.kounecorp.bettercallsam.game1_reacttime.ReactTime.class);
                ReactTime.putExtra("userName",U.getName());
                startActivity(ReactTime);
            }
        });

        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Deconnecté",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        this.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting = new Intent(AccueilActivity.this,fr.kounecorp.bettercallsam.preferencePage.preference.class);
                startActivity(setting);
            }
        });
        calculertaux(ID,test);

    }

    protected ArrayList<Consommer> getConsoUtilisateur(String IDUtilisateur) {
        Cursor data = mDatabaseHelper.getConsoWhereID(IDUtilisateur);
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
        return consoJourList;
    }


    private void calculertaux(String IDUtilisateur,ArrayList<Consommer> a) {
        SharedPreferences sp = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        boolean sexeMale = sp.getBoolean(getString(R.string.SEXE),true);
        double coef = (sexeMale) ? 0.7 : 0.6;
        double poids = Double.valueOf(sp.getString(getString(R.string.POIDS),"70"));

        double somme = 0;
        for(Consommer c : a) {
            somme += c.getNbVerres()+(c.getDeg()/10);
        }

        double conso = ((somme * 10)/poids*coef);
        DecimalFormat f = new DecimalFormat("##.00");

        tvconso.setText(String.valueOf(f.format(conso)));


    }

}
