package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;


//FIXME: LAG on switch between views.
//FIXME: MAINACTIVITY does not actively 'save'. (its cause the app crashes in the second half when saving a note due to not fetching the right id)
//TODO: UPDATE note name to button display name.
//TODO: multiple if statements for note.isEmpty check, otherwise set to default "New Note #1" & " ";
public class MainActivity extends AppCompatActivity {
    Button btnCreateNote, newNoteButton;
    ScrollView scrollViewNotes;
    LinearLayout buttonLayout;
    Intent NotepadActivity;
    Bundle savedInstanceState;
    int count = 0, index;
    NotepadDatabase notepadDatabase;

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

        //adds it to layout
        LinearLayout ll = findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(newNoteButton, lp);

        //creates a new notepad to save data to said array index
        Notepad n = new Notepad();
        notepadDatabase.getNotepad()[index] = n;
        System.out.println(index);
        //notepadDatabase.arrayIndex = index;

        //click listener used to determine what id the button is
        newNoteButton.setOnClickListener(v1 -> openNote((Button) v1));

        btnCreateNote.setClickable(false);
    }

    public void openNote(Button button) {
        // runs when the note is opened
        NotepadActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        notepadDatabase.setArrayIndex(button.getId());
        NotepadActivity.putExtra("button", button.getId()); //passes the button id to the other program
        System.out.println(button.getId());
        startActivity(NotepadActivity);
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


    @Override
    public void onResume() {
        //runs whenever the app is resumed or comes back from second activity
        super.onResume();
        if(notepadDatabase.isCreateNewNote()) {
            btnCreateNote.setClickable(true);
        }
        //ensures the method does not run on start
        if(objectsInList() != 0){
            ViewGroup parentLayout = findViewById(R.id.buttonLayout);

            for (int i = 0; i < parentLayout.getChildCount(); i++) {
                View childView = parentLayout.getChildAt(i);

                System.out.println("Iteration " + i + ", Child ID: " + childView.getId());

                if (childView instanceof Button && childView.getId() == index) {
                    ((Button) childView).setText(notepadDatabase.getNotepad()[index].getTitle());
                }
            }
        }
    }
}
