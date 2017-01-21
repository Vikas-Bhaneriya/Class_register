package com.example.vicky.paul;

/**
 * Created by vicky on 31/12/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;


import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class  Myhelperadapter {

    Myhelper helper;

    public Myhelperadapter(Context context) {
        helper = new Myhelper(context);
    }

    public Data getData(int id) {
        ++id;
        SQLiteDatabase db = helper.getReadableDatabase();
        String classname="";
        String classtype ="";
    String time = null;
        String date = null;

        String[] columns = { Myhelper.UID, Myhelper.CLASS_, Myhelper.CLASS_TYPE, Myhelper.TIMEI,Myhelper.DATE };
        try {
            Cursor cursor = db.query(Myhelper.TABLE_NAME, columns, null, null, null, null, null);
            cursor.move(id);
            classname = cursor.getString(cursor.getColumnIndex(Myhelper.CLASS_));
            classtype = cursor.getString(cursor.getColumnIndex(Myhelper.CLASS_TYPE));
            date =cursor.getString(cursor.getColumnIndex(Myhelper.DATE));
            time = cursor.getString(cursor.getColumnIndex(Myhelper.TIMEI));
        } catch (Exception e) {}
        finally{
            db.close();
            return new Data(classname,classtype,date,time);
        }
    }

    public void update(int id, String classname, String class_type , String date,int time) {
        ++id;
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            if (classname != null)
                db.execSQL("UPDATE " + Myhelper.TABLE_NAME + " SET " + Myhelper.CLASS_ + " = '" + classname + "' WHERE "+ Myhelper.UID + " = " + id);
            if (class_type!=null)
                db.execSQL("UPDATE " + Myhelper.TABLE_NAME + " SET " + Myhelper.CLASS_TYPE + " = " + class_type + "," + Myhelper.DATE+ " = " + date + " ," + Myhelper.TIMEI+ " = " + time + " WHERE " + Myhelper.UID + " = " + id);
        }catch(Exception e){
            Toast.makeText(helper.context, e+"",Toast.LENGTH_SHORT).show();
        }finally{
            db.close();
        }
    }

    public void remove(String classname) {

        SQLiteDatabase db = helper.getWritableDatabase();


        try {
            int f = db.delete(Myhelper.TABLE_NAME, Myhelper.CLASS_+ " = '"+classname+"'", null);
            Log.d("delete ","  deleated susseccesfully");

           //  db.execSQL("delete from "+ Myhelper.TABLE_NAME+" where " + Myhelper.CLASS_ + " = '" +classname +");


        } catch (Exception e) {
            Toast.makeText(helper.context, e + "", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    public void removeAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            db.execSQL("DELETE FROM " + Myhelper.TABLE_NAME);
        }catch(Exception e){
            Toast.makeText(helper.context,e+"", Toast.LENGTH_SHORT).show();
        }finally{
            db.close();
        }
    }

    /*public void bunkAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " + Myhelper.TABLE_NAME + " SET " + Myhelper.TOTAL + " = " + Myhelper.TOTAL + " +1 ");
        }catch(Exception e){
            Toast.makeText(helper.context,e+"", Toast.LENGTH_SHORT).show();
        }finally{
            db.close();
        }


    } */


     /*
    public void attendAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " + Myhelper.TABLE_NAME + " SET " + Myhelper.ATTEND + " = " + Myhelper.ATTEND + " +1 ,"+ Myhelper.TOTAL + " = " + Myhelper.TOTAL+" +1 ");
        }catch(Exception e){
            Toast.makeText(helper.context,e+"", Toast.LENGTH_SHORT).show();
        }finally{
            db.close();
        }
    }
 */
    public void storeData(Data data) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Myhelper.CLASS_, data.name);
        cv.put(Myhelper.CLASS_TYPE, data.type);
        cv.put(Myhelper.TIMEI, data.time);
        cv.put(Myhelper.DATE, data.date);
        db.insert(Myhelper.TABLE_NAME, null, cv);
    }

    public ArrayList<Data> getAllData() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = { Myhelper.UID, Myhelper.CLASS_, Myhelper.CLASS_TYPE, Myhelper.TIMEI,Myhelper.DATE };
        Cursor cursor = db.query(Myhelper.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Data> list = new ArrayList<Data>();



        String classname="";
        String classtype ="";
        String time = null;
        String date = null;
        while (cursor.moveToNext()) {
            classname = cursor.getString(cursor.getColumnIndex(Myhelper.CLASS_));
        classtype = cursor.getString(cursor.getColumnIndex(Myhelper.CLASS_TYPE));
            time = cursor.getString(cursor.getColumnIndex(Myhelper.TIMEI));
            date=cursor.getString(cursor.getColumnIndex(Myhelper.DATE));
            list.add(new Data(classname, classtype,date,time ));
        }
        return list;
    }

    static class Myhelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "Table1";
        private static final String DATABASE_NAME = "MyDatabase";
        private static final String UID = "_id";
        private static final String CLASS_ = "name";
        private static final String CLASS_TYPE = "type";
        private static final String DATE = "date";
        private static final String TIMEI = "time";

        private static final String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " "
                + "INTEGER PRIMARY KEY ," + CLASS_ + " VARCHAR(20), " + CLASS_TYPE + " VARCHAR(20), " +TIMEI + " TEXT,"    + DATE
                + " TEXT );";
        private static final String DROP_TABLE = "DROP TABLE IF NOT EXISTS " + TABLE_NAME;
//autoincremenr

        private Context context;

        public Myhelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_DATABASE);
            } catch (Exception e) {
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
            }
        }
    }
}
