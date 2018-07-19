package com.sarthak.medopedia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.sarthak.medopedia.pojos.Medicine;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    /* DBHelper dbHelper;
     Medicine medicine;*/
    private static final String DATABASE_NAME = "my_medicine";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "medicine";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    Context context;
    SQLiteDatabase sqLiteDatabase;
    private ArrayList<Medicine> list;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        sqLiteDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER primary key autoincrement, " +
                COLUMN_NAME + " VARCHAR )";
        sqLiteDatabase.execSQL(query);
        //Toast.makeText(context, "OnCreate", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //Toast.makeText(context, "OnUpgrade", Toast.LENGTH_SHORT).show();

    }

    public void addMedicine(String name) {


        //Todo check such that a medicine can be added once

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_NAME + "=?";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, new String[]{name});
        int count = cursor.getCount();
        cursor.close();
        if (count == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, name);
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            Toast.makeText(context, "Medicine added", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, "Already present", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Medicine> showAllMedicine() {
        list = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_NAME};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                //int id=cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                //Medicine medicine=new Medicine(id, name);
                Medicine medicine = new Medicine(name);
                list.add(medicine);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

  /*  public boolean medname(int pos,String namepass)
    {
        Cursor cursor= sqLiteDatabase.rawQuery("select " + COLUMN_NAME + " from " + TABLE_NAME + " where " + COLUMN_ID + " = " + pos,null);
        String name= cursor.toString();
        if(name.equals(namepass)){
            return false;
        }
        else
            return true;
    }*/

}
