package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotepadActivity extends AppCompatActivity {
    //Second Activity, will open up the note you clicked and populate the note fields if there is data saved in that note
    EditText noteName, noteNote; //UI Text Boxes
    Button noteSave, noteCancel; //ui buttons (not used much)
    Intent MainActivity; //was used to swap between activities, but was removed for finish();
    NotepadDatabase notepadDatabase; //database where we pull note data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        //new NotepadDatabase();
        notepadDatabase = NotepadDatabase.getInstance();

        //sets booleans so checks are only true if set by a method later
        notepadDatabase.setCreateNewNote(false);
        notepadDatabase.setDeleteNote(false);
        onInit();

        MainActivity = new Intent(this, MainActivity.class);
    }

    protected void onInit(){
        noteName = findViewById(R.id.etNoteName);
        noteNote = findViewById(R.id.etNoteNote);
        noteSave = findViewById(R.id.btnSaveEditNote);
        noteCancel = findViewById(R.id.btnCancelEditNote);

        if(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()] != null){
            //checks if the index this note is assigned to is empty or not, if not, it will populate the fields
            noteName.setText(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()].getTitle());
            noteNote.setText(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()].getNote());
        }

    }


    public void onCancelClick(View v){
        //just sends you back if your cancel
        notepadDatabase.setCreateNewNote(true);
        MainActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        finish();
    }

    public void onSaveClick(View v){
        //saves the note to the current index the buttonid is on
        int buttonId = getIntent().getIntExtra("button", -1); //gets the extra that was passed in from MainActivity
        if(!(noteName.getText().toString().isEmpty()) && buttonId != -1){
            //runs if the name is not empty and the extra that was passed in was not a default value
            notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString());
            if(!noteNote.getText().toString().isEmpty()){
                //runs if the note has text in it
                notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString(), noteNote.getText().toString());
            }
            //sets the boolean to true so you can create a new note (don't want user spam clicking create new note)
            notepadDatabase.setCreateNewNote(true);
        } else {
            notepadDatabase.setNotepadIndex(buttonId, "NOTE " + (buttonId + 1));
        }
        finish();
    }

    public void onDeleteClick(View v){
        //deletes the notepad index and sets the boolean to true so the app can
        //know to delete the button when the app returns to MainActivity
        int buttonId = getIntent().getIntExtra("button", -1);
        notepadDatabase.deleteNotepadIndex(buttonId);
        notepadDatabase.setDeleteNote(true);
        notepadDatabase.setCreateNewNote(true);
        finish();
    }
}