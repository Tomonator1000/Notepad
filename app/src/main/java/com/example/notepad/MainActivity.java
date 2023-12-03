package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    Button btnCreateNote, myButton;
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
        notepadDatabase = new NotepadDatabase();
    }

    public void createNote(View v){
        count++;
        index = nextAvailableID();
        System.out.println(index);
        myButton = new Button(this);
        myButton.setText("New Note" + count);
        myButton.setId(index);

        LinearLayout ll = findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);

        Notepad n = new Notepad();
        n.setCount(index);
        notepadDatabase.notepad[index] = n;
        //notepadDatabase.arrayIndex = index;
        //set opennote to on click for that dynamic button note

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(NotepadActivity);
            }
        });
    }

    public void openNote(View view) {
        NotepadActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //TODO: BUTTON CREATES NEW NOTE BUTTON. not opening with ID
        notepadDatabase.arrayIndex = view.getId();
        NotepadActivity.putExtra("index", index);
        System.out.println(view.getId());
        startActivity(NotepadActivity);
    }

    public int nextAvailableID(){
        int count = 0;
        for (int i = 0; i < notepadDatabase.notepad.length; i++) {
            if(notepadDatabase.notepad[i] == null){
                count = i;
                return count;
            }
        }
        return -1;
    }
    public int objectsInList(){
        int count = 0;
        for (int i = 0; i < notepadDatabase.notepad.length; i++) {
            if(notepadDatabase.notepad[i] != null){
                count++;
            }
        }
        return count;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(objectsInList() != 0){
            ViewGroup parentLayout = findViewById(R.id.buttonLayout); // Replace with the ID of your parent layout

            for (int i = 0; i < parentLayout.getChildCount(); i++) {
                View childView = parentLayout.getChildAt(i);

                if (childView instanceof Button && childView.getId() == index) {
                    ((Button) childView).setText(notepadDatabase.notepad[index].getTitle());
                }
            }
        }
    }
}
