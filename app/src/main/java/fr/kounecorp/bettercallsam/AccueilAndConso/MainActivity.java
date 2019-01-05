package fr.kounecorp.bettercallsam.AccueilAndConso;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.kounecorp.bettercallsam.R;


public class MainActivity extends AppCompatActivity {



    DatabaseHelper mDatabaseHelper;
    private Button btnOK, btnAdmin;
    private EditText editTextNomU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNomU = (EditText) findViewById(R.id.editTextNomU);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnAdmin = (Button) findViewById(R.id.btnAdmin);



        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilisateur u = chercherUser(v);
                if (u == null) {
                    Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
                    intent.putExtra("userName",editTextNomU.getText().toString());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
                    intent.putExtra("userName",editTextNomU.getText().toString());
                    startActivity(intent);
                }

            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddInDataBase.class);
                startActivity(intent);
            }
        });






    }





    private Utilisateur chercherUser(View v) {
        Utilisateur U;
        String NOM = editTextNomU.getText().toString();
        U = mDatabaseHelper.getUserWhereName(NOM);
        return U;
    }


}
