package com.sarthak.medopedia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.sarthak.medopedia.pojos.Alarm;

import java.util.ArrayList;
import java.util.Objects;

public class AlarmDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_alarm";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "alarm";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_HOUR = "hour";
    private static final String COLUMN_MINUTE = "minute";
    Context context;
    SQLiteDatabase sqLiteDatabase;

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        sqLiteDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER primary key autoincrement, " +
                COLUMN_NAME + " VARCHAR," +
                COLUMN_HOUR + " INTEGER," +
                COLUMN_MINUTE + " INTEGER )";

        sqLiteDatabase.execSQL(query);
        //Toast.makeText(context, "OnCreate", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public void addAlarm(String name, int hour, int minute) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_HOUR, hour);
        contentValues.put(COLUMN_MINUTE, minute);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Alarm> getAlarms() {
        ArrayList<Alarm> alarms = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_HOUR, COLUMN_MINUTE};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int hour = cursor.getInt(cursor.getColumnIndex(COLUMN_HOUR));
                int minute = cursor.getInt(cursor.getColumnIndex(COLUMN_MINUTE));
                Alarm alarm = new Alarm(name, hour, minute);
                alarms.add(alarm);
            } while (cursor.moveToNext());
        }
        Objects.requireNonNull(cursor).close();
        return alarms;
    }
}
