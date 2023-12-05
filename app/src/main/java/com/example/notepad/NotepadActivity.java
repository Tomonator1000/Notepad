package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class NotepadActivity extends AppCompatActivity {
    //Second Activity, will open up the note you clicked and populate the note fields if there is data saved in that note
    //Second Activity, will open up a note and populate the note fields if data resides.
    //DECLARED VARS
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
        notepadDatabase = NotepadDatabase.getInstance(); //gets instance of database / array

        //sets booleans so checks are only true if set by a method later
        //defaults boolean to false for use of creation/deletion checks
        notepadDatabase.setCreateNewNote(false);
        notepadDatabase.setDeleteNote(false);
        onInit();

        MainActivity = new Intent(this, MainActivity.class);
    }
    protected void onInit(){ //INiTIALIZED VARS
        noteName = findViewById(R.id.etNoteName);
        noteNote = findViewById(R.id.etNoteNote);
        noteSave = findViewById(R.id.btnSaveEditNote);
        noteCancel = findViewById(R.id.btnCancelEditNote);

        if(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()] != null){
            //checks if the index this note is assigned to is empty or not, if not, it will populate the fields
            //populates note text boxes if index in array is not empty.
            noteName.setText(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()].getTitle());
            noteNote.setText(notepadDatabase.getNotepad()[notepadDatabase.getArrayIndex()].getNote());
        }
    }
    //saves the note to the current index the buttonid is on

    public void onSaveClick(View v){ //onClick to save edit note.
        int buttonId = getIntent().getIntExtra("button", -1); //gets the extra that was passed in from MainActivity
        if(!(noteName.getText().toString().isEmpty()) && buttonId != -1){
            //runs if the name is not empty and the extra that was passed in was not a default value
            //saves note title
            notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString());
            if(!noteNote.getText().toString().isEmpty()){
                //runs if the note has text in it
                //saves note text
                notepadDatabase.setNotepadIndex(buttonId, noteName.getText().toString(), noteNote.getText().toString());
            }
            //sets the boolean to true so you can create a new note (don't want user spam clicking create new note)
            //sets create note check to true, (only 1 new note at a time).
        } else {
            //defaults note title value to "NOTE #1", etc)
            notepadDatabase.setNotepadIndex(buttonId, "NOTE " + (buttonId + 1));
        }
        notepadDatabase.setCreateNewNote(true);
        finish(); //returns to previous activity.
    }
    public void onDeleteClick(View v){
        //deletes the notepad index and sets the boolean to true so the app can
        //know to delete the button when the app returns to MainActivity
        //sets check to delete note and allow creation of new note.
        //deletes current index
        int buttonId = getIntent().getIntExtra("button", -1);
        notepadDatabase.deleteNotepadIndex(buttonId);
        notepadDatabase.setDeleteNote(true);
        finish();
        notepadDatabase.setCreateNewNote(true);
        finish(); //returns to previous activity
    }
    public void onCancelClick(View v){ //onClick for canceling of edit note (similar to toggleUI)
        int buttonId = getIntent().getIntExtra("button", -1); //gets the extra that was passed from MainActivity
        if(noteName.getText().toString().isEmpty()){
            //allows creation of new note & defaults note name value.
            notepadDatabase.setNotepadIndex(buttonId, "NOTE " + (buttonId + 1));
            notepadDatabase.setCreateNewNote(true);
        }
        MainActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        finish(); //returns to previous activity.
    }
}
