package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotepadActivity extends AppCompatActivity {
    EditText noteName, noteNote;
    Button noteSave, noteCancel;
    Intent MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        onInit();

        MainActivity = new Intent(this, MainActivity.class);
    }

    protected void onInit(){
        noteName = findViewById(R.id.etNoteName);
        noteNote = findViewById(R.id.etNoteNote);
        noteSave = findViewById(R.id.btnSaveEditNote);
        noteCancel = findViewById(R.id.btnCancelEditNote);
    }


    public void onCancelClick(View v){
        MainActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(MainActivity);
    }
}