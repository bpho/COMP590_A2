package com.example.bpho.assignment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private Sensor s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAccelerometer();
        checkLight();
    }

    public void checkAccelerometer() {
        TextView status = (TextView)findViewById(R.id.accStatus);
        TextView info = (TextView)findViewById(R.id.accInfo);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            status.setTextColor(Color.GREEN);
            status.setText("Status: Accelerometer is present.");
            info.append(" Resolution: " +String.valueOf(s.getResolution()));
            info.append("\n          Max Range: " +String.valueOf(s.getMaximumRange()));
            info.append("\n          Min Delay: " +String.valueOf(s.getMinDelay()));
        } else {
            status.setTextColor(Color.RED);
            status.setText("Status: Accelerometer is not present.");
            info.setText("Info: NULL, sensor not present.");
        }

    }

    public void checkLight() {
        TextView status = (TextView) findViewById(R.id.lightStatus);
        TextView info = (TextView) findViewById(R.id.lightInfo);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sm.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            status.setTextColor(Color.GREEN);
            status.setText("Status: Light Sensor is present.");
            info.append(" Resolution: " + String.valueOf(s.getResolution()));
            info.append("\n          Max Range: " + String.valueOf(s.getMaximumRange()));
            info.append("\n          Min Delay: " + String.valueOf(s.getMinDelay()));
        } else {
            status.setTextColor(Color.RED);
            status.setText("Status: Light Sensor is not present.");
        }

    }

    public void plotAccData(View view) {
        Intent intent = new Intent(this, AccDataActivity.class);
        startActivity(intent);
    }

    public void plotLightData(View view) {
        Intent intent = new Intent(this, LightDataActivity.class);
        startActivity(intent);
    }
}
