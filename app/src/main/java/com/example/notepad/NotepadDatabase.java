package com.example.notepad;

public class NotepadDatabase {
    private static NotepadDatabase instance; //makes an instance so the same database is available to both activities
    private int arrayIndex; //currentIndex that is being used in the notepadActivity
    private boolean createNewNote = false; //boolean to check to create new note
    private boolean deleteNote = false; //boolean to check if it can delete a note
    private Notepad[] getNotepad; //database itself, uses the Notepad class

    NotepadDatabase() {
        //default constructor will instantiate the variables
        arrayIndex = 0;
        getNotepad = new Notepad[100];
    }

    public static NotepadDatabase getInstance() {
        //runs whenever you want an instance of notepad database and not a new one
        //allows synced used of the database between activities
        if (instance == null) {
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
        //used to set only the name variable at a given index, used when no note is set, but a title was
        if(getNotepad[arrayIndex] == null){
            Notepad n = new Notepad();
            getNotepad[arrayIndex] = n;
        }
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
