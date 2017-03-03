package com.example.bpho.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bpho on 2/28/17.
 */

public class LightDataActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        TextView title = (TextView)findViewById(R.id.title);
        title.setText("Light Sensor Graph");

        Intent intent = getIntent();
    }

    // TODO: Dynamically set axis labels and titles and values

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
