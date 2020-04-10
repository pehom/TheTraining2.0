package com.android.pehom.thetraining20.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.activities.MainActivity;
import com.android.pehom.thetraining20.activities.ScheduleActivity;
import com.android.pehom.thetraining20.models.Exercise;
import com.android.pehom.thetraining20.models.Schedule;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class TrainingDayAdapter extends RecyclerView.Adapter<TrainingDayAdapter.TrainingDayViewHolder> {
    private List<Exercise> data;
    private Schedule schedule;
    private View.OnClickListener clickListener;
    private int exercisesCount;
    private final String trainingState = "trainingState";

    public TrainingDayAdapter(Schedule schedule) {
        this.data = schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises();
        exercisesCount = 0;
        for (int i = 0 ; i < data.size(); i++) {
            if (data.get(i).getSetsDone() < data.get(i).getSetsNumber()) {
                exercisesCount++;
            }
        }
        this.schedule = schedule;

    }

    @NonNull
    @Override
    public TrainingDayViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_day_recyclerview_item, parent,false);
        TrainingDayViewHolder viewHolder = new TrainingDayViewHolder(view, 0);
       // int position = viewHolder.getAdapterPosition();


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TrainingDayViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.exerciseTitleTextView.setText(data.get(position).getTitle());
        holder.setsDone = data.get(position).getSetsDone();
        final int setsNumber = data.get(position).getSetsNumber();
        final TextView[] textViews = new TextView[setsNumber];

        /*LinearLayout child1LL = new LinearLayout(holder.trainingDayParentLinearLayout.getContext());
        LinearLayout.LayoutParams child1Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        child1Params.setMargins(0,10,0,0);
        child1LL.setLayoutParams(child1Params);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams( 0, 140, 1);
        layoutParams1.setMargins(10, 0, 10, 0);
        TextView textView1 = new TextView(child1LL.getContext());
        textView1.setLayoutParams(layoutParams1);
        textView1.setText("whoop whoop");
        child1LL.addView(textView1);

        holder.trainingDayParentLinearLayout.addView(child1LL);
        LinearLayout child2LL = new LinearLayout(holder.trainingDayParentLinearLayout.getContext());
        child2LL.setLayoutParams(child1Params);
        holder.trainingDayParentLinearLayout.addView(child2LL);
        TextView textView2 = new TextView(child1LL.getContext());
        textView2.setLayoutParams(layoutParams1);
        textView2.setText("whoop whoop");
        child2LL.addView(textView2);*/


        if (setsNumber > 9) {
            final LinearLayout[] childLinearLayouts = new LinearLayout[setsNumber/9+1];
            /*for (int i = 0; i<setsNumber/9; i++) {
                childLinearLayouts[i] = new LinearLayout(holder.trainingDayParentLinearLayout.getContext());
                LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                childParams.setMargins(0,10,0,0);
                childLinearLayouts[i].setLayoutParams(childParams);
                childLinearLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( 0, 140, 1);
                layoutParams.setMargins(10, 0, 10, 0);
                TextView textView = new TextView(childLinearLayouts[i].getContext());
                textView.setLayoutParams(layoutParams);
                textView.setText("whoop whoop");
                holder.trainingDayParentLinearLayout.addView(childLinearLayouts[i]);
                childLinearLayouts[i].addView(textView);

            }*/
            Log.d("trainingDayAdapter", "setsNumber / 9 = " + setsNumber + " / " + "9" + " = " + setsNumber/9);
            for (int n=0; n <= setsNumber / 9; n++) {
                Log.d("get", " setsNumber = " +  setsNumber);

                Log.d("get", " setsNumber/9-1 = " +  (setsNumber/9-1));

                Log.d("get", "n = " + n);
                childLinearLayouts[n] = new LinearLayout(holder.trainingDayParentLinearLayout.getContext());
                LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                childParams.setMargins(0,20,0,0);
                childLinearLayouts[n].setLayoutParams(childParams);
                childLinearLayouts[n].setOrientation(LinearLayout.HORIZONTAL);
                holder.trainingDayParentLinearLayout.addView(childLinearLayouts[n]);

                if (n < setsNumber/9) {
                    for (int i = 0; i < 9; i++) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 140,1);
                        Log.d("getWidth", "i = " + i );
                        layoutParams.setMargins(10, 0, 10, 0);
                        textViews[i+n*9] = new TextView(holder.trainingDayParentLinearLayout.getContext());
                        textViews[i+n*9].setLayoutParams(layoutParams);
                        textViews[i+n*9].setText("" + data.get(position).getSet());
                        textViews[i+n*9].setGravity(Gravity.CENTER);
                        textViews[i+n*9].setTextSize((float) 30);
                        textViews[i+n*9].setBackgroundColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimary));
                        if (i < holder.setsDone) {
                            textViews[i+n*9].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimaryDark));
                            textViews[i+n*9].setClickable(false);
                        } else {
                            textViews[i+n*9].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorAccent));
                            final int finalI = i+n*9;
                            clickListener = new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    if (setsNumber == 1) {
                                        textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                        textViews[finalI].setClickable(false);
                                        holder.setsDone++;
                                        schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                        exercisesCount--;
                                        if (exercisesCount == 0){
                                            schedule.setDaysCompleted(schedule.getDaysCompleted()+1);
                                            writeToFile(v.getContext(), trainingState, schedule.toString());
                                            Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                            if (schedule.getDaysCompleted()<28) {
                                                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                                v.getContext().startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                v.getContext().startActivity(intent);
                                            }
                                        }
                                    } else {
                                        int prevTextView = finalI-1;
                                        if (holder.setsDone == 0 && finalI==0) {
                                            textViews[holder.setsDone].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                            textViews[holder.setsDone].setClickable(false);
                                            holder.setsDone++;
                                            schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                        } else if (!textViews[prevTextView].isClickable()){
                                            if ( holder.setsDone<setsNumber-1) {
                                                textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                                textViews[finalI].setClickable(false);
                                                holder.setsDone++;
                                                schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                            } else if(holder.setsDone == setsNumber-1){
                                                textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                                textViews[finalI].setClickable(false);
                                                holder.setsDone++;
                                                schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                                exercisesCount--;
                                                if (exercisesCount == 0){
                                                    schedule.setDaysCompleted(schedule.getDaysCompleted()+1);
                                                    writeToFile(v.getContext(), trainingState, schedule.toString());
                                                    Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                                    if (schedule.getDaysCompleted()<28) {
                                                        Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                                        v.getContext().startActivity(intent);
                                                    } else {
                                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                        v.getContext().startActivity(intent);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            };
                            textViews[i+n*9].setOnClickListener(clickListener);
                        }
                        childLinearLayouts[n].addView(textViews[i+n*9]);
                    }
                } else  if (n == setsNumber/9) {
                    for (int i=0; i <setsNumber % 9; i++ ) {
                        Log.d("get", "textview[1].getWidth =" + textViews[1].getWidth());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( 0, 140,1);
                        layoutParams.setMargins(10, 0, 10, 0);
                        textViews[i+n*9] = new TextView(holder.trainingDayParentLinearLayout.getContext());
                        textViews[i+n*9].setLayoutParams(layoutParams);
                        textViews[i+n*9].setText("" + data.get(position).getSet());
                        textViews[i+n*9].setGravity(Gravity.CENTER);
                        textViews[i+n*9].setTextSize((float) 30);
                        textViews[i+n*9].setBackgroundColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimary));
                        if (i < holder.setsDone) {
                            textViews[i+n*9].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimaryDark));
                            textViews[i+n*9].setClickable(false);
                        } else {
                            textViews[i+n*9].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorAccent));
                            final int finalI = i+n*9;
                            clickListener = new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    if (setsNumber == 1) {
                                        textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                        textViews[finalI].setClickable(false);
                                        holder.setsDone++;
                                        schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                        exercisesCount--;
                                        if (exercisesCount == 0){
                                            schedule.setDaysCompleted(schedule.getDaysCompleted()+1);
                                            writeToFile(v.getContext(), trainingState, schedule.toString());
                                            Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                            if (schedule.getDaysCompleted()<28) {
                                                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                                v.getContext().startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                v.getContext().startActivity(intent);
                                            }
                                        }
                                    } else {
                                        int prevTextView = finalI-1;
                                        if (holder.setsDone == 0 && finalI==0) {
                                            textViews[holder.setsDone].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                            textViews[holder.setsDone].setClickable(false);
                                            holder.setsDone++;
                                            schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                        } else if (!textViews[prevTextView].isClickable()){
                                            if ( holder.setsDone<setsNumber-1) {
                                                textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                                textViews[finalI].setClickable(false);
                                                holder.setsDone++;
                                                schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                            } else if(holder.setsDone == setsNumber-1){
                                                textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                                textViews[finalI].setClickable(false);
                                                holder.setsDone++;
                                                schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                                exercisesCount--;
                                                if (exercisesCount == 0){
                                                    schedule.setDaysCompleted(schedule.getDaysCompleted()+1);
                                                    writeToFile(v.getContext(), trainingState, schedule.toString());
                                                    Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                                    if (schedule.getDaysCompleted()<28) {
                                                        Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                                        v.getContext().startActivity(intent);
                                                    } else {
                                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                        v.getContext().startActivity(intent);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            };
                            textViews[i+n*9].setOnClickListener(clickListener);
                        }
                        childLinearLayouts[n].addView(textViews[i+n*9]);
                    }
                }
            }
        } else {
            LinearLayout childLinearLayout = new LinearLayout(holder.trainingDayParentLinearLayout.getContext());
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            childParams.setMargins(0,10,0,0);
            childLinearLayout.setLayoutParams(childParams);
            childLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            holder.trainingDayParentLinearLayout.addView(childLinearLayout);
            for (int i = 0; i < setsNumber; i++) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 140, 1);
                layoutParams.setMargins(10, 0, 10, 0);
                textViews[i] = new TextView(holder.trainingDayParentLinearLayout.getContext());
                textViews[i].setLayoutParams(layoutParams);
                textViews[i].setText("" + data.get(position).getSet());
                textViews[i].setGravity(Gravity.CENTER);
                textViews[i].setTextSize((float) 30);
                textViews[i].setBackgroundColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimary));
                if (i < holder.setsDone) {
                    textViews[i].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimaryDark));
                    textViews[i].setClickable(false);
                } else {
                    textViews[i].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorAccent));
                    final int finalI = i;
                    clickListener = new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            if (setsNumber == 1) {
                                textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                textViews[finalI].setClickable(false);
                                holder.setsDone++;
                                schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                exercisesCount--;
                                if (exercisesCount == 0){
                                    schedule.setDaysCompleted(schedule.getDaysCompleted()+1);
                                    writeToFile(v.getContext(), trainingState, schedule.toString());
                                    Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                    if (schedule.getDaysCompleted()<28) {
                                        Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                        v.getContext().startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                        v.getContext().startActivity(intent);
                                    }
                                }
                            } else {
                                int prevTextView = finalI-1;
                                if (holder.setsDone == 0 && finalI==0) {
                                    textViews[holder.setsDone].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                    textViews[holder.setsDone].setClickable(false);
                                    holder.setsDone++;
                                    schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                } else if (!textViews[prevTextView].isClickable()){
                                    if ( holder.setsDone<setsNumber-1) {
                                        textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                        textViews[finalI].setClickable(false);
                                        holder.setsDone++;
                                        schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                    } else if(holder.setsDone == setsNumber-1){
                                        textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                        textViews[finalI].setClickable(false);
                                        holder.setsDone++;
                                        schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                        exercisesCount--;
                                        if (exercisesCount == 0){
                                            schedule.setDaysCompleted(schedule.getDaysCompleted()+1);
                                            writeToFile(v.getContext(), trainingState, schedule.toString());
                                            Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                            if (schedule.getDaysCompleted()<28) {
                                                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                                v.getContext().startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                v.getContext().startActivity(intent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    };
                    textViews[i].setOnClickListener(clickListener);
                }
                childLinearLayout.addView(textViews[i]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrainingDayViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitleTextView;
        LinearLayout trainingDayParentLinearLayout;
        int setsDone;

        public TrainingDayViewHolder(@NonNull View itemView, int setsDone) {
            super(itemView);
            exerciseTitleTextView = itemView.findViewById(R.id.exerciseTitleTextView);
            trainingDayParentLinearLayout = itemView.findViewById(R.id.trainingDayMainLinearLayout);
            this.setsDone = setsDone;
        }
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

}
