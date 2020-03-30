package com.android.pehom.thetraining20;

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

    private int  thePullupsCount;
    private int daysCompleted, setsDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        resetFlag = false;
        String[] readFile;
        readFile = readFromFile(this, trainingState).split(">>");

        if (readFile.length>1) {
            Log.d("mylog", "readFile = " + readFile);

            thePullupsCount = Integer.parseInt(readFile[0].trim());
            daysCompleted = Integer.parseInt(readFile[1].trim());
            setsDone = Integer.parseInt(readFile[2].trim());
            createTrainingTable(daysCompleted);
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
            scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" + daysCompleted + "\n" +
                    "setsDone = " + setsDone);
        } else {
            Intent intent = getIntent();
            thePullupsCount = intent.getIntExtra("thePullupsCount", 5);
            createTrainingTable(0);
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
            scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" +0 + "\n" +
                    "setsDone = " + 0);
        }

        TextView trainingProgressTextView = findViewById(R.id.trainingProgressTextView);
        trainingProgressTextView.setMovementMethod(new ScrollingMovementMethod());

    }
    public void createTrainingTable(int daysQompleted) {
//        days = new TrainingDay[]{new TrainingDay((TextView) findViewById(R.id.day1TextView), false, 1),
//                new TrainingDay((TextView) findViewById(R.id.day2TextView), false, 2),
//                new TrainingDay((TextView) findViewById(R.id.day3TextView), false, 3),
//                new TrainingDay((TextView) findViewById(R.id.day4TextView), false, 4),
//                new TrainingDay((TextView) findViewById(R.id.day5TextView), false, 5),
//                new TrainingDay((TextView) findViewById(R.id.day6TextView), false, 6),
//                new TrainingDay((TextView) findViewById(R.id.day7TextView), false, 7),
//                new TrainingDay((TextView) findViewById(R.id.day8TextView), false, 8),
//                new TrainingDay((TextView) findViewById(R.id.day9TextView), false, 9),
//                new TrainingDay((TextView) findViewById(R.id.day10TextView), false, 10),
//                new TrainingDay((TextView) findViewById(R.id.day11TextView), false, 11),
//                new TrainingDay((TextView) findViewById(R.id.day12TextView), false, 12),
//                new TrainingDay((TextView) findViewById(R.id.day13TextView), false, 13),
//                new TrainingDay((TextView) findViewById(R.id.day14TextView), false, 14),
//                new TrainingDay((TextView) findViewById(R.id.day15TextView), false, 15),
//                new TrainingDay((TextView) findViewById(R.id.day16TextView), false, 16),
//                new TrainingDay((TextView) findViewById(R.id.day17TextView), false, 17),
//                new TrainingDay((TextView) findViewById(R.id.day18TextView), false, 18),
//                new TrainingDay((TextView) findViewById(R.id.day19TextView), false, 19),
//                new TrainingDay((TextView) findViewById(R.id.day20TextView), false, 20),
//                new TrainingDay((TextView) findViewById(R.id.day21TextView), false, 21),
//                new TrainingDay((TextView) findViewById(R.id.day22TextView), false, 22),
//                new TrainingDay((TextView) findViewById(R.id.day23TextView), false, 23),
//                new TrainingDay((TextView) findViewById(R.id.day24TextView), false, 24),
//                new TrainingDay((TextView) findViewById(R.id.day25TextView), false, 25),
//                new TrainingDay((TextView) findViewById(R.id.day26TextView), false, 26),
//                new TrainingDay((TextView) findViewById(R.id.day27TextView), false, 27),
//                new TrainingDay((TextView) findViewById(R.id.day28TextView), false, 28),
//        };
//        daysCompleted =  daysQompleted;
//        for (int i=0;i<28; i++){
//            if (i<daysCompleted && daysCompleted>0)  days[i].setDone(true);
//            //  Log.d("mylog", "day" + i + " isDone = " + days[i].isDone());
//        }
//
//
//        for (int j = 0; j<days.length;j++){
//            days[j].getThisDayTextView().setText(""+(j+1));
//            if (days[j].isDone()){
//                days[j].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//            } else {
//                days[j].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
//            }
//        }
//        if (daysCompleted < 28 ){
//            days[daysCompleted].getThisDayTextView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("mylog", "daysCompleted ="+ daysCompleted );
//                    writeToFile(getApplicationContext(), trainingState,"" + thePullupsCount + ">>"  + daysCompleted+ ">>" +  setsDone);
//                    Intent intent = new Intent(ScheduleActivity.this, TrainingDayActivity.class);
//                    /*intent.putExtra("dayNumber", days[daysCompleted].getThisDayNumber());
//                    intent.putExtra("thePullupsCount", thePullupsCount);
//                    intent.putExtra("setsDone", setsDone);*/
//                    startActivityForResult(intent, 123);
//                }
//            });
//        }

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
        String[] readFile;
        readFile = readFromFile(this, trainingState).split(">>");

        if (readFile.length>1) {
            Log.d("mylog", "readFile = " + readFile);

            thePullupsCount = Integer.parseInt(readFile[0].trim());
            daysCompleted = Integer.parseInt(readFile[1].trim());
            setsDone = Integer.parseInt(readFile[2].trim());
            createTrainingTable(daysCompleted);
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
            scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" + daysCompleted + "\n" +
                    "setsDone = " + setsDone);
        } else {
            Intent intent = getIntent();
            thePullupsCount = intent.getIntExtra("thePullupsCount", 5);
            createTrainingTable(0);
            TextView scheduleInfoTextView = findViewById(R.id.scheduleInfoTextView);
            scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" +0 + "\n" +
                    "setsDone = " + 0);
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
