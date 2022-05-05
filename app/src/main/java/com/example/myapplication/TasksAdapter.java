package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private Context context;
    ArrayList<MyTask> tasks;
    private taskAdapterListener listener;
    public TasksAdapter(Context context, ArrayList<MyTask> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder holder, int position) {
        MyTask task = tasks.get(position);
        holder.taskTV.setText(task.task);
        holder.taskCB.setChecked(false);
        holder.taskCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked == true) {
                Log.e("NIENSRTIE", tasks.toString());
                long delayMillis = 450;
                Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tasks.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    listener.delete(tasks);
                }
            },delayMillis);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTV;
        MaterialCheckBox taskCB;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTV = itemView.findViewById(R.id.taskTV);
            taskCB = itemView.findViewById(R.id.taskCB);
        }
    }
    public interface taskAdapterListener {
        void delete(ArrayList<MyTask> tasks);
    }
    public void setListener(taskAdapterListener listener) {
        this.listener = listener;
    }
}
