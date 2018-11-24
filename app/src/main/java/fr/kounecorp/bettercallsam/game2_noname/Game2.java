package fr.kounecorp.bettercallsam.game2_noname;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fr.kounecorp.bettercallsam.R;

public class Game2 extends Activity {

    private CanvasView myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        myCanvas = findViewById(R.id.canvasView);
        TextView infoGame = findViewById(R.id.game2Info);

        myCanvas.initializeForme();
    }

    public void clearCanvas(View v) {
        myCanvas.clearCanvas();
    }

    public void newFormes(View v) {
        myCanvas.initializeForme();
        myCanvas.invalidate();
    }
}
