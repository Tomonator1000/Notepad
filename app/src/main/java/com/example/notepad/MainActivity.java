package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;


public class MainActivity extends AppCompatActivity {
    //DECLARED VARS
    Button btnCreateNote, newNoteButton; //ui buttons.
    ScrollView scrollViewNotes; //scroll view for notes.
    LinearLayout buttonLayout; //layout that allows for button use & dynamic creation.
    Intent NotepadActivity; //2nd Activity for Notepad editing.
    int count = 0, index; //count for amount of notes created, index for current index in array.
    NotepadDatabase notepadDatabase; //'database' for all our notes.
    Button currentButton; //dynamically created button used to open notes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInit();
    }
    public void onInit(){ //INITIALIZED VARS
        btnCreateNote = findViewById(R.id.btnNewNote);
        scrollViewNotes = findViewById(R.id.buttonScrollView);
        buttonLayout = findViewById(R.id.buttonLayout);

        NotepadActivity = new Intent(this,NotepadActivity.class);
        new NotepadDatabase();
        //returns an instance of Notepad to have the same version through activities (MainActivity and NotepadActivity respectively)
        notepadDatabase = NotepadDatabase.getInstance();
    }
    public void createNote(View v){ //onClick creates new note.
        count++; //increases count for button# when creating a new button to print this number
        index = nextAvailableID(); //finds the next id not taken by a object

        //creates a new button
        newNoteButton = new Button(this);
        newNoteButton.setText("(New) Note #" + count);
        newNoteButton.setId(index); //sets ID for identification later(having index and button ID be the same number simplifies the process)

        //creates the properties for a new instance of a note button.
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(10, Color.DKGRAY); //outline color
        drawable.setColor(Color.LTGRAY);
        newNoteButton.setBackground(drawable); //sets properties to new instance of button

        //adds new button to layout/scroll view
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
        buttonLayout.addView(newNoteButton, lp);

        //creates new instance of notepad to save to notepad database array.
        notepadDatabase.getNotepad()[index] = new Notepad();
        System.out.println(index); //sout for index

        //click listener used to determine what id the button is (uses lambda)
        newNoteButton.setOnClickListener(v1 -> openNote((Button) v1));

        btnCreateNote.setEnabled(false); //disables create note until new note has been accessed.
        btnCreateNote.setText("New Note #" + count + " WAS CREATED!");
    }
    public void openNote(Button button) { //onClick opens instance of note.
        currentButton = button; //saves the button so it can be deleted later(if user clicks delete)

        //flag defines what the activities will do in the background
        NotepadActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        notepadDatabase.setArrayIndex(button.getId());
        NotepadActivity.putExtra("button", button.getId()); //passes button id to the other program
        startActivity(NotepadActivity);
    }
    @Override
    public void onResume() { //RESUMES application when NotepadActivity returns back to Main Activity
        super.onResume();

        //finds the group of buttons in buttonLayout and makes it a ViewGroup
        ViewGroup parentLayout = buttonLayout;

        if(notepadDatabase.isCreateNewNote()) {
            //allows user to create new note if previous new note was accessed.
            btnCreateNote.setEnabled(true);
            btnCreateNote.setText("CREATE NEW NOTE");
        }

        if(notepadDatabase.isDeleteNote()){
            //deletes button and updates count
            buttonLayout.removeView(currentButton);
            count--;
        }

        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            //hooks up every button, one by one, to a childView under the parent view
            View childView = parentLayout.getChildAt(i);

            //checks if the item its looking at is a button
            if (childView instanceof Button) {
                //pulls the id the button was assigned earlier, buttonId and the index the item is placed
                //will always be the same so the numbers never get mixed up
                int buttonId = childView.getId();

                //renames button if button check is valid
                if(notepadDatabase.getNotepad()[buttonId].getTitle() != null) {
                    ((Button) childView).setText(notepadDatabase.getNotepad()[buttonId].getTitle());
                }
                else{
                    //else, the button did not have a value inside of it so we give it a default value
                    notepadDatabase.setNotepadIndex(buttonId, "Note " + buttonId);
                    ((Button) childView).setText(notepadDatabase.getNotepad()[buttonId].getTitle());
                }
            }
        }

    }
    public int nextAvailableID(){
        //locates next empty slot within array.
        int count = 0;
        int emptyCount = 0; //temp count
        for (int i = 0; i < notepadDatabase.getNotepad().length; i++) {
            if(notepadDatabase.getNotepad()[i] == null){
                count = i;
                System.out.println(count); //sout count
                return count;
            } else {
                emptyCount++; //adds to temp count if slot is not empty
            }
            if(emptyCount >= notepadDatabase.getNotepad().length){
                //if empty count is equal to length of notepad database array
                //(meaning entire array is full)
                Notepad[] tempNote = new Notepad[notepadDatabase.getNotepad().length + (50)]; //increase array by 50
                for (int h = 0; h < notepadDatabase.getNotepad().length; h++) {
                    //for every slot in original database array, set to new temp array
                    tempNote[h] = notepadDatabase.getNotepad()[h];
                }
                notepadDatabase.setGetNotepad(tempNote); //assign temp array to original array.
            }
        }
        return -1; //returns 'default' value  if check fails
    }
}
