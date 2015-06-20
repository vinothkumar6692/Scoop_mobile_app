package com.example.vinoth.test1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Harshi on 5/5/15.
 */
public class DBConnection1 extends SQLiteOpenHelper {

    public DBConnection1(Context c)
    {
        super(c,"newsdb",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public Cursor getvalues(String string)
    {
        Cursor c= null;
        if(string.equalsIgnoreCase("cnn") || string.equalsIgnoreCase("bbc") || string.equalsIgnoreCase("nytimes"))
        {
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("select * from news1",null);
        }
        if(string.equalsIgnoreCase("engadget"))
        {
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("select * from engadget",null);
        }
        if(string.equalsIgnoreCase("verge"))
        {
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("select * from verge",null);
        }
        if(string.equalsIgnoreCase("usa"))
        {
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("select * from usa",null);
        }
        return c;
    }

    public Cursor getDetails(String t, String source)
    {
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if(source.equalsIgnoreCase("cnn") || source.equalsIgnoreCase("bbc") || source.equalsIgnoreCase("nytimes"))
        {
            c = db.rawQuery("select * from news1 where title='"+t+"'",null);
        }
        if(source.equalsIgnoreCase("engadget"))
        {
            c = db.rawQuery("select * from engadget where title='"+t+"'",null);
        }
        if(source.equalsIgnoreCase("verge"))
        {
            c = db.rawQuery("select * from verge where title='"+t+"'",null);
        }
        if(source.equalsIgnoreCase("usa"))
        {
            c = db.rawQuery("select * from usa where title='"+t+"'",null);
        }
        return c;

    }
}
