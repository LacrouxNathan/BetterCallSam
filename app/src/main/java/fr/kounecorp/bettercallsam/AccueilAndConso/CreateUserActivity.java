package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.kounecorp.bettercallsam.R;

public class CreateUserActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private Button btnAddUser;
    private EditText editTextNomC, editTextTailleC, editTextPoidsC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        editTextNomC = (EditText) findViewById(R.id.editTextNomC);
        editTextTailleC = (EditText) findViewById(R.id.editTextTailleC);
        editTextPoidsC = (EditText) findViewById(R.id.editTextPoidsC);
        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        mDatabaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("userName");
        editTextNomC.setText(name);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterUser(v);
                Intent intent = new Intent(CreateUserActivity.this, AccueilActivity.class);
                intent.putExtra("userName",editTextNomC.getText().toString());
                startActivity(intent);

            }
        });

    }

    private void ajouterUser(View v) {
        String NOM = editTextNomC.getText().toString();
        String TAILLE = editTextTailleC.getText().toString();
        int T = Integer.parseInt(TAILLE);
        String POIDS = editTextPoidsC.getText().toString();
        int P = Integer.parseInt(POIDS);
        mDatabaseHelper.createUser(NOM,T,P,mDatabaseHelper.getWritableDatabase());
        mDatabaseHelper.close();
    }
}
