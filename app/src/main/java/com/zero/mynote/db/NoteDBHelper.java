package com.zero.mynote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luowei on 15/4/10.
 */
public class NoteDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static  final String DB_NAME = "Note";
    private static  final String CREATE_NOTE = "create table NoteTable("
            + "noteID integer primary key autoincrement not null,"
            + "noteTime varchar,"
            + "noteText text)";
    private static  int version = 1;

    public NoteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public NoteDBHelper(Context context){
        super(context,DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
