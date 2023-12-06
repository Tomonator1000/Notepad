package com.example.notepad;

public class NotepadDatabase {
    //DECLARED VARS
    private static NotepadDatabase instance; //makes an instance so the same database is available to both activities
    private int arrayIndex; //currentIndex that is being used in the notepadActivity
    private boolean createNewNote = false; //boolean to check to create new note
    private boolean deleteNote = false; //boolean to check if it can delete a note
    private Notepad[] getNotepad; //database itself, uses the Notepad class

    NotepadDatabase() { //default constructor
        arrayIndex = 0;
        getNotepad = new Notepad[100];
    }
    //GETTERS & SETTERS
    public static NotepadDatabase getInstance() {
        //gets instance of Database rather than creating new instance.
        //syncs database between activities.
        if (instance == null) { //creates new instance if instance is null.
            instance = new NotepadDatabase();
        }
        return instance;
    }

    //getters and setters
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
        //used to set both notepad variables at a given index
        getNotepad[arrayIndex].setTitle(title);
        getNotepad[arrayIndex].setNote(note);
    }
    public void setNotepadIndex(int arrayIndex, String title) {
        //used to set only the name variable at a given index, allows for empty note but given title.
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


    public void setGetNotepad(Notepad[] getNotepad) { //setNotepad to recreate & copy existing array over.
        this.getNotepad = getNotepad;
    }
}
