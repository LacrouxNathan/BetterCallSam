package fr.kounecorp.bettercallsam.game2_noname;

import android.graphics.Canvas;

public class Cercle extends Forme {

    public Cercle(float x, float y, float rayon) {
        super();
        super.setForme(x,y,x+rayon,y+rayon);
    }

    @Override
    public void display(Canvas canvas) {
        if (isTouched()) {
            if (isChoosen())
                canvas.drawOval(getForme(), this.fillGreen());
            else
                canvas.drawOval(getForme(), this.fillRed());
        } else {
            canvas.drawOval(getForme(), this.noFill());
        }
    }
}
