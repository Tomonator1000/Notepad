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


//FIXME: LAG on switch between views.
//TODO: multiple if statements for note.isEmpty check, otherwise set to default "New Note #1" & " "; (huh?)
public class MainActivity extends AppCompatActivity {
    Button btnCreateNote, newNoteButton;
    ScrollView scrollViewNotes;
    LinearLayout buttonLayout;
    Intent NotepadActivity;
    Bundle savedInstanceState;
    int count = 0, index;
    NotepadDatabase notepadDatabase;
    Button currentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        onInit();
    }

    public void onInit(){
        btnCreateNote = findViewById(R.id.btnNewNote);
        scrollViewNotes = findViewById(R.id.buttonScrollView);
        buttonLayout = findViewById(R.id.buttonLayout);

        NotepadActivity = new Intent(this,NotepadActivity.class);
        new NotepadDatabase();
        //returns an instance of Notepad to have the same version through activities (MainActivity and NotepadActivity respectively)
        notepadDatabase = NotepadDatabase.getInstance();
    }

    public void createNote(View v){
        count++; //counts for button# when creating a new button to print this number
        index = nextAvailableID(); //finds the next id not taken by a object
        System.out.println(index);

        //creates a new button
        newNoteButton = new Button(this);
        newNoteButton.setText("New Note #" + count);
        newNoteButton.setId(index);
        //newNoteButton.setBackgroundColor(Color.CYAN);

        //sets the background of the button
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(5, Color.BLUE);
        drawable.setColor(Color.CYAN);
        newNoteButton.setBackground(drawable);

        //adds it to layout
        LinearLayout ll = findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        ll.addView(newNoteButton, lp);

        //creates a new notepad to save data to said array index
        Notepad n = new Notepad();
        notepadDatabase.getNotepad()[index] = n;
        System.out.println(index);
        //notepadDatabase.arrayIndex = index;

        //click listener used to determine what id the button is (uses lambda)
        newNoteButton.setOnClickListener(v1 -> openNote((Button) v1));

        btnCreateNote.setClickable(false);
    }

    public void openNote(Button button) {
        // runs when the note is opened
        currentButton = button; //sets the button so i can delete it later(if user wants to delete)

        //not sure what flag to use, clearing the not could save processing power
        NotepadActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        notepadDatabase.setArrayIndex(button.getId());
        NotepadActivity.putExtra("button", button.getId()); //passes the button id to the other program
        startActivity(NotepadActivity);
    }

    @Override
    public void onResume() {
        //runs whenever the app is resumed or comes back from second activity
        super.onResume();
        if(notepadDatabase.isCreateNewNote()) {
            //allows the user to create a new note if they edited the last note
            btnCreateNote.setClickable(true);
        }
        //ensures the method does not run on start
        if(objectsInList() != 0){
            //finds the group of buttons and makes it a ViewGroup
            ViewGroup parentLayout = findViewById(R.id.buttonLayout);

            if(notepadDatabase.isDeleteNote()){
                //save the button from earlier for quick and easy deletion
                buttonLayout.removeView(currentButton);
            }
            for (int i = 0; i < parentLayout.getChildCount(); i++) {
                //hooks up every button to a childView under the parent view
                View childView = parentLayout.getChildAt(i);

                //if statement checks if its the right button then renames it
                if (childView instanceof Button && childView.getId() == index) {
                    ((Button) childView).setText(notepadDatabase.getNotepad()[index].getTitle());
                }
            }
        }
    }

    public int nextAvailableID(){
        //finds the next spot in the array that is empty
        int count = 0;
        for (int i = 0; i < notepadDatabase.getNotepad().length; i++) {
            if(notepadDatabase.getNotepad()[i] == null){
                count = i;
                System.out.println(count);
                return count;
            }
        }
        return -1;
    }
    public int objectsInList(){
        //finds out how many objects exist in the list
        int count = 0;
        for (int i = 0; i < notepadDatabase.getNotepad().length; i++) {
            if(notepadDatabase.getNotepad()[i] != null){
                count++;
            }
        }
        return count;
    }

}
