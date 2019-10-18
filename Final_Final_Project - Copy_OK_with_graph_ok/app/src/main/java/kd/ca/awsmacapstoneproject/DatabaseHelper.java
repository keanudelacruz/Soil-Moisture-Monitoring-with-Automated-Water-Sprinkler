package kd.ca.awsmacapstoneproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "record.db";
    public static final String TABLE_NAME = "tbl_record";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "HUMIDITY";
    public static final String COL_3 = "DATE_TIME";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,HUMIDITY TEXT, DATE_TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //public boolean insertData(String humidity,String datetime)
    public boolean insertData(String humidity,Long datetime)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, humidity);
        contentValues.put(COL_3, datetime);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return  false;
        }
        else {
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME,null);
        return res;
    }
    public Cursor getAllData1(){
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor res = db.rawQuery("Select "+ COL_1 + "," + COL_2 + "," + COL_3 +" from " + TABLE_NAME + ";",null);
        Cursor res = db.rawQuery("Select " + COL_2 + "," + COL_3 +" from " + TABLE_NAME + " where ID=1",null);
        return res;
    }
}
