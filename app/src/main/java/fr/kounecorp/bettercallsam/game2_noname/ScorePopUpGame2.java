package fr.kounecorp.bettercallsam.game2_noname;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.kounecorp.bettercallsam.R;

public class ScorePopUpGame2 extends Activity {

    private int[] scores;
    private int scoreReactTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.score_pop_up_window_game2);
        int r = getResources().getColor(R.color.RTRouge);
        int g = getResources().getColor(R.color.RTVert);

        this.scoreReactTime = getIntent().getIntExtra("avg", 0);
        this.scores = getIntent().getIntArrayExtra("scores");

        TextView score1Text = findViewById(R.id.score1Text);
        TextView score2Text = findViewById(R.id.score2Text);
        TextView score3Text = findViewById(R.id.score3Text);

        score1Text.setText(getString(R.string.textScoreNGame2,1));
        score2Text.setText(getString(R.string.textScoreNGame2,2));
        score3Text.setText(getString(R.string.textScoreNGame2,3));

        TextView score1Val = findViewById(R.id.score1Val);
        TextView score2Val = findViewById(R.id.score2Val);
        TextView score3Val = findViewById(R.id.score3Val);

        int col = (scores[0] <= 0) ? r : g;
        score1Val.setTextColor(col);
        col = (scores[1]-scores[0] <= 0) ? r : g;
        score2Val.setTextColor(col);
        col = (scores[2]-scores[1] <= 0) ? r : g;
        score3Val.setTextColor(col);

        score1Val.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                            Math.abs(scores[0]),
                                                            scores[0]));

        score2Val.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                    Math.abs(scores[1]-scores[0]),
                                                scores[1]-scores[0]));

        score3Val.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                    Math.abs(scores[2]-scores[1]),
                                                scores[2]-scores[1]));

        Button continuer = findViewById(R.id.btnContinuer);

        //TODO listener lançant le troisième et dernier jeu
        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game3 = new Intent(ScorePopUpGame2.this,Game2.class);
                game3.putExtra("avg", scoreReactTime);
                game3.putExtra("scores", scores);
                startActivity(game3);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Empeche l'utilisateur a faire retour, il est obligé de cliquer sur "Continuer"
        Toast.makeText(getApplicationContext(), R.string.BackInfo, Toast.LENGTH_SHORT).show();
    }

}
