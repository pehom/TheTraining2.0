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
import android.widget.Button;
import android.widget.TextView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.models.Converter;
import com.android.pehom.thetraining20.models.Exercise;
import com.android.pehom.thetraining20.models.Schedule;
import com.android.pehom.thetraining20.models.TrainingDay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    private final  String APP_STATE = "APP_STATE";
    private final String TRAINING_STATE = "trainingState";
    private final String TRAINING_PROGRESS = "trainingProgress";
    private  final String TRAINING_PROGRESS_DIVIDER = "!!!";
    private boolean resetFlag;
    private Schedule schedule;
    private int daysCompleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        resetFlag = false;
        String readFile;
        readFile = readFromFile(this, TRAINING_STATE);
        if (!readFile.equals("")) {
            Log.d("mylog", "scheduleActivity.onCreate readFile = " + readFile);
            schedule = Schedule.fromString(readFile);
            daysCompleted = schedule.getDaysCompleted();
            createTrainingTable(daysCompleted);
            TextView infoTextView = findViewById(R.id.infoTextView);
            infoTextView.setMovementMethod(new ScrollingMovementMethod());
            if (schedule.getExercises()!= null) {
                for (int i = 0; i<schedule.getExercises().size(); i++){
                    infoTextView.append(schedule.getExercises().get(i).forPrint() + "\n");
                }
            }
        }
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
                    writeToFile(getApplicationContext(), TRAINING_STATE, schedule.toString());
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
                TextView infoTextView = findViewById(R.id.infoTextView);
                infoTextView.setText("set has been done");
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
            writeToFile(this, TRAINING_STATE, schedule.toString());
            Log.d("mylog", "onStop SchedAc writeToFile() = " + schedule.toString());
            Log.d("mylog", "onStop SchedAc readFromFile() = " + readFromFile(this, TRAINING_STATE));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFlag = false;
        String readFile;
        readFile = readFromFile(this, TRAINING_STATE);
        Log.d("mylog", "scheduleActivity. onResume readFile = " + readFile);

        if (!readFile.equals("")) {
            Schedule schedule = Schedule.fromString(readFile);

            createTrainingTable(schedule.getDaysCompleted());
            TextView infoTextView = findViewById(R.id.infoTextView);
            /*scheduleInfoTextView.setText("thePullUpsCount = " + thePullupsCount + "\n" + "daysCompleted =" + daysCompleted + "\n" +
                    "setsDone = " + setsDone);*/
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.schedule_activity_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resetTraining:
                writeToFile(this, TRAINING_STATE, schedule.toString());
                writeToFile(this, APP_STATE, "schedule reset");
                resetFlag = true;
                startActivity(new Intent(ScheduleActivity.this, MainActivity.class));
                finishAffinity();
                break;
            case R.id.trainingProgress:
                TextView infoTextView = findViewById(R.id.infoTextView);
                infoTextView.setMovementMethod(new ScrollingMovementMethod());
                infoTextView.setText("");
                TextView infoTitleTextView = findViewById(R.id.infoTitleTextView);
                infoTitleTextView.setText(R.string.info_textview_training_progress);
                Log.d("trainingProgress", "readData = " + readFromFile(this, TRAINING_PROGRESS));
                String[] splittedData = readFromFile(this, TRAINING_PROGRESS).split(TRAINING_PROGRESS_DIVIDER);
                if (!splittedData[0].equals("")) {
                    for (int i = 0; i< splittedData.length; i++) {
                        String[] splittedSchedule = splittedData[i].split("schDi");
                        infoTextView.append(splittedSchedule[0] + "\n");
                        List<Exercise> exercises = Converter.exercisesFromString(splittedSchedule[1]);
                        for (int j = 0; j<exercises.size(); j++) {
                            infoTextView.append(exercises.get(j).forPrint() + "\n" );
                        }
                        infoTextView.append("\n");
                    }
                } else {
                    infoTextView.setText(R.string.training_progress_info_cleared);
                }

                Button closeInfoButton = findViewById(R.id.closeTrainingProcessButton);
                closeInfoButton.setVisibility(View.VISIBLE);
                findViewById(R.id.clearTrainingProgress).setVisibility(View.VISIBLE);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void closeInfo(View view) {
        TextView infoTextView = findViewById(R.id.infoTextView);
        TextView infoTitleTextView = findViewById(R.id.infoTitleTextView);
        infoTitleTextView.setText(R.string.info_title_schedule);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        infoTextView.setText("");
        for (int i = 0; i<schedule.getExercises().size(); i++){
            infoTextView.append(schedule.getExercises().get(i).forPrint() + "\n");
        }
       // isInfoSchedule = true;
        Button closeInfoButton = findViewById(R.id.closeTrainingProcessButton);
        closeInfoButton.setVisibility(View.INVISIBLE);
        findViewById(R.id.clearTrainingProgress).setVisibility(View.INVISIBLE);
    }

    public void clearTrainingProgress(View view) {
        writeToFile(this, TRAINING_PROGRESS, "");
        Log.d("trainingProgress", "trainingProgress cleared");
        Log.d("trainingProgress", "after clearing training progress =" + readFromFile(this, TRAINING_PROGRESS));
        TextView infoTextView = findViewById(R.id.infoTextView);
        infoTextView.setText(readFromFile(this, TRAINING_PROGRESS));

    }
}
