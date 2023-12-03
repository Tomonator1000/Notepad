package com.example.notepad;

public class NotepadDatabase {
    public int arrayIndex = 0;

    public Notepad[] notepad = new Notepad[30];

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public Notepad[] getNotepad() {
        return notepad;
    }

    public void setNotepadIndex(int arrayIndex, String title, String note) {
        notepad[arrayIndex].setTitle(title);
        notepad[arrayIndex].setNote(note);
    }
}
