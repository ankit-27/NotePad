package myapp.com.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ankit on 14/4/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyNotepad";
    public static final String NOTEPAD_TABLE_NAME = "notepad";
    public static final String NOTEPAD_COLUMN_TITLE = "title";
    public static final String NOTEPAD_COLUMN_MSG = "msg";


    public DBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + NOTEPAD_TABLE_NAME + "(" + NOTEPAD_COLUMN_TITLE +
                " TEXT PRIMARY KEY," + NOTEPAD_COLUMN_MSG + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notepad");
        onCreate(db);
    }

    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS notepad");
        onCreate(db);
    }

    public boolean insertNotes(String titl, String ms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTEPAD_COLUMN_TITLE,titl);
        contentValues.put(NOTEPAD_COLUMN_MSG,ms);
        db.insert(NOTEPAD_TABLE_NAME,null,contentValues);
        return true;
    }

    public String getData(String t) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ NOTEPAD_TABLE_NAME + " where " + NOTEPAD_COLUMN_TITLE + "="+"'"+t+"'"+" "
                ,null);
        String a = res.getString(res.getColumnIndex(NOTEPAD_COLUMN_MSG));
        return a;
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

    public ArrayList<String> getAllMsg(){
        ArrayList<String> msg_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from notepad",null);
        res.moveToFirst();

        while (res.isAfterLast()==false){
            msg_list.add(res.getString(res.getColumnIndex(NOTEPAD_COLUMN_MSG)));
            res.moveToNext();
        }
        return msg_list;
    }
}
