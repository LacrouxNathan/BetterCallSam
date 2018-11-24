package fr.kounecorp.bettercallsam.game2_noname;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CanvasView extends View {

    private Canvas mCanvas;
    private List<Forme> formes;
    private Random random;
    private Bitmap mBitmap;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        this.formes = new ArrayList<>();
        this.random = new Random();
    }

    public void initializeForme(int w, int h) {
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

    public void initializeForme() {
        int nbFormes = 0;
        while (nbFormes < 5) {
            int x = this.random.nextInt(700);
            int y = this.random.nextInt(1200);
            int L = this.random.nextInt(200)+100;
            int l = this.random.nextInt(200)+100;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Forme forme : this.formes) {
            forme.display(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (Forme forme : this.formes) {
                forme.testClick(x,y);
                invalidate();
            }
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.performClick();
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public void clearCanvas() {
        this.formes.clear();
        invalidate();
    }
}
