package com.android.pehom.thetraining20.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pehom.thetraining20.CustomLinearLayoutManager;
import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.adapters.CountAdapter;
import com.android.pehom.thetraining20.adapters.CreateScheduleAdapter;
import com.android.pehom.thetraining20.models.Exercise;
import com.android.pehom.thetraining20.models.Schedule;
import com.google.android.material.textfield.TextInputEditText;

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
//    private TextView pullupsCountTextView, pullupsTitleTextView;
//    private int pullupsCount, thePullupsCount;
    private String daysCompleted, setsDone;
    private RecyclerView createSheduleRecyclerView;
    private List<Exercise> exercises;
    private float stopX, startX, dX;
    private CreateScheduleAdapter createScheduleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String readFile;
        readFile = readFromFile(this,trainingState);
        Log.d("mylog", "onCreate readFromFile = " + readFromFile(this,trainingState));
        if (!readFile.equals("")) {
            /*daysCompleted = readFile[1].trim();
            setsDone = readFile[2].trim();
        } else {
            daysCompleted="0";
            setsDone= "0";
        }
        if (readFile[0].trim().equals("0") && readFile[1].trim().equals("0") && readFile[2].trim().equals("0")){*/

            createSheduleRecyclerView = findViewById(R.id.createScheduleRecyclerView);
            createSheduleRecyclerView.setHasFixedSize(true);
            exercises = new ArrayList<>();
            exercises.add(new Exercise(getResources().getString(R.string.exercise1), 5,10,0));
            exercises.add(new Exercise(getResources().getString(R.string.exercise2), 5,  25,0));
            exercises.add(new Exercise(getResources().getString(R.string.exercise3), 5,  20,0));

            RecyclerView.LayoutManager createScheduleLayoutManager = new LinearLayoutManager(this);
            createScheduleAdapter = buildScheduleAdapter();

            createSheduleRecyclerView.setLayoutManager(createScheduleLayoutManager);
            createSheduleRecyclerView.setAdapter(createScheduleAdapter);

        } else {
            Log.d("mylog", "MainActivity > onCreate > readFile = " + readFile);
            startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
    }

    private CreateScheduleAdapter buildScheduleAdapter(){
        final CreateScheduleAdapter adapter = new CreateScheduleAdapter(exercises, new CreateScheduleAdapter.OnTitleTouchListener() {
            @Override
            public void onTitleTouch(RecyclerView.ViewHolder viewHolder, MotionEvent event, int position) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        startX = event.getRawX();
                        dX = viewHolder.itemView.getX() - event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        if (event.getRawX() > startX) {
                            viewHolder.itemView.animate()
                                    .x(event.getRawX() + dX)
                                    .setDuration(0)
                                    .start();
                        }
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        stopX = event.getRawX();
                        Log.d("mylog", "startx = " + startX + "  stopx = " + stopX);

                        if (stopX - startX > 200) {
                            createScheduleAdapter.removeItem(viewHolder);
                        }
                        else {
                            viewHolder.itemView.animate()
                                    .x(0)
                                    .setDuration(0)
                                    .start();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d("mylog", "action canceled");

                        viewHolder.itemView.animate()
                                .x(0)
                                .setDuration(0)
                                .start();
                        break;
                }
            }
        }, new CreateScheduleAdapter.OnSetsNumberTouchListener() {
            @Override
            public void onSetsNumberTouch(TextView tv, MotionEvent event, int position) {
                int setsNumber;
                try {
                    setsNumber = Integer.parseInt(tv.getText().toString());
                } catch (IllegalArgumentException iae) {
                    Log.d("mylog", "error: ", iae);
                    setsNumber = 0;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        startX = event.getRawX();
                        dX = tv.getX() - event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        if (event.getRawX() > startX) {
                        }
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        stopX = event.getRawX();
                        Log.d("mylog", "startx = " + startX + "  stopx = " + stopX);

                        if (stopX - startX > 0) {
                            if (setsNumber>0)  {
                                setsNumber--;
                            }
                            tv.setText("" + setsNumber);
                            exercises.get(position).setSetsNumber(setsNumber);
                        } else if (stopX - startX < 0) {
                            setsNumber++;
                            tv.setText("" + setsNumber);
                            exercises.get(position).setSetsNumber(setsNumber);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d("mylog", "action canceled");
                        break;
                }
            }
        }, new CreateScheduleAdapter.OnSetTouchListener() {
            @Override
            public void onCountTouch(TextView tv, MotionEvent event, int position) {
                int set;
                try {
                    set = Integer.parseInt(tv.getText().toString());
                } catch (IllegalArgumentException iae) {
                    Log.d("mylog", "error: ", iae);
                    set = 0;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        startX = event.getRawX();
                        dX = tv.getX() - event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        stopX = event.getRawX();
                        Log.d("mylog", "startx = " + startX + "  stopx = " + stopX);

                        if (stopX - startX > 0) {
                            if (set>0) {
                                set--;
                            }
                            tv.setText("" + set);
                            exercises.get(position).setSet(set);

                        } else if (stopX - startX < 0) {
                            set++;
                            tv.setText("" + set);
                            exercises.get(position).setSet(set);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d("mylog", "action canceled");
                        break;
                }
            }
        });
        return adapter;
    }

    private RecyclerView createCountRecyclerView() {
        RecyclerView countRecyclerView = new RecyclerView(this);
        CustomLinearLayoutManager layoutManager =
                new CustomLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ArrayList<Integer> countArrayList = new ArrayList<>();
        for (int i= 0; i<500; i++){
            countArrayList.add(i);
        }
        CountAdapter countAdapter = new CountAdapter(countArrayList);
        countRecyclerView.setLayoutManager(layoutManager);
        countRecyclerView.setAdapter(countAdapter);

        /*ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                onChildDraw();
            }
        };*/
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
        Schedule newSchedule = new Schedule("test title", exercises, 0);
        writeToFile(this, trainingState, newSchedule.toString());
        String dayLongName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.getDefault());
        String prevTraining = readFromFile(this, trainingProgress);
        writeToFile(this, trainingProgress, prevTraining + "\n" + dayLongName + ".i." + newSchedule.toString() );
        Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
       // intent.putExtra("schedule", newSchedule.toString());
        startActivity(intent);
    }



    public void addExercise(View view) {
        TextInputEditText textInputEditText = findViewById(R.id.customExerciseTextInputEditText);
        String customExerciseTitle = textInputEditText.getText().toString();
        Log.d("mylog", "custom exercise title = " + customExerciseTitle);
        if (!customExerciseTitle.isEmpty()) {
            exercises.add(new Exercise(customExerciseTitle, 5,10,0));
            createScheduleAdapter.notifyDataSetChanged();
            textInputEditText.setText("");
            textInputEditText.clearFocus();
           // textInputEditText.set
        }
    }
}
