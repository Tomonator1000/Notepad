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
    NotepadDatabase notepadDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        onInit();
        notepadDatabase = new NotepadDatabase();

        MainActivity = new Intent(this, MainActivity.class);

        if(notepadDatabase.notepad[notepadDatabase.arrayIndex] != null){
            noteName.setText(notepadDatabase.notepad[notepadDatabase.arrayIndex].getTitle());
        }
    }

    protected void onInit(){
        noteName = findViewById(R.id.etNoteName);
        noteNote = findViewById(R.id.etNoteNote);
        noteSave = findViewById(R.id.btnSaveEditNote);
        noteCancel = findViewById(R.id.btnCancelEditNote);
    }


    public void onCancelClick(View v){
        MainActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        finish();
        //startActivity(MainActivity);
    }

    public void onSaveClick(View v){
        if(!(noteName.getText().toString().isEmpty() && noteNote.getText().toString().isEmpty())){
            notepadDatabase.setNotepadIndex(notepadDatabase.arrayIndex, noteName.getText().toString(), noteName.getText().toString());
            /*
            notepadDatabase.notepad[notepadDatabase.arrayIndex].setTitle(noteName.getText().toString());
            notepadDatabase.notepad[notepadDatabase.arrayIndex].setNote(noteNote.getText().toString());
            System.out.println(notepadDatabase.notepad[notepadDatabase.arrayIndex].getTitle());

             */
        }
        finish();
        //startActivity(MainActivity);
    }
}