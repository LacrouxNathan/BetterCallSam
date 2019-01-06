package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.kounecorp.bettercallsam.R;


public class AccueilActivity extends AppCompatActivity {

    private TextView nomuser,textJeux,deco;

    private DatabaseHelper mDatabaseHelper;
    private Button btnAddConso, btnViewConso;

    private Utilisateur U;


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

        Intent intent = getIntent();
        String name = intent.getExtras().getString("userName");
        final String ID = mDatabaseHelper.getIDWhereName(name);
        U = mDatabaseHelper.getUserWhereName(name);

        nomuser.setText(String.valueOf(U.getName()));


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



    }

    protected ArrayList<Consommer> getConsoUtilisateur(String IDUtilisateur, String dateC) {
        Cursor data = mDatabaseHelper.getConsoWhereIDAndDate(IDUtilisateur,dateC);
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




}
