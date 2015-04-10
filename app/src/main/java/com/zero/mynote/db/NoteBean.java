package com.zero.mynote.db;

/**
 * Created by luowei on 15/4/10.
 */
public class NoteBean {
    private int noteID;
    private String noteTime;
    private String noteText;

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return noteText;
    }
}
