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
        notepadDatabase.setDeleteNote(false);
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
        //saves the note to the current index the buttonid is on
        int buttonId = getIntent().getIntExtra("button", -1); //gets my extra i passed in
        if(!(noteName.getText().toString().isEmpty()) && buttonId != -1){
            notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString());
            if(!noteNote.getText().toString().isEmpty()){
                notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString(), noteNote.getText().toString());
            }
            notepadDatabase.setCreateNewNote(true);
        }
        finish();
    }

    public void onDeleteClick(View v){
        //deletes the notepad index and sets the boolean to true so i can know to delete the button when the app returns to MainActivity
        int buttonId = getIntent().getIntExtra("button", -1);
        notepadDatabase.deleteNotepadIndex(buttonId);
        notepadDatabase.setDeleteNote(true);
        finish();
    }
}