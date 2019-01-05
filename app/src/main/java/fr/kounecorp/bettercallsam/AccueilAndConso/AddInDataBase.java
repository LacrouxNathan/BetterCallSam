package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.kounecorp.bettercallsam.R;

public class AddInDataBase extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnDelAll, btnViewData;
    private EditText editTextNom, editTextDegre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in_data_base);
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextDegre = (EditText) findViewById(R.id.editTextDegre);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelAll = (Button) findViewById(R.id.btnDelAll);
        btnViewData = findViewById(R.id.btnViewData);
        mDatabaseHelper = new DatabaseHelper(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterAlcool(v);
                Toast.makeText(getApplicationContext(),"Alcool Ajouté",Toast.LENGTH_SHORT).show();
                editTextNom.setText("");
                editTextDegre.setText("");

            }
        });

        btnDelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supDonnees(v);

            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddInDataBase.this, ListDataActivity.class);
                intent.putExtra("act","addindatabase");
                startActivity(intent);

            }
        });

    }


    public void ajouterAlcool(View view ) {
        String NOM = editTextNom.getText().toString();
        String DEG = editTextDegre.getText().toString();
        int DEGRE = Integer.parseInt(DEG);
        mDatabaseHelper.addInfo(NOM,DEGRE,mDatabaseHelper.getWritableDatabase());
        mDatabaseHelper.close();
    }

    public void supDonnees(View view) {
        mDatabaseHelper.delAllInfo(mDatabaseHelper.getWritableDatabase());
        mDatabaseHelper.close();
    }

}
