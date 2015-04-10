package com.zero.mynote.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luowei on 15/4/10.
 */
public class NoteInfo {
    private NoteDBHelper mDBHelper;

    public NoteInfo(Context context){
        mDBHelper = new NoteDBHelper(context);
    }

    public void insertNote(String noteTime,String noteText){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "insert into NoteTable(noteTime,noteText) values (?,?)";
        db.execSQL(sql,new String[]{noteTime,noteText});
        db.close();
    }

    public void deleteNote(int id){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "delete from NoteTable where noteID = ?";
        db.execSQL(sql,new Object[]{id});
        db.close();
    }


    public void deleteAll(){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "delete from NoteTable";
        db.execSQL(sql);
        db.close();

    }

    public List<NoteBean> getAllNotes(){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        List<NoteBean> list = new ArrayList<>();
        String sql = "select * from NoteTable";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                NoteBean note = new NoteBean();
                note.setNoteID(cursor.getInt(cursor.getColumnIndex("noteID")));
                note.setNoteTime(cursor.getString(cursor.getColumnIndex("noteTime")));
                note.setNoteText(cursor.getString(cursor.getColumnIndex("noteText")));
                list.add(note);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
