package com.android.pehom.thetraining20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class TrainingDayActivity extends AppCompatActivity {
    private final String fileName = "trainingState";

    private int dayNumber, thePullupsCount, setsDone, daysCompleted;
    private TrainingDay[] sets;
    private View.OnClickListener onSetClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_day);
        final Intent intent = getIntent();
        dayNumber = intent.getIntExtra("dayNumber", 0);
        thePullupsCount = intent.getIntExtra("thePullupsCount", 10);
        setsDone = intent.getIntExtra("setsDone", 0);
        TextView exerciseTextView = findViewById(R.id.exerciseTextView);
        exerciseTextView.setText("Pull-ups   Day " + (dayNumber));
        TextView trainingDayInfoTextView = findViewById(R.id.trainingDayInfoTextView);
        trainingDayInfoTextView.setText("thePullUpsCount = "+ thePullupsCount +"\n"+ "dayNumber =" + dayNumber +"\n" +
                "setsDone = " + setsDone);
        sets = new TrainingDay[] {new TrainingDay((TextView) findViewById(R.id.row1TextView), false, 1),
                new TrainingDay((TextView) findViewById(R.id.row2TextView), false, 2),
                new TrainingDay((TextView) findViewById(R.id.row3TextView), false, 3),
                new TrainingDay((TextView) findViewById(R.id.row4TextView), false, 4),
                new TrainingDay((TextView) findViewById(R.id.row5TextView), false, 5)};
        for (int i=0; i<5;i++) {
            sets[i].getThisDayTextView().setText(""+thePullupsCount);
            if (i<setsDone && setsDone > 0) {
                sets[i].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            } else {
                sets[i].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            }
            //  final int j = i;
            onSetClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sets[setsDone].setDone(true);
                    sets[setsDone].getThisDayTextView().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    sets[setsDone].getThisDayTextView().setClickable(false);

                    if (setsDone < 4) {
                        setsDone++;
                        TextView trainingDayInfoTextView = findViewById(R.id.trainingDayInfoTextView);
                        trainingDayInfoTextView.setText("thePullUpsCount = "+ thePullupsCount +"\n"+ "dayNumber =" + dayNumber +"\n" +
                                "setsDone = " + setsDone);
                        sets[setsDone].getThisDayTextView().setOnClickListener(onSetClickListener);
                    } else {
                        setsDone = 0;
                        Intent intent1 = new Intent(TrainingDayActivity.this, MainActivity.class);
                        intent1.putExtra("daysCompleted", dayNumber);
                        intent1.putExtra("pullupsCount", thePullupsCount);
                        writeToFile(getApplicationContext(), "" + thePullupsCount + ">>"  + dayNumber+ ">>" +  setsDone);

                        startActivity(intent1);

                    }
                }
            };
            if (setsDone<5)
                sets[setsDone].getThisDayTextView().setOnClickListener(onSetClickListener);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writeToFile(this, "" + thePullupsCount + ">>"  + daysCompleted+ ">>" +  setsDone);
        Log.d("mylog", "onDestroy writeToFile() = " + thePullupsCount + ">>"  + daysCompleted+ ">>" +  setsDone);
        Log.d("mylog", "onDestroy readFromFile() = " +  readFromFile(this));
    }
}
