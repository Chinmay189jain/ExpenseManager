package com.example.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class IncomeDatabase extends SQLiteOpenHelper {
    ArrayList<String> List_income=new ArrayList<String>();
    ArrayList<String> List_expense=new ArrayList<String>();
    private static final String DATABASE_NAME = "transaction.db";
    private static final String TABLE_INCOME = "income";
    private static final String COL_1 = "DateIncome";
    private static final String COL_2 = "TimeIncome";
    private static final String COL_3 = "AmountIncome";
    private static final String COL_4 = "DescIncome";
    private int TOTAL_INCOME;
    private static final String TABLE_EXPENSE = "expense";
    private static final String COL_5 = "DateExpense";
    private static final String COL_6 = "TimeExpense";
    private static final String COL_7 = "AmountExpense";
    private static final String COL_8 = "DescExpense";
    private int TOTAL_EXPENSE;

    public IncomeDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_INCOME+" (DateIncome,TimeIncome,AmountIncome,DescIncome)");
        db.execSQL("CREATE TABLE "+TABLE_EXPENSE+" (DateExpense,TimeExpense,AmountExpense,DescExpense)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXPENSE);
    }
    public boolean insertIncomeData(String dtIn, String tmIn, int amtIn, String infoIn) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,dtIn);
        contentValues.put(COL_2,tmIn);
        contentValues.put(COL_3,amtIn);
        contentValues.put(COL_4,infoIn);
        long result=db.insert(TABLE_INCOME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public int totalIncome() {
        TOTAL_INCOME=0;
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_INCOME;
        Cursor cursor=db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            TOTAL_INCOME+=cursor.getInt(2);
        }
        return TOTAL_INCOME;
    }
    public boolean insertExpenseData(String dtEx, String tmEx, int amtEx, String infoEx) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_5,dtEx);
        contentValues.put(COL_6,tmEx);
        contentValues.put(COL_7,amtEx);
        contentValues.put(COL_8,infoEx);
        long result=db.insert(TABLE_EXPENSE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public int totalExpense() {
        TOTAL_EXPENSE=0;
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_EXPENSE;
        Cursor cursor=db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            TOTAL_EXPENSE+=cursor.getInt(2);
        }
        return TOTAL_EXPENSE;
    }

    public ArrayList<String> getList_income() {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_INCOME;
        Cursor cursor=db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String dt=cursor.getString(0);
            String tm=cursor.getString(1);
            int amt=cursor.getInt(2);
            String desc = cursor.getString(3);
            String rupee=String.valueOf(amt);
            String total="\n"+dt+" - "+tm+" :-"+"\n\n"+"Amount = "+rupee+"\n\n"+"Description :"+"\n"+desc+"\n";
            List_income.add(total);
        }
        return List_income;
    }

    public ArrayList<String> getList_expense() {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_EXPENSE;
        Cursor cursor=db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String dt=cursor.getString(0);
            String tm=cursor.getString(1);
            int amt=cursor.getInt(2);
            String desc = cursor.getString(3);
            String rupee=String.valueOf(amt);
            String total="\n"+dt+" - "+tm+" :-"+"\n\n"+"Amount = "+rupee+"\n\n"+"Description :"+"\n"+desc+"\n";
            List_expense.add(total);
        }
        return List_expense;
    }

    public boolean deleteData_income (String dt, String tm) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_INCOME, "DateIncome=? AND TimeIncome=?",new String[]{dt,tm}) > 0;
    }

    public boolean deleteData_expense (String dt, String tm) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_EXPENSE, "DateExpense=? AND TimeExpense=?",new String[]{dt,tm}) > 0;
    }
}
