package com.example.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NotesDatabase extends SQLiteOpenHelper {
    ArrayList<String> arrayList=new ArrayList<String>();
    private static final String DATABASE_NAME = "Notes.db";
    private static final String TABLE_NAME = "noteslist";
    private static final String COL_1 = "DateTime";
    private static final String COL_2 = "Notes";

    public NotesDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (DateTime, Notes)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public boolean insertData(String dtf, String txt) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,dtf);
        contentValues.put(COL_2,txt);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public ArrayList<String> viewData() {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String x=cursor.getString(0);
            String y=cursor.getString(1);
            String z="\n"+x+" :-"+"\n\n"+y+"\n";
            arrayList.add(z);
        }
        return arrayList;
    }
    public boolean deleteData (String dttm, String notetxt) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME, "DateTime=? AND Notes=?",new String[]{dttm,notetxt}) > 0;
    }
}
