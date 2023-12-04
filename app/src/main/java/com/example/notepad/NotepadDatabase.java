package com.example.notepad;

public class NotepadDatabase {
    private static NotepadDatabase instance;
    private int arrayIndex;
    private boolean createNewNote = false;
    private boolean deleteNote = false;
    private Notepad[] getNotepad;

    NotepadDatabase() {
        arrayIndex = 0;
        getNotepad = new Notepad[100];
    }

    public static NotepadDatabase getInstance() {
        if (instance == null) {
            instance = new NotepadDatabase();
        }
        return instance;
    }

    public boolean isCreateNewNote() {
        return createNewNote;
    }

    public void setCreateNewNote(boolean createNewNote) {
        this.createNewNote = createNewNote;
    }

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public Notepad[] getNotepad() {
        return getNotepad;
    }

    public void setNotepadIndex(int arrayIndex, String title, String note) {
        getNotepad[arrayIndex].setTitle(title);
        getNotepad[arrayIndex].setNote(note);
    }
    public void setNotepadIndex(int arrayIndex, String title) {
        getNotepad[arrayIndex].setTitle(title);
    }
    public void deleteNotepadIndex(int arrayIndex){
       getNotepad[arrayIndex] = null;
    }

    public boolean isDeleteNote() {
        return deleteNote;
    }

    public void setDeleteNote(boolean deleteNote) {
        this.deleteNote = deleteNote;
    }
}
