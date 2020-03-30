package com.android.pehom.thetraining20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.pehom.thetraining20.adapters.CountAdapter;
import com.android.pehom.thetraining20.adapters.CreateScheduleAdapter;
import com.android.pehom.thetraining20.models.Exercise;
import com.android.pehom.thetraining20.models.Schedule;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String trainingState = "trainingState";
    private final String trainingProgress = "trainingProgress";
    private TextView pullupsCountTextView, pullupsTitleTextView;
    private int pullupsCount, thePullupsCount;
    private String daysCompleted, setsDone;
    private RecyclerView createSheduleRecyclerView;
    private List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] readFile;
        readFile = readFromFile(this,trainingState).split(">>");
        Log.d("mylog", "onCreate readFromFile = " + readFromFile(this,trainingState));
        if (readFile.length>2) {
            daysCompleted = readFile[1].trim();
            setsDone = readFile[2].trim();
        } else {
            daysCompleted="0";
            setsDone= "0";
        }


        if (readFile[0].trim().equals("0") && readFile[1].trim().equals("0") && readFile[2].trim().equals("0")){

            createSheduleRecyclerView = findViewById(R.id.createScheduleRecyclerView);
            exercises = new ArrayList<>();
            exercises.add(new Exercise(getResources().getString(R.string.pullup), 10));
            exercises.add(new Exercise(getResources().getString(R.string.jumps), 25));
            exercises.add(new Exercise(getResources().getString(R.string.pushup), 20));

            RecyclerView.LayoutManager createScheduleLayoutManager = new LinearLayoutManager(this);
            CreateScheduleAdapter createScheduleAdapter = new CreateScheduleAdapter(exercises, createCountRecyclerView());

            createSheduleRecyclerView.setLayoutManager(createScheduleLayoutManager);
            createSheduleRecyclerView.setAdapter(createScheduleAdapter);



            /*final float[] startx = new float[1];
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
            });*/
        } else {
            Log.d("mylog", "MainActivity > onCreate > readFile = " + readFile);
            startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
    }

    private RecyclerView createCountRecyclerView() {
        RecyclerView countRecyclerView = new RecyclerView(this);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ArrayList<Integer> countArrayList = new ArrayList<>();
        for (int i= 0; i<500; i++){
            countArrayList.add(i);
        }
        CountAdapter countAdapter = new CountAdapter(countArrayList);
        countRecyclerView.setLayoutManager(layoutManager);
        countRecyclerView.setAdapter(countAdapter);
        return countRecyclerView;
    }


    public void writeToFile(Context context, String fileName,  String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile(Context context, String fileName) {

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
     //   thePullupsCount = pullupsCountRecyclerView.getChildViewHolder(this).
        writeToFile(this, trainingState, ""+thePullupsCount+">>"+daysCompleted + ">>" + setsDone);
        String dayLongName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String prevTraining = readFromFile(this, trainingProgress);
        writeToFile(this, trainingProgress, prevTraining + "\n" + dayLongName + "   pull-ups count = " + thePullupsCount );
        Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
        intent.putExtra("thePullupsCount", thePullupsCount);
        startActivity(intent);
    }
}
