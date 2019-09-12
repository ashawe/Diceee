package com.ashstudios.diceee;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.seismic.ShakeDetector;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener {

    private static final String TAG = "MainActivity";
    private Vibrator v;
    private Spinner spinner;
    private ImageView imageView1,imageView2,imageView3;
    private String[] selector = { "one", "two", "three" };
    private int[] dices = {R.drawable.ic_dice,R.drawable.ic_dice2,R.drawable.ic_dice3,R.drawable.ic_dice4, R.drawable.ic_dice5, R.drawable.ic_dice6};
    private int rollNum = 1;
    ShakeDetector sd;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.selector);
        imageView1 = findViewById(R.id.dice1);
        imageView2 = findViewById(R.id.dice2);
        imageView3 = findViewById(R.id.dice3);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, selector);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.GONE);
                    imageView3.setVisibility(View.GONE);
                    rollNum = 1;
                }
                if(i==1)
                {
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.GONE);
                    rollNum = 2;
                }
                if(i==2)
                {
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.VISIBLE);
                    rollNum = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(this);
    }

    private void vibrate()
    {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(300);
        }
    }

    @Override
    public void hearShake() {
        vibrate();
        Random random = new Random();
        int dice1 = random.nextInt(6);
        int dice2 = random.nextInt(6);
        int dice3 = random.nextInt(6);

        switch (rollNum)
        {
            case 1:
                imageView1.setImageResource(dices[dice1]);
                break;
            case 2:
                imageView1.setImageResource(dices[dice1]);
                imageView2.setImageResource(dices[dice2]);
                break;
            case 3:
                imageView1.setImageResource(dices[dice1]);
                imageView2.setImageResource(dices[dice2]);
                imageView3.setImageResource(dices[dice3]);
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sd.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sd.start(sensorManager);
        sd.setSensitivity(12);
    }
}
