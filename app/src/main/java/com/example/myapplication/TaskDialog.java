package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class TaskDialog extends AppCompatDialogFragment {
    private TextInputEditText taskText;
    private MaterialButton saveButton;
    private taskDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.task_dialog, null);
        builder.setView(view)
                //.setTitle("Create new Task")
                //.setNegativeButton("Cancel")
                /*.setPositiveButton("Save", (dialog, which) -> {
                    String taskString = taskText.getText().toString();
                    listener.submit(taskString);
                    //tasks.add(new Task(""));
                    //adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })*/;
        taskText = view.findViewById(R.id.taskDialogET);
        saveButton = view.findViewById(R.id.taskDialogSaveButton);
        saveButton.setOnClickListener(v -> {
            String taskString = taskText.getText().toString();
            listener.submit(taskString);
            dismiss();
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (taskDialogListener) context;
    }

    public interface taskDialogListener {
        void submit(String taskString);
    }
}
