package com.example.bpho.assignment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bpho on 2/28/17.
 */

public class LightDataActivity extends AppCompatActivity implements SensorEventListener{

    private Sensor s;
    private SensorManager sm;
    private ArrayList<Float> sensorValues = new ArrayList<>();
    private float totalValue = 0;
    private int counter = 0;
    private int sensorCalls = 0;
    private int[] xAxis = new int[] {0, 2, 4, 6, 8, 10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent intent = getIntent();

        TextView firstY = (TextView)findViewById(R.id.firstY);
        TextView secondY = (TextView)findViewById(R.id.secondY);
        TextView thirdY = (TextView)findViewById(R.id.thirdY);
        TextView fourthY = (TextView)findViewById(R.id.fourthY);
        TextView fifthY = (TextView)findViewById(R.id.fifthY);
        TextView sixthY = (TextView)findViewById(R.id.sixthY);
        TextView seventhY = (TextView)findViewById(R.id.seventhY);
        seventhY.setTextSize(10);
        sixthY.setText("50");
        sixthY.setTextSize(10);
        fifthY.setText("100");
        fifthY.setTextSize(10);
        fourthY.setText("150");
        fourthY.setTextSize(10);
        thirdY.setText("200");
        thirdY.setTextSize(10);
        secondY.setText("250");
        secondY.setTextSize(10);
        firstY.setText("300");
        firstY.setTextSize(10);

        VerticalTextView yAxis = (VerticalTextView)findViewById((R.id.yAxisLabel));
        yAxis.setText("Data Ix");
        TextView title = (TextView)findViewById(R.id.title);
        title.setText("Light Sensor Graph");
        TextView legend1 = (TextView)findViewById(R.id.legend1);
        TextView legend2 = (TextView)findViewById(R.id.legend2);
        TextView legend3 = (TextView)findViewById(R.id.legend3);
        legend1.setTextColor(Color.parseColor("#168222"));
        legend2.setTextColor(Color.parseColor("#131784"));
        legend3.setTextColor(Color.parseColor("#f29e29"));

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v("Light (Ix): ", String.valueOf(event.values[0]));

        SensorPlotView plotView = (SensorPlotView)findViewById(R.id.sensorPlotView);
        plotView.accPlot = false;
        float actValue = event.values[0];
        sensorValues.add(actValue);
        totalValue += actValue;
        counter++;
        sensorCalls++;
        float avgValue = totalValue/counter;
        float stdDev = calculateStdDev(avgValue);
        Log.v("Mean: ", String.valueOf(avgValue));
        Log.v("Std Dev: ", String.valueOf(stdDev));
        if (sensorCalls > 6) {
            changeXAxis();
        }
        checkAnimation(avgValue);
        plotView.addMeanPoint(avgValue);
        plotView.addStdDevPoint(stdDev);
        plotView.addValuePoint(actValue);
        plotView.invalidate();
    }

    public void changeXAxis() {
        TextView firstX = (TextView)findViewById(R.id.firstX);
        TextView secondX = (TextView)findViewById(R.id.secondX);
        TextView thirdX = (TextView)findViewById(R.id.thirdX);
        TextView fourthX = (TextView)findViewById(R.id.fourthX);
        TextView fifthX = (TextView)findViewById(R.id.fifthX);
        TextView sixthX = (TextView)findViewById(R.id.sixthX);
        for (int i = 0; i < xAxis.length; i++) {
            xAxis[i]+=2;
        }

        firstX.setText(String.valueOf(xAxis[0]));
        secondX.setText(String.valueOf(xAxis[1]));
        thirdX.setText(String.valueOf(xAxis[2]));
        fourthX.setText(String.valueOf(xAxis[3]));
        fifthX.setText(String.valueOf(xAxis[4]));
        sixthX.setText(String.valueOf(xAxis[5]));
    }

    public float calculateStdDev(float mean) {
        double summation = 0;
        for (int i = 0; i < counter; i++) {
            summation += Math.pow((sensorValues.get(i) - mean), 2);
        }
        return (float)Math.sqrt(summation / counter);
    }

    public void checkAnimation(float average) {
        ImageView img = (ImageView)findViewById(R.id.animation);
        if (average > 50 && average < 155) {
//            img.setBackgroundResource(R.drawable.light);
            img.setBackgroundResource(R.drawable.lightlistmed);
            ((AnimationDrawable)img.getBackground()).start();
        } else if (average >= 155) {
//            img.setBackgroundResource(R.drawable.brightlight);
            img.setBackgroundResource(R.drawable.lightlisthigh);
            ((AnimationDrawable)img.getBackground()).start();
        } else {
//            img.setBackgroundResource(R.drawable.darklight);
            img.setBackgroundResource(R.drawable.lightlistlow);
            ((AnimationDrawable)img.getBackground()).start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not Used
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sm.registerListener(this, s, 200000);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sm.unregisterListener(this);
    }
}
