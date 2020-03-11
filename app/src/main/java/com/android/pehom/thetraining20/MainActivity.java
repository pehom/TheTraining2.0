package com.android.pehom.thetraining20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private final String fileName = "trainingState";
    private TextView pullupsCountTextView, pullupsTitleTextView;
    private int pullupsCount, thePullupsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] readFile;
        readFile = readFromFile(this).split(">>");
        if (readFile.length>1) {
            Log.d("mylog", "MainActivity > onCreate > readFile = " + readFile);
            startActivity(new Intent(MainActivity.this, ScheduleActivity.class));

        } else {
                final float[] startx = new float[1];
                final float[] stopx = new float[1];
                pullupsCountTextView = findViewById(R.id.pullupsCountTextView);
                pullupsCountTextView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: // нажатие
                                startx[0] = event.getRawX();
                                break;
                            case MotionEvent.ACTION_MOVE: // движение
                                break;
                            case MotionEvent.ACTION_UP: // отпускание
                                stopx[0] = event.getRawX();
                                Log.d("mylog", "startx = " + startx[0] + "  stopx = " + stopx[0]);
                                if (stopx[0] > startx[0]) {
                                    pullupsCount--;
                                    if (pullupsCount > -1) {
                                        pullupsCountTextView.setText("" + pullupsCount);
                                    }
                                } else if (stopx[0] < startx[0]) {
                                    pullupsCount++;
                                    if (pullupsCount > -1) {
                                        pullupsCountTextView.setText("" + pullupsCount);
                                    }
                                }
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                Log.d("mylog", "action canceled");
                                break;
                        }

                        return true;
                    }
                });

            }
    }


    public void writeToFile(Context context, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void createSchedule(View view) {
        thePullupsCount = pullupsCount;
        Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
        intent.putExtra("thePullupsCount", thePullupsCount);
        startActivity(intent);
    }
}
