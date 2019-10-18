package kd.ca.awsmacapstoneproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper
{



    public Helper(Context context)
    {
        super(context, "DBListing", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String createTable = " create table tblList(ID INTEGER PRIMARY KEY AUTOINCREMENT, listADDRESS STRING, listDATE STRING); ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public  void  insertData(String xx, String yy)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String createTable = "insert into tblList(listADDRESS,listDATE) values('" + xx + "','" + yy + "');";
        db.execSQL(createTable);

    }

    public String selectData1(long yy)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor y = db.rawQuery("select * from tblList where listADDRESS =" + yy + ";", null);

        String x="";
        if (y.moveToFirst()) {
            x = y.getString(y.getColumnIndex("listADDRESS"));
        }
        return x;
    }

    public String selectData(String yy){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor y = db.rawQuery("select * from tblList where listADDRESS ='" + yy + "';", null);
        //Cursor y = db.rawQuery("select * from tblList;", null);
        String x="";
        while (y.moveToNext()){
            x += "HUMIDITY: " + y.getString(y.getColumnIndex("listDATE"));
            x+= "%\nDATE: " + y.getString(y.getColumnIndex("listADDRESS"));;
            x+="\n\n";
        }
        //if (y.moveToNext()) {
        //x += y.getString(y.getColumnIndex("listDATE"));
        //}
        return x;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM tblList",null);
        return res;
    }
}
