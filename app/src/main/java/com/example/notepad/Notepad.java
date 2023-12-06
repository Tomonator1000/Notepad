package com.example.notepad;

public class Notepad {
    //Notepad class, saved within NotepadDatabase array.
    //DECLARED VARS
    private String title, note;

    public Notepad(String name, String note){
        title = name;
        this.note = note;
    }
    public Notepad(){

    }


    //GETTERS & SETTERS
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
