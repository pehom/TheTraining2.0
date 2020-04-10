package com.android.pehom.thetraining20.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.adapters.TrainingDayAdapter;
import com.android.pehom.thetraining20.models.Exercise;
import com.android.pehom.thetraining20.models.Schedule;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;

public class TrainingDayActivity extends AppCompatActivity {
    private final  String APP_STATE = "APP_STATE";
    private final String TRAINING_STATE = "trainingState";
    private int dayNumber;
    private  boolean resetFlag;
    private Schedule schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_day);
        resetFlag = false;
        String readFile;
        readFile = readFromFile(this, TRAINING_STATE);
        if (!readFile.isEmpty()) {
            schedule = Schedule.fromString(readFile);
            dayNumber = schedule.getDaysCompleted()+1;
            TextView dayTitleTextView = findViewById(R.id.dayTitleTextView);
            dayTitleTextView.append(" "+dayNumber);
            RecyclerView recyclerView = findViewById(R.id.trainingDayRecyclerView);
            TrainingDayAdapter adapter = new TrainingDayAdapter(schedule);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
            recyclerView.addItemDecoration(divider);
        }
    }

    public void writeToFile(Context context, String filename, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
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
            Log.d("mylog", "onStop TrainAc writeToFile() = " + schedule.toString());
        }
    }

    @Override
    public void onBackPressed() {
        writeToFile(this, TRAINING_STATE, schedule.toString());
        Log.d("mylog", "onBackPressed writeToFile() = " + schedule.toString());
        startActivity(new Intent(TrainingDayActivity.this, ScheduleActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.training_day_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resetTraining:
                writeToFile(this, TRAINING_STATE, schedule.toString());
                writeToFile(this, APP_STATE, "schedule reset");
                resetFlag = true;
                startActivity(new Intent(TrainingDayActivity.this, MainActivity.class));
                finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}
