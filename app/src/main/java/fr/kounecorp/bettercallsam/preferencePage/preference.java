package fr.kounecorp.bettercallsam.preferencePage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import fr.kounecorp.bettercallsam.R;

public class preference extends AppCompatActivity {

    private EditText taille,poids;
    private RadioButton rMale, rFemale;
    private Button bSave;

    public static final String SHARED_PREFS = "Preference";
    public static final String TAILLE = "Taille";
    public static final String POIDS = "Poids";
    public static final String SEXE = "Sexe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        this.taille = (EditText)findViewById(R.id.Edit_taille);
        this.poids = (EditText)findViewById(R.id.Edit_poids);

        this.rFemale = (RadioButton)findViewById(R.id.rb_female);
        this.rMale = (RadioButton)findViewById(R.id.rb_male);

        this.bSave = (Button)findViewById(R.id.Button_save);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sauvegarder();
            }
        });
        charger();
    }

    private void sauvegarder() {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(TAILLE, taille.getText().toString());
        editor.putString(POIDS, poids.getText().toString());
        editor.putBoolean(SEXE, rMale.isChecked());

        editor.apply();
        Toast.makeText(this,"Données Sauvegardés",Toast.LENGTH_SHORT).show();

    }

    private void charger() {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        taille.setText(sp.getString(TAILLE,""));
        poids.setText(sp.getString(POIDS,""));
        rMale.setChecked(sp.getBoolean(SEXE,true));
        rFemale.setChecked(!sp.getBoolean(SEXE,true));

    }
}