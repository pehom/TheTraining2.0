package com.android.pehom.thetraining20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class TrainingDayActivity extends AppCompatActivity {
    private final String fileName = "trainingState";

    private int dayNumber, thePullupsCount, setsDone, daysCompleted;
    private TrainingDay[] sets;
    private View.OnClickListener onSetClickListener;
    private  boolean resetFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_day);
        resetFlag = false;
        String[] readFile;
        readFile = readFromFile(this, fileName).split(">>");
        if (readFile.length > 2) {
            thePullupsCount = Integer.parseInt(readFile[0].trim());
            daysCompleted = Integer.parseInt(readFile[1].trim());
            setsDone = Integer.parseInt(readFile[2].trim());
            dayNumber = daysCompleted+1;
        }

        /*final Intent intent = getIntent();
        dayNumber = intent.getIntExtra("dayNumber", 0);
        daysCompleted = dayNumber;
        thePullupsCount = intent.getIntExtra("thePullupsCount", 10);
        setsDone = intent.getIntExtra("setsDone", 0);*/

//        TextView exerciseTextView = findViewById(R.id.exerciseTextView);
//        exerciseTextView.setText("Pull-ups   Day " + (dayNumber));
//        TextView trainingDayInfoTextView = findViewById(R.id.trainingDayInfoTextView);
//        trainingDayInfoTextView.setText("thePullUpsCount = "+ thePullupsCount +"\n"+ "dayNumber =" + dayNumber +"\n" +
//                "setsDone = " + setsDone);
//        sets = new TrainingDay[] {new TrainingDay((TextView) findViewById(R.id.row1TextView), false, 1),
//                new TrainingDay((TextView) findViewById(R.id.row2TextView), false, 2),
//                new TrainingDay((TextView) findViewById(R.id.row3TextView), false, 3),
//                new TrainingDay((TextView) findViewById(R.id.row4TextView), false, 4),
//                new TrainingDay((TextView) findViewById(R.id.row5TextView), false, 5)};
//        for (int i=0; i<5;i++) {
//            sets[i].getThisDayTextView().setText(""+thePullupsCount);
//            if (i<setsDone && setsDone > 0) {
//                sets[i].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//            } else {
//                sets[i].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
//            }
//            //  final int j = i;
//            onSetClickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    sets[setsDone].setDone(true);
//                    sets[setsDone].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//                    sets[setsDone].getThisDayTextView().setClickable(false);
////
//                    if (setsDone < 4) {
//                        setsDone++;
//                        TextView trainingDayInfoTextView = findViewById(R.id.trainingDayInfoTextView);
//                        trainingDayInfoTextView.setText("thePullUpsCount = "+ thePullupsCount +"\n"+ "dayNumber =" + dayNumber +"\n" +
//                                "setsDone = " + setsDone);
//                        sets[setsDone].getThisDayTextView().setOnClickListener(onSetClickListener);
//                    } else {
//                        setsDone = 0;
//                        Intent intent1 = new Intent(TrainingDayActivity.this, ScheduleActivity.class);
//                        intent1.putExtra("daysCompleted", dayNumber);
//                        intent1.putExtra("pullupsCount", thePullupsCount);
//                        intent1.putExtra("setsDone", setsDone);
//                        writeToFile(getApplicationContext(), "" + thePullupsCount + ">>"  + (daysCompleted+1)+ ">>" +  setsDone);
//
//                        startActivity(intent1);
//
//                    }
//                }
//            };
//            if (setsDone<5)
//                sets[setsDone].getThisDayTextView().setOnClickListener(onSetClickListener);
//        }

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
            Log.d("mylog", "data for  writeToFile() = " + thePullupsCount + ">>" + daysCompleted + ">>" + setsDone);

            writeToFile(this, "" + thePullupsCount + ">>" + daysCompleted + ">>" + setsDone);
            Log.d("mylog", "onStop TrainAc writeToFile() = " + thePullupsCount + ">>" + daysCompleted + ">>" + setsDone);
            Log.d("mylog", "onStop TrainAc readFromFile() = " + readFromFile(this, fileName));
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        writeToFile(this, "" + thePullupsCount + ">>"  + daysCompleted+ ">>" +  setsDone);
        Log.d("mylog", "onBackPressed writeToFile() = " + thePullupsCount + ">>"  + daysCompleted+ ">>" +  setsDone);

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
                writeToFile(this, "0>>0>>0");
                resetFlag = true;
                startActivity(new Intent(TrainingDayActivity.this, MainActivity.class));
                finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}
