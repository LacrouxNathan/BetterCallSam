package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.kounecorp.bettercallsam.R;


public class PageAlcool extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private TextView nomalcool,degalcool,addalcoolemie;
    private Button btnAddAlconso;
    private EditText editTextNbV;
    private TextView nbverres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_page_alcool);
        addalcoolemie = (TextView) findViewById(R.id.addalcoolemie);
        nomalcool = (TextView) findViewById(R.id.nomalcool);
        degalcool = (TextView) findViewById(R.id.degalcool);
        btnAddAlconso = (Button) findViewById(R.id.btnAddAlconso);
        editTextNbV = (EditText) findViewById(R.id.editTextNbV);
        nbverres = (TextView) findViewById(R.id.nbverres);
        Intent intent = getIntent();
        final int pos = intent.getExtras().getInt("position");
        String iduserString = "";
        if (intent.getExtras().getString("ID") != null) {
            iduserString = intent.getExtras().getString("ID");
            btnAddAlconso.setClickable(true);
            btnAddAlconso.setEnabled(true);
        } else {
            iduserString = "999";
            btnAddAlconso.setClickable(false);
            btnAddAlconso.setEnabled(false);
        }
        final int iduser = Integer.parseInt(iduserString);
        Alcool A = mDatabaseHelper.getDataWhereId(pos+1);
        nomalcool.setText(A.getNomAlc());
        int deg = A.getDegre();
        String d = Integer.toString(deg);
        degalcool.setText("Degré : " +d);
        editTextNbV.setText("1");
        final String[] nbVString = {"0"};
        nbVString[0] = editTextNbV.getText().toString();
        final int[] nbV = {0};
        nbV[0] = Integer.parseInt(nbVString[0]);
        double alcoolemieint = nbV[0]*0.2;
        String alcoolemiestring = Double.toString(alcoolemieint);
        addalcoolemie.setText("Ajout Alcoolémie :" +alcoolemiestring );



        btnAddAlconso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.addConsomation(iduser,pos+1,nbV[0]);
                Toast.makeText(getApplicationContext(),nbV[0]+" Verres Ajoutés",Toast.LENGTH_SHORT).show();
            }
        });

        editTextNbV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    int valueOFs = Integer.parseInt(s.toString());
                    double alcoolemieint = valueOFs * 0.2;
                    String alcoolemiestring = Double.toString(alcoolemieint);
                    alcoolemiestring = String.format("%.2f", alcoolemieint);
                    addalcoolemie.setText("Ajout Alcoolémie : " + alcoolemiestring);
                    nbVString[0] = editTextNbV.getText().toString();
                    nbV[0] = Integer.parseInt(nbVString[0]);
                }
            }
        });


    }
}
