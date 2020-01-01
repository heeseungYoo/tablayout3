package com.example.project1_test13;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import java.util.ArrayList;

public class DrawView extends View  {

    private static final float DEFAULT_STROKE_WIDTH = 3;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private static final float TOUCH_TOLERANCE = 4;

    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath = new Path();
    private Bitmap mBitmap;
    private float mX, mY;

    private ArrayList<Path> paths = new ArrayList<>();
    private ArrayList<Path> undonePaths = new ArrayList<>();

    private ArrayList<Point> arrP = new ArrayList<>();
    private ArrayList<Point> undoArrP = new ArrayList<>();
    int color;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        mCanvas = new Canvas();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);

        mBitmap = img;
        mCanvas = canvas;
    }



    @Override
    protected void onDraw(Canvas canvas) {

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }

        for (Point p : arrP){
            canvas.drawPath(p.path, p.paint);
        }
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                undoArrP.clear();
                //undonePaths.clear();
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                    mX = x;
                    mY = y;
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mPath.lineTo(mX, mY);
                mCanvas.drawPath(mPath, mPaint);
                arrP.add(new Point (mPath, mPaint));
               // paths.add(mPath);
                mPath = new Path();
                invalidate();
                break;
        }
        return true;
    }


    public void onClickUndo () {
        if(arrP.size() > 0) {
            undoArrP.add(arrP.remove(arrP.size()-1));
            invalidate();
        } else {}
        /*
        if (paths.size()>0) {
            undonePaths.add(paths.remove(paths.size()-1));
            invalidate();
        }else{
        }*/
    }

    public void onClickRedo (){
        if(undoArrP.size()>0) {
            arrP.add(undoArrP.remove(undoArrP.size()-1));
            invalidate();
        } else {
            Toast.makeText(getContext(), "undoarrp: " + undoArrP.size(), Toast.LENGTH_SHORT).show();
        }
        /*
        if (undonePaths.size()>0){
            paths.add(undonePaths.remove(undonePaths.size()-1));
            invalidate();
        }else {
        }*/
    }

    public void setSize(float width) {
        mPaint.setStrokeWidth(width);
        invalidate();
    }

    //getWidth, getShape 해야함
    public void setColor(int color, float width) {
        init();
 //       mPaint = new Paint();
        mPaint.setColor(color);
   //     mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(width);

/*
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.getStrokeWidth();
  */  }

    public void setWidth(float width) {
        init();
 //       mPaint = new Paint();
 //       mPaint.setColor(color);
        mPaint.setStrokeWidth(width);
  //      mPaint.setStyle(Paint.Style.STROKE);
        /*
        mPaint.getColor();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);*/
    }



    public void reset() {
        arrP.clear();
        undoArrP.clear();
        invalidate();
    }


}
