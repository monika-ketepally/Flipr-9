package com.example.flipr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper  extends SQLiteOpenHelper {
    public static final String database_name = "flipr.db";
    public static final String column2 = "firstname";
    public static final String column3 = "lastname";
    public static final String column4 = "password";
    public static final String column5 = "email";
    public static final String column6 = "phonenumber";
    public static final String column1 = "id";
    public static final String col1 = "sendid";
    public static final String col2 = "sendto";
    public static final String col3 = "cc";
    public static final String col4 = "subject";
    public static final String col5 = "message";
    public static final String col6 = "email";

    public DataBaseHelper(@Nullable Context context) {
        super(context,database_name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT,lastname TEXT,password TEXT, email TEXT UNIQUE, phonenumber TEXT)");
        db.execSQL("CREATE TABLE mails (sendid INTEGER PRIMARY KEY AUTOINCREMENT, sendto TEXT,cc TEXT,subject TEXT, message TEXT,FOREIGN KEY(email) REFERENCES users(email))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS mails");
        onCreate(db);
    }
    public boolean insertdata(String fname,String lname,String pass,String em,String ph){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.column2,fname);
        cv.put(DataBaseHelper.column3,lname);
        cv.put(DataBaseHelper.column4,pass);
        cv.put(DataBaseHelper.column5,em);
        cv.put(DataBaseHelper.column6,ph);
        long id = db.insert("users",null,cv);
        if(id == -1){
            return false;
        }
        return true;
    }
    public boolean isValidLogin(String email,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from users where email = ? and password = ?",new String[]{email,pass});
        if(c.getCount()>0){
            return true;
        }
        return false;
    }
    public boolean googleCheck(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from users where email = ?",new String[]{email});
        if(c.getCount()>0){
            return true;
        }
        return false;
    }
    public boolean insertMail(String sendto,String cc,String sub,String msg,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.col2,sendto);
        cv.put(DataBaseHelper.col3,cc);
        cv.put(DataBaseHelper.col4,sub);
        cv.put(DataBaseHelper.col5,msg);
        cv.put(DataBaseHelper.col6,email);
        long id = db.insert("mails",null,cv);
        if(id == -1){
            return false;
        }
        return true;
    }
}
