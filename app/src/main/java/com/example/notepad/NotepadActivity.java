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
        //new NotepadDatabase();
        notepadDatabase = NotepadDatabase.getInstance();

        notepadDatabase.setCreateNewNote(false);
        onInit();

        MainActivity = new Intent(this, MainActivity.class);
    }

    protected void onInit(){
        noteName = findViewById(R.id.etNoteName);
        //TODO: updated noteNote to multiLine textbox. might implicate problems
        noteNote = findViewById(R.id.etNoteNote);
        noteSave = findViewById(R.id.btnSaveEditNote);
        noteCancel = findViewById(R.id.btnCancelEditNote);

        if(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()] != null){
            noteName.setText(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()].getTitle());
            noteNote.setText(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()].getNote());
        }
    }


    public void onCancelClick(View v){
        MainActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        finish();
    }

    public void onSaveClick(View v){
        //FIXME
        /*
        should not check if both are empty. seperate if statements for both title & note, if empty then default.
         */
        int buttonId = getIntent().getIntExtra("button", -1);
        if(!(noteName.getText().toString().isEmpty()) && buttonId != -1){
            notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString());
            if(!noteNote.getText().toString().isEmpty()){
                notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString(), noteNote.getText().toString());
            }
            notepadDatabase.setCreateNewNote(true);
        }
        finish();
    }
}