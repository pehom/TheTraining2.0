package com.android.pehom.thetraining20.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.models.Schedule;
import com.android.pehom.thetraining20.models.TrainingDay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ScheduleActivity extends AppCompatActivity {
    private final String trainingState = "trainingState";
    private final String trainingProgress = "trainingProgress";
    private TrainingDay[] days;
    private boolean resetFlag;
    private Schedule schedule;

    private int  thePullupsCount;
    private int daysCompleted, setsDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        resetFlag = false;
        String readFile;
        readFile = readFromFile(this, trainingState);

        if (!readFile.equals("")) {
            Log.d("mylog", "scheduleActivity.onCreate readFile = " + readFile);
            schedule = Schedule.fromString(readFile);
          //  thePullupsCount = Integer.parseInt(readFile[0].trim());
            daysCompleted = schedule.getDaysCompleted();
           // setsDone = Integer.parseInt(readFile[2].trim());
            createTrainingTable(daysCompleted);
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
          //  scheduleInfoTextView.setText(readFile);
        } else {
            /*Intent intent = getIntent();
            thePullupsCount = intent.getIntExtra("thePullupsCount", 5);
            createTrainingTable(0);
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
            scheduleInfoTextView.setText(readFile);*/
        }

        TextView trainingProgressTextView = findViewById(R.id.trainingProgressTextView);
        trainingProgressTextView.setMovementMethod(new ScrollingMovementMethod());

    }
    public void createTrainingTable(int daysQompleted) {
        TextView[] daysTextViews = new TextView[] {findViewById(R.id.day1TextView),findViewById(R.id.day2TextView),
                findViewById(R.id.day3TextView),findViewById(R.id.day4TextView),
                findViewById(R.id.day5TextView),findViewById(R.id.day6TextView),
                findViewById(R.id.day7TextView),findViewById(R.id.day8TextView),
                findViewById(R.id.day9TextView),findViewById(R.id.day10TextView),
                findViewById(R.id.day11TextView),findViewById(R.id.day12TextView),
                findViewById(R.id.day13TextView),findViewById(R.id.day14TextView),
                findViewById(R.id.day15TextView),findViewById(R.id.day16TextView),
                findViewById(R.id.day17TextView),findViewById(R.id.day18TextView),
                findViewById(R.id.day19TextView),findViewById(R.id.day20TextView),
                findViewById(R.id.day21TextView),findViewById(R.id.day22TextView),
                findViewById(R.id.day23TextView),findViewById(R.id.day24TextView),
                findViewById(R.id.day25TextView),findViewById(R.id.day26TextView),
                findViewById(R.id.day27TextView),findViewById(R.id.day28TextView)};
        if (daysQompleted > 0) {
            for (int i=0; i<daysQompleted; i++){
                daysTextViews[i].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            }
        }

        if (daysQompleted < 28 ){
            daysTextViews[daysCompleted].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mylog", "daysCompleted ="+ daysCompleted );
                    writeToFile(getApplicationContext(), trainingState, schedule.toString());
                    Intent intent = new Intent(ScheduleActivity.this, TrainingDayActivity.class);
                    /*intent.putExtra("dayNumber", days[daysCompleted].getThisDayNumber());
                    intent.putExtra("thePullupsCount", thePullupsCount);
                    intent.putExtra("setsDone", setsDone);*/
                    startActivityForResult(intent, 123);
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == RESULT_OK){
                setsDone = data.getIntExtra("setsDone", 0);
                TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
                scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" +daysCompleted + "\n" +
                        "setsDone = " + setsDone);
            }
        }
    }

    public void writeToFile(Context context,String fileName,  String data) {
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

    @Override
    protected void onStop() {
        super.onStop();
        if (!resetFlag) {
            writeToFile(this, trainingState, "" + thePullupsCount + ">>" + daysCompleted + ">>" + setsDone);
            Log.d("mylog", "onStop SchedAc writeToFile() = " + thePullupsCount + ">>" + daysCompleted + ">>" + setsDone);
            Log.d("mylog", "onStop SchedAc readFromFile() = " + readFromFile(this, trainingState));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFlag = false;
        String readFile;
        readFile = readFromFile(this, trainingState);

        if (!readFile.equals("")) {
            Log.d("mylog", "readFile = " + readFile);
            Schedule schedule = Schedule.fromString(readFile);

            createTrainingTable(schedule.getDaysCompleted());
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
            /*scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" + daysCompleted + "\n" +
                    "setsDone = " + setsDone);*/
        } else {
            /*Intent intent = getIntent();
            thePullupsCount = intent.getIntExtra("thePullupsCount", 5);
            createTrainingTable(0);
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
            scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" +0 + "\n" +
                    "setsDone = " + 0);*/
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reset_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resetTraining:
                writeToFile(this, trainingState, "0>>0>>0");
                resetFlag = true;
                startActivity(new Intent(ScheduleActivity.this, MainActivity.class));
                finishAffinity();
                break;
            case R.id.trainingProgress:
                TextView trainingProgressTextView = findViewById(R.id.trainingProgressTextView);
                TextView trainingProgressTitleTextView = findViewById(R.id.trainingProgressTitleTextView);
                trainingProgressTitleTextView.setVisibility(View.VISIBLE);

        }
        return super.onOptionsItemSelected(item);
    }
}
