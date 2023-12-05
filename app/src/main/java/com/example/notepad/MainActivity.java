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
public class MainActivity extends AppCompatActivity {
    Button btnCreateNote, newNoteButton; //ui buttons
    ScrollView scrollViewNotes; //Scrolling
    LinearLayout buttonLayout; //Layout for buttons so we can dynamically make buttons
    Intent NotepadActivity; //The notepad activity that we use to start a separate UI for notes
    int count = 0, index; //count counts how many buttons have been made, index is the current index the app is on
    NotepadDatabase notepadDatabase; //database for all our notes
    Button currentButton; //dynamically created button used to open notes

    public int getCount() {
        return count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //creates a new button
        newNoteButton = new Button(this);
        newNoteButton.setText("New Note #" + count);
        newNoteButton.setId(index); //sets ID for identification later(having index and button ID be the same number simplifies the process)

        //sets the background of the dynamic note button
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(10, Color.DKGRAY);
        drawable.setColor(Color.LTGRAY);
        newNoteButton.setBackground(drawable);

        //adds it to layout
        LinearLayout ll = findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
        ll.addView(newNoteButton, lp);

        //creates a new notepad to save data to said array index
        Notepad n = new Notepad();
        notepadDatabase.getNotepad()[index] = n;
        System.out.println(index);

        //click listener used to determine what id the button is (uses lambda)
        newNoteButton.setOnClickListener(v1 -> openNote((Button) v1));

        btnCreateNote.setClickable(false);
        btnCreateNote.setText("New Note #" + count + " WAS CREATED!");
    }

    public void openNote(Button button) {
        // runs when the note is opened
        currentButton = button; //saves the button so it can be deleted later(if user clicks delete)

        //not sure what flag to use, clearing the activity could save processing power
        NotepadActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        notepadDatabase.setArrayIndex(button.getId());
        NotepadActivity.putExtra("button", button.getId()); //passes the button id to the other program
        startActivity(NotepadActivity);
    }

    @Override
    public void onResume() {
        //onResume() runs whenever the app is resumed or comes back from second activity
        super.onResume();
        if(notepadDatabase.isCreateNewNote()) {
            //allows the user to create a new note if they edited the last note
            btnCreateNote.setClickable(true);
            btnCreateNote.setText("CREATE NEW NOTE");
        }
        //ensures the method does not run on start
        if(objectsInList() != 0){
            //finds the group of buttons in the buttonLayout and makes it a ViewGroup
            ViewGroup parentLayout = findViewById(R.id.buttonLayout);

            if(notepadDatabase.isDeleteNote()){
                //save the button from earlier for quick and easy deletion
                buttonLayout.removeView(currentButton);
                count--;
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
