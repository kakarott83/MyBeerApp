package e.lm280.myapplication.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.CursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class DatenbankManager extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myDatenbank.db";
    private static final String TABLE_USER = "user";
    private static final String TABLE_ITEM = "item";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SECTION = "section";
    private static final String KEY_DATE = "date";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERID = "userid";

    private static final String USERTABLE_CREATE =
            "CREATE TABLE " + TABLE_USER + " (_" +
            KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_SECTION + " TEXT NOT NULL)";

    private static final String ITEMTABLE_CREATE =
            "CREATE TABLE " + TABLE_ITEM + " (_" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_DATE + " TEXT NOT NULL, " +
                    KEY_USERNAME + " TEXT NOT NULL, " +
                    KEY_USERID + " TEXT NOT NULL)";

    private static final String USERTABLE_DROP =
            "DROP TABLE IF EXISTS " + TABLE_USER;

    private static final String ITEMTABLE_DROP =
            "DROP TABLE IF EXISTS " + TABLE_ITEM;

    public static final String GET_ALL_USER =
            "SELECT _id, name, section FROM " + TABLE_USER;

    public static final String GET_ALL_ITEMS =
            "SELECT _id, date, userid, username FROM " + TABLE_ITEM;

    public static final String GET_ALL_ITEMS_BY_USER =
            "SELECT _id, date, userid, username FROM " + TABLE_ITEM + " WHERE username = ?";

    public DatenbankManager(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( USERTABLE_CREATE );
        db.execSQL( ITEMTABLE_CREATE );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( USERTABLE_DROP );
        db.execSQL( ITEMTABLE_DROP );
        onCreate( db );
    }

    public void insertUser(String name, String section){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( KEY_NAME,name );
        values.put( KEY_SECTION,section );
        long newRowId = db.insert( TABLE_USER,null,values );
        db.close();
    }

    public void insertItem(String name,String id, String date ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( KEY_USERNAME,name );
        values.put( KEY_USERID,id );
        values.put( KEY_DATE,date );
        long newRowId = db.insert( TABLE_ITEM,null,values );
        db.close();
    }

    public void deleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( TABLE_USER,"_" + KEY_ID + " = ?",new String[]{String.valueOf( userid )} );
    }

    public void deleteDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( TABLE_USER,null,null );
        db.delete( TABLE_ITEM,null,null );
    }

    public ArrayList<HashMap<String,String>> GetUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> userList = new ArrayList<>(  );
        Cursor cursor = db.rawQuery( GET_ALL_USER,null );
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>(  );
            user.put( "_id",cursor.getString( cursor.getColumnIndex( "_"+ KEY_ID ) ) );
            user.put( "name",cursor.getString( cursor.getColumnIndex( KEY_NAME ) ) );
            user.put( "section",cursor.getString( cursor.getColumnIndex( KEY_SECTION ) ) );
            userList.add( user );
        }

        return userList;

    }

    public ArrayList<HashMap<String,String>> GetItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> itemsList = new ArrayList<>(  );
        Cursor cursor = db.rawQuery( GET_ALL_ITEMS,null );
        while (cursor.moveToNext()){
            HashMap<String,String> items = new HashMap<>(  );
            items.put( "_id",cursor.getString( cursor.getColumnIndex( "_" + KEY_ID ) ) );
            items.put( "date",cursor.getString( cursor.getColumnIndex( KEY_DATE ) ) );
            items.put( "username",cursor.getString( cursor.getColumnIndex( KEY_USERNAME ) ) );
            items.put( "userid",cursor.getString( cursor.getColumnIndex( KEY_USERID ) ) );
            itemsList.add( items );
        }

        return itemsList;
    }

    public ArrayList<HashMap<String,String>> GetItemsByUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> itemsList = new ArrayList<>(  );
        Cursor cursor = db.rawQuery( GET_ALL_ITEMS_BY_USER,new String[]{username} );
        while (cursor.moveToNext()){
            HashMap<String,String> items = new HashMap<>(  );
            items.put( "_id",cursor.getString( cursor.getColumnIndex( "_" + KEY_ID ) ) );
            items.put( "date",cursor.getString( cursor.getColumnIndex( KEY_DATE ) ) );
            items.put( "username",cursor.getString( cursor.getColumnIndex( KEY_USERNAME ) ) );
            items.put( "userid",cursor.getString( cursor.getColumnIndex( KEY_USERID ) ) );
            itemsList.add( items );
        }

        return itemsList;
    }
}
