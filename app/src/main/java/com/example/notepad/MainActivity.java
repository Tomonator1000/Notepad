package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    Button btnCreateNote;
    ScrollView scrollViewNotes;
    LinearLayout buttonLayout;
    Intent NotepadActivity;

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
    }

    public void createNote(View v){
        openNote(v,1);
    }

    public void openNote(View view,int noteNum){
        NotepadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //TODO: BUTTON CREATES NEW NOTE BUTTON.
        Button myButton = new Button(this);
        myButton.setText("Push Me");

        LinearLayout ll = findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);

        NotepadActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(NotepadActivity);
    }

}
