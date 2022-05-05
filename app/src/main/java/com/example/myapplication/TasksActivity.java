package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity implements TaskDialog.taskDialogListener{
    ArrayList<MyTask> tasks;
    TasksAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Intent i = getIntent();
        FirebaseUser user = i.getParcelableExtra("user");
        //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance("https://task-fcf51-default-rtdb.firebaseio.com/");
        database.setPersistenceEnabled(true);
        String uid = user.getUid();
        myRef = database.getReference(uid);
        tasks = new ArrayList<>();
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot postSnapshot: task.getResult().getChildren()) {
                        tasks.add(postSnapshot.getValue(MyTask.class));
                    }
                    adapter.notifyDataSetChanged();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MyTask> newTasks = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    newTasks.add(postSnapshot.getValue(MyTask.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FireBase read failed ", error.getMessage());
            }
        });


                }
            }
        });

        RecyclerView rv = findViewById(R.id.tasksRV);
        adapter = new TasksAdapter(this, tasks);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        adapter.setListener(tasks -> {
            myRef.setValue(tasks);
        });
        FloatingActionButton newTaskFAB = findViewById(R.id.newTaskFAB);

        newTaskFAB.setOnClickListener(v -> {
            TaskDialog taskDialog = new TaskDialog();
            taskDialog.show(getSupportFragmentManager(), "taskDialog");
        });
    }

    @Override
    public void submit(String taskString) {
        tasks.add(new MyTask(taskString));
        adapter.notifyItemInserted(tasks.size());
        myRef.setValue(tasks);
    }

}