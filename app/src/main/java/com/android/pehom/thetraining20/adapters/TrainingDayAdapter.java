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
    private final String TRAINING_STATE = "trainingState";
    private final String APP_STATE = "APP_STATE";

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
        final TextView[] textViews = new TextView[(setsNumber/7+1)*7];

        if (setsNumber > 7) {
            final LinearLayout[] childLinearLayouts = new LinearLayout[setsNumber / 7 + 1];
          //  Log.d("trainingDayAdapter", "setsNumber / 9 = " + setsNumber + " / " + "9" + " = " + setsNumber / 9);
            for (int n = 0; n <= setsNumber / 7; n++) {
                childLinearLayouts[n] = new LinearLayout(holder.trainingDayParentLinearLayout.getContext());
                LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                childParams.setMargins(0, 20, 0, 0);
                childLinearLayouts[n].setLayoutParams(childParams);
                childLinearLayouts[n].setOrientation(LinearLayout.HORIZONTAL);
                holder.trainingDayParentLinearLayout.addView(childLinearLayouts[n]);

                for (int i = 0; i < 7; i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 140, 1);
                  //  Log.d("getWidth", "i = " + i);
                    layoutParams.setMargins(10, 0, 10, 0);
                    textViews[i + n * 7] = new TextView(holder.trainingDayParentLinearLayout.getContext());
                    textViews[i + n * 7].setLayoutParams(layoutParams);
                    textViews[i + n * 7].setText("" + data.get(position).getSet());
                    textViews[i + n * 7].setGravity(Gravity.CENTER);
                    textViews[i + n * 7].setTextSize((float) 30);
                    textViews[i + n * 7].setBackgroundColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimary));
                    if (i < holder.setsDone) {
                        textViews[i + n * 7].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorPrimaryDark));
                        textViews[i + n * 7].setClickable(false);
                    } else {
                        textViews[i + n * 7].setTextColor(ContextCompat.getColor(holder.trainingDayParentLinearLayout.getContext(), R.color.colorAccent));
                        final int finalI = i + n * 7;
                        clickListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (setsNumber == 1) {
                                    textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                    textViews[finalI].setClickable(false);
                                    holder.setsDone++;
                                    schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                    exercisesCount--;
                                    if (exercisesCount == 0) {
                                        schedule.setDaysCompleted(schedule.getDaysCompleted() + 1);
                                        writeToFile(v.getContext(), TRAINING_STATE, schedule.toString());
                                       // Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                        if (schedule.getDaysCompleted() < 28) {
                                            Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                            v.getContext().startActivity(intent);
                                        } else {
                                            writeToFile(v.getContext(), APP_STATE, "schedule finished");
                                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            v.getContext().startActivity(intent);
                                        }
                                    }
                                } else {
                                    int prevTextView = finalI - 1;
                                    if (holder.setsDone == 0 && finalI == 0) {
                                        textViews[holder.setsDone].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                        textViews[holder.setsDone].setClickable(false);
                                        holder.setsDone++;
                                        schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                    } else if (!textViews[prevTextView].isClickable()) {
                                        if (holder.setsDone < setsNumber - 1) {
                                            textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                            textViews[finalI].setClickable(false);
                                            holder.setsDone++;
                                            schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                        } else if (holder.setsDone == setsNumber - 1) {
                                            textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                            textViews[finalI].setClickable(false);
                                            holder.setsDone++;
                                            schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                            exercisesCount--;
                                            if (exercisesCount == 0) {
                                                schedule.setDaysCompleted(schedule.getDaysCompleted() + 1);
                                                writeToFile(v.getContext(), TRAINING_STATE, schedule.toString());
                                              //  Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                                if (schedule.getDaysCompleted() < 28) {
                                                    Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                                    v.getContext().startActivity(intent);
                                                } else {
                                                    writeToFile(v.getContext(), APP_STATE, "schedule finished");
                                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    v.getContext().startActivity(intent);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        };
                        textViews[i + n * 7].setOnClickListener(clickListener);
                    }
                    childLinearLayouts[n].addView(textViews[i + n * 7]);
                    if ((i + n * 7) >= setsNumber) {
                        textViews[i + n * 7].setVisibility(View.INVISIBLE);
                    }
                }
            }
        } else {
            LinearLayout childLinearLayout = new LinearLayout(holder.trainingDayParentLinearLayout.getContext());
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            childParams.setMargins(0, 20, 0, 0);
            childLinearLayout.setLayoutParams(childParams);
            childLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            holder.trainingDayParentLinearLayout.addView(childLinearLayout);
            for (int i = 0; i < setsNumber; i++) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 140, 1);
              //  Log.d("getWidth", "i = " + i);
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
                        @Override
                        public void onClick(View v) {
                            if (setsNumber == 1) {
                                textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                textViews[finalI].setClickable(false);
                                holder.setsDone++;
                                schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                exercisesCount--;
                                if (exercisesCount == 0) {
                                    schedule.setDaysCompleted(schedule.getDaysCompleted() + 1);
                                    writeToFile(v.getContext(), TRAINING_STATE, schedule.toString());
                                    Log.d("umbaba", "days completed = " + schedule.getDaysCompleted());
                                   // Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                    if (schedule.getDaysCompleted() < 28) {
                                        Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                        v.getContext().startActivity(intent);
                                    } else {
                                        writeToFile(v.getContext(), APP_STATE, "schedule finished");
                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                        intent.putExtra("scheduleFinished", true);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        v.getContext().startActivity(intent);
                                    }
                                }
                            } else {
                                int prevTextView = finalI - 1;
                                if (holder.setsDone == 0 && finalI == 0) {
                                    textViews[holder.setsDone].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                    textViews[holder.setsDone].setClickable(false);
                                    holder.setsDone++;
                                    schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                } else if (!textViews[prevTextView].isClickable()) {
                                    if (holder.setsDone < setsNumber - 1) {
                                        textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                        textViews[finalI].setClickable(false);
                                        holder.setsDone++;
                                        schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                    } else if (holder.setsDone == setsNumber - 1) {
                                        textViews[finalI].setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));
                                        textViews[finalI].setClickable(false);
                                        holder.setsDone++;
                                        schedule.getTrainingDays().get(schedule.getDaysCompleted()).getExercises().get(position).setSetsDone(holder.setsDone);
                                        exercisesCount--;
                                        if (exercisesCount == 0) {
                                            schedule.setDaysCompleted(schedule.getDaysCompleted() + 1);
                                            writeToFile(v.getContext(), TRAINING_STATE, schedule.toString());
                                          //  Log.d("trainingDayAdapter", "writeToFile data = " + schedule.toString());
                                            if (schedule.getDaysCompleted() < 28) {
                                                Log.d("umbaba", "days completed = " + schedule.getDaysCompleted());
                                                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                                                v.getContext().startActivity(intent);
                                            } else {
                                               // Log.d("daysCompleted", "days completed = " + schedule.getDaysCompleted());
                                                writeToFile(v.getContext(), APP_STATE, "schedule finished");
                                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                intent.putExtra("scheduleFinished", true);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
