package myapp.com.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ankit on 14/4/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyNotepad.db";
    public static final String NOTEPAD_TABLE_NAME = "notepad";
    public static final String NOTEPAD_COLUMN_ID = "id";
    public static final String NOTEPAD_COLUMN_TITLE = "title";
    public static final String NOTEPAD_COLUMN_MSG = "msg";


    public DBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table notepad"+"(id integer primary key,title text,msg text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notepad");
        onCreate(db);
    }

    public boolean insertNotes(String title, String msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("msg",msg);
        db.insert("notepad",null,contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from notepad where id="+id+" ",null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NOTEPAD_TABLE_NAME);
        return numRows;
    }

    public boolean updateNotes(Integer id, String title, String msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("msg",msg);
        db.update("notepad",contentValues,"id = ?",new String[] { Integer.toString(id)});
        return true;
    }

    public ArrayList<String> getAllNotes()
    {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from notepad",null);
        res.moveToFirst();

        while (res.isAfterLast()==false) {
            array_list.add(res.getString(res.getColumnIndex(NOTEPAD_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }
}
