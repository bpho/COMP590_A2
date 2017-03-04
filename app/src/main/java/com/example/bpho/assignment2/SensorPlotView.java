package com.example.bpho.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by bpho on 2/28/17.
 */

public class SensorPlotView extends View {

    public ArrayList<Float> meanPoints = new ArrayList<>(6);
    public ArrayList<Float> valuePoints = new ArrayList<>(6);
    public ArrayList<Float> stdDevPoints = new ArrayList<>(6);
    public boolean accPlot = false;

    public SensorPlotView(Context context) {
        super(context);
    }

    public SensorPlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SensorPlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SensorPlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    // Removes the first in the list, every value gets shift forward, oldest value becomes at index 1remove
    public void addMeanPoint(float mean) {
        if (meanPoints.size() > 6) {
            meanPoints.remove(0);
        }
        meanPoints.add(mean);
    }

    public void addValuePoint(float value) {
        if (valuePoints.size() > 6) {
            valuePoints.remove(0);
        }
        valuePoints.add(value);
    }

    public void addStdDevPoint(float stdDev) {
        if (stdDevPoints.size() > 6) {
            stdDevPoints.remove(0);
        }
        stdDevPoints.add(stdDev);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewWidth = this.getWidth();
        int viewHeight = this.getHeight();
        super.onDraw(canvas);
        drawCanvasBorder(canvas, viewWidth, viewHeight);
        drawXYLines(canvas, viewWidth, viewHeight);

        if (accPlot == true) {
            drawValuePointsAcc(canvas, viewWidth, viewHeight);
            drawMeanPointsAcc(canvas, viewWidth, viewHeight);
            drawStdDevPointsAcc(canvas, viewWidth, viewHeight);
        } else {
            drawValuePointsLight(canvas, viewWidth, viewHeight);
            drawMeanPointsLight(canvas, viewWidth, viewHeight);
            drawStdDevPointsLight(canvas, viewWidth, viewHeight);
        }
    }

    public void drawMeanPointsLight(Canvas canvas, int width, int height) {
        Paint p = new Paint();
        Paint line = new Paint();
        p.setStrokeWidth(20);
        line.setStrokeWidth(5);
        p.setColor(Color.parseColor("#131784"));
        line.setColor(Color.parseColor("#131784"));

        int incrementX = width/5;
        int counterX = 0;

        for (int i = 0; i < meanPoints.size(); i++) {
            canvas.drawPoint(counterX, height - meanPoints.get(i)*3, p);
            if (i > 0) {
                canvas.drawLine((counterX-incrementX), height-meanPoints.get(i-1)*3, counterX, height-meanPoints.get(i)*3, line);
            }
            counterX += incrementX;
        }
    }

    public void drawValuePointsLight(Canvas canvas, int width, int height) {
        Paint p = new Paint();
        Paint line = new Paint();
        p.setStrokeWidth(20);
        line.setStrokeWidth(5);
        p.setColor(Color.parseColor("#168222"));
        line.setColor(Color.parseColor("#168222"));

        int incrementX = width/5;
        int counterX = 0;
        // Height = (ACTUAL) 900 (150 per interval), (REPRESENTED) Values up to 300 (50 per interval)
        // 3:1 ratio

        for (int i = 0; i < valuePoints.size(); i++) {
            canvas.drawPoint(counterX, height - valuePoints.get(i)*3, p);
//            Log.v("Height: ", String.valueOf(valuePoints.get(i)));
            if (i > 0) {
                canvas.drawLine((counterX-incrementX), height-valuePoints.get(i-1)*3, counterX, height-valuePoints.get(i)*3, line);
            }
            counterX += incrementX;
        }
    }

    public void drawStdDevPointsLight(Canvas canvas, int width, int height) {
        Paint p = new Paint();
        Paint line = new Paint();
        p.setStrokeWidth(20);
        line.setStrokeWidth(5);
        p.setColor(Color.parseColor("#f29e29"));
        line.setColor(Color.parseColor("#f29e29"));

        int incrementX = width/5;
        int counterX = 0;

        for (int i = 0; i < stdDevPoints.size(); i++) {
            canvas.drawPoint(counterX, height - stdDevPoints.get(i)*3, p);
            if (i > 0) {
                canvas.drawLine((counterX-incrementX), height-stdDevPoints.get(i-1)*3, counterX, height-stdDevPoints.get(i)*3, line);
            }
            counterX += incrementX;
        }
    }

    public void drawValuePointsAcc(Canvas canvas, int width, int height) {
        Paint p = new Paint();
        Paint line = new Paint();
        p.setStrokeWidth(20);
        line.setStrokeWidth(5);
        p.setColor(Color.parseColor("#168222"));
        line.setColor(Color.parseColor("#168222"));

        int incrementX = width/5;
        int counterX = 0;

        // Height of plot view is 900. 900/6 = 150 (Each 10 = 150 or (900 - 150 for first one)) Ratio is 1:15
        for (int i = 0; i < valuePoints.size(); i++) {
            canvas.drawPoint(counterX, height - valuePoints.get(i)*15, p);
            if (i > 0) {
//                Log.v("Value Points: ", String.valueOf(valuePoints.get(i)));
                canvas.drawLine((counterX-incrementX), height-valuePoints.get(i-1)*15, counterX, height-valuePoints.get(i)*15, line);
            }
            counterX += incrementX;
        }
    }

    public void drawMeanPointsAcc(Canvas canvas, int width, int height) {
        Paint p = new Paint();
        Paint line = new Paint();
        p.setStrokeWidth(20);
        line.setStrokeWidth(5);
        p.setColor(Color.parseColor("#131784"));
        line.setColor(Color.parseColor("#131784"));

        int incrementX = width/5;
        int counterX = 0;

        for (int i = 0; i < meanPoints.size(); i++) {
            canvas.drawPoint(counterX, height - meanPoints.get(i)*15, p);
            if (i > 0) {
                canvas.drawLine((counterX-incrementX), height-meanPoints.get(i-1)*15, counterX, height-meanPoints.get(i)*15, line);
            }
//            Log.v("CounterX: ", String.valueOf(counterX));    // Check to see if counterX is actually being reset every update
            counterX += incrementX;
        }
    }

    public void drawStdDevPointsAcc(Canvas canvas, int width, int height) {
        Paint p = new Paint();
        Paint line = new Paint();
        p.setStrokeWidth(20);
        line.setStrokeWidth(5);
        p.setColor(Color.parseColor("#f29e29"));
        line.setColor(Color.parseColor("#f29e29"));

        int incrementX = width/5;
        int counterX = 0;

        for (int i = 0; i < stdDevPoints.size(); i++) {
            canvas.drawPoint(counterX, height - stdDevPoints.get(i)*15, p);
            if (i > 0) {
                canvas.drawLine((counterX-incrementX), height-stdDevPoints.get(i-1)*15, counterX, height-stdDevPoints.get(i)*15, line);
            }
            counterX += incrementX;
        }

    }

    public void drawXYLines(Canvas canvas, int width, int height) {
        Paint xPaint = new Paint();
        xPaint.setStrokeWidth(2);
        xPaint.setColor(Color.BLACK);
        int incrementX = width/5;
        int incrementY = height/6;
        int counterX = 0;
        while (counterX < width) {
            canvas.drawLine(counterX, 0, counterX, height, xPaint);
            counterX += incrementX;
        }

        int counterY = 0;
        while (counterY < height) {
            canvas.drawLine(0, counterY, width, counterY, xPaint);
            counterY += incrementY;
        }
    }


    public void drawCanvasBorder(Canvas canvas, int width, int height) {
        Paint axisPaint = new Paint();
        axisPaint.setStrokeWidth(8);
        axisPaint.setColor(Color.BLACK);
        canvas.drawLine(0, 0, 0, height, axisPaint);        // Left border
        canvas.drawLine(0, 0, width, 0, axisPaint);         // Top border
        canvas.drawLine(0, height, width, height, axisPaint);   // Bottom border
        canvas.drawLine(width, 0, width, height, axisPaint);    // Right border
    }
}
