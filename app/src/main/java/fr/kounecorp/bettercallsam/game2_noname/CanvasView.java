package fr.kounecorp.bettercallsam.game2_noname;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.kounecorp.bettercallsam.R;


public class CanvasView extends View {

    private static final int NBPARTIESMAX = 3;

    private int scoreReactTime;

    private List<Forme> formes;
    private Random random;
    private int theChoosen;
    private int[] scores;
    private int numPartie;
    private TextView scoreValue;
    private TextView infoGame;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        this.formes = new ArrayList<>();
        this.random = new Random();
        this.scores = new int[CanvasView.NBPARTIESMAX];
        this.numPartie = 0;
    }

    //TODO initialiser les formes en fonction de la taille du Canvas
    private void initializeForme(int w, int h) {
        // w*0.2 < x < w*0.8
        // h*0.2 < y < h*0.8
        // w*0.05 < L < w*0.15
        // h*0.05 < l < h*0.15
        int nbFormes = 0;
        int w10  = (int) (w*0.1);
        int w20 = (int) (w*0.2);
        int w80 = (int) (w*0.8);

        int h10  = (int) (w*0.1);
        int h20 = (int) (h*0.2);
        int h80 = (int) (h*0.8);

        while (nbFormes < 5) {
            int x = this.random.nextInt(w80) + w20;
            int y = this.random.nextInt(h80) + h20;
            int L = this.random.nextInt(w20) + w10;
            int l = this.random.nextInt(h20) + h10;
            Rectangle newR = new Rectangle(x,y,L,l);

            if (this.formes.isEmpty()) {
                this.formes.add(newR);
                nbFormes++;
            } else {
                boolean drawable = true;
                for (Forme forme : this.formes) {
                    if (forme instanceof Rectangle) {
                        Rectangle r = (Rectangle) forme;
                        if (r.intersects(newR)) {
                            drawable = false;
                        }
                    }
                }
                if (drawable) {
                    this.formes.add(newR);
                    nbFormes++;
                }
            }
        }
    }

    public void initializeForme(int nbRects, int nbCarres, int nbCercles) {
        this.theChoosen = random.nextInt(3);
        this.printInfoGameOnView();
        this.printScoreNOnView(this.numPartie);
        this.addNRect(nbRects, (this.theChoosen == 0));
        this.addNCarre(nbCarres, (this.theChoosen == 1));
        this.addNCercle(nbCercles,(this.theChoosen == 2));
    }

    private void addNRect(int nb, boolean choosen) {
        int nbRects = 0;
        while (nbRects < nb) {
            int x = this.random.nextInt(700);
            int y = this.random.nextInt(1200);
            int L = this.random.nextInt(200)+100;
            int l = this.random.nextInt(200)+100;
            Rectangle newR = new Rectangle(x,y,L,l);
            newR.setChoosen(choosen);
            if (this.formes.isEmpty() && !(L-20 < l && l < L+20)) {
                this.formes.add(newR);
                nbRects++;
            } else if (L != l){
                boolean drawable = true;
                for (Forme forme : this.formes) {
                    if (forme.intersects(newR)) {
                        drawable = false;
                    }
                }
                if (drawable) {
                    this.formes.add(newR);
                    nbRects++;
                }
            }
        }
    }

    private void addNCarre(int nb, boolean choosen) {
        int nbCarres = 0;
        while (nbCarres < nb) {
            int x = this.random.nextInt(700);
            int y = this.random.nextInt(1200);
            int c = this.random.nextInt(200)+100;
            Carre newC = new Carre(x,y,c);
            newC.setChoosen(choosen);
            if (this.formes.isEmpty()) {
                this.formes.add(newC);
                nbCarres++;
            } else {
                boolean drawable = true;
                for (Forme forme : this.formes) {
                    if (forme.intersects(newC)) {
                        drawable = false;
                    }
                }
                if (drawable) {
                    this.formes.add(newC);
                    nbCarres++;
                }
            }
        }
    }

    private void addNCercle(int nb, boolean choosen) {
        int nbCercles = 0;
        while (nbCercles < nb) {
            int x = this.random.nextInt(700);
            int y = this.random.nextInt(1200);
            int r = this.random.nextInt(200)+100;
            Cercle newC = new Cercle(x,y,r);
            newC.setChoosen(choosen);
            if (this.formes.isEmpty()) {
                this.formes.add(newC);
                nbCercles++;
            } else {
                boolean drawable = true;
                for (Forme forme : this.formes) {
                    if (forme.intersects(newC)) {
                        drawable = false;
                    }
                }
                if (drawable) {
                    this.formes.add(newC);
                    nbCercles++;
                }
            }
        }
    }

    public void setScoreReactTime(int score) {
        this.scoreReactTime = score;
    }

    public void setInfoGameView(TextView t) {
        this.infoGame = t;
        this.infoGame.setTextColor(getResources().getColor(android.R.color.black));
    }

    public String getChoosenToString() {
        switch (this.theChoosen) {
            case 0:
                return "Rectangles";
            case 1:
                return "Carrés";
            default:
                return "Cercles";
        }
    }

    public void printInfoGameOnView() {
        infoGame.setText(this.getContext().getString(R.string.infoGame2,this.getChoosenToString()));
    }

    public void setScoreValueView(TextView t) {
        this.scoreValue = t;
    }

    private void printScoreNOnView(int n) {
        String score = getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                        Math.abs(scores[n]),
                                                        scores[n]);
        this.scoreValue.setText(score);
    }


    private void lunchPopUp() {
        Intent scorePopUp = new Intent(this.getContext(), ScorePopUpGame2.class);
        scorePopUp.putExtra("avg", this.scoreReactTime);
        scorePopUp.putExtra("scores", this.scores);
        this.getContext().startActivity(scorePopUp);
    }

    private void updateScoreN(int n) {
        int nbFormeChoosenTouched = 0;
        if (n > 0) {
            scores[n] = scores[n-1];
        } else {
            scores[n] = 0;
        }
        for (Forme forme : this.formes) {
            if (forme.isTouched() && forme.isChoosen()) {
                scores[n]++;
                nbFormeChoosenTouched++;
            } else if (forme.isTouched()) {
                scores[n]--;
            }
        }
        printScoreNOnView(n);
        nextGame(nbFormeChoosenTouched);

    }

    private void nextGame(int nbFormeChoosenTouched) {
        if (nbFormeChoosenTouched == this.numPartie+1) {
            if (this.numPartie+1 < CanvasView.NBPARTIESMAX) {
                this.clearCanvas();
                this.initializeForme(this.numPartie+2,this.numPartie+2,this.numPartie+2);
                this.numPartie++;
            } else {
                lunchPopUp();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.numPartie < CanvasView.NBPARTIESMAX) {
            for (Forme forme : this.formes) {
                forme.display(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP && this.numPartie < CanvasView.NBPARTIESMAX) {
            for (Forme forme : this.formes) {
                forme.testClick(x,y);
                invalidate();
            }
            updateScoreN(this.numPartie);
        }
        return true;
    }

    public void clearCanvas() {
        this.formes.clear();
        invalidate();
    }
}
