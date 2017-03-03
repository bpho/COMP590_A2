package com.example.bpho.assignment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bpho on 2/27/17.
 */

public class AccDataActivity extends AppCompatActivity implements SensorEventListener{

    private Sensor s;
    private SensorManager sm;
    private ArrayList<Float> sensorValues = new ArrayList<>();
    private float totalValue = 0;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent intent = getIntent();

        TextView title = (TextView)findViewById(R.id.title);
        title.setText("Accelerometer Sensor Graph");
        TextView legend1 = (TextView)findViewById(R.id.legend1);
        TextView legend2 = (TextView)findViewById(R.id.legend2);
        TextView legend3 = (TextView)findViewById(R.id.legend3);
        legend1.setTextColor(Color.parseColor("#168222"));
        legend2.setTextColor(Color.parseColor("#131784"));
        legend3.setTextColor(Color.parseColor("#f29e29"));


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register event listener (every .1 seconds onSensorChanged called)
        sm.registerListener(this, s, 200000);   // 200k Microseconds = 200ms = .2seconds
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Add point to plotView
        SensorPlotView plotView = (SensorPlotView)findViewById(R.id.sensorPlotView);

        float actValue = (float)Math.sqrt((Math.pow(event.values[0], 2)) + ((Math.pow(event.values[1], 2))) + ((Math.pow(event.values[2], 2))));
        totalValue += actValue;
        sensorValues.add(actValue);
        counter++;
        float avgValue = totalValue/counter;
        float stdDev = calculateStdDev(avgValue);
//        Log.v("Actual Value: ", String.valueOf(actValue));
//        Log.v("Mean Value: ", String.valueOf(avgValue));
//        Log.v("Std Dev: ", String.valueOf(stdDev));
        plotView.addMeanPoint(avgValue);
        plotView.addStdDevPoint(stdDev);
        plotView.addValuePoint(actValue);
        plotView.invalidate();
    }

    public float calculateStdDev(float mean) {
        double summation = 0;
        for (int i = 0; i < counter; i++) {
            summation += Math.pow((sensorValues.get(i) - mean), 2);
        }
        return (float)Math.sqrt(summation / counter);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
