package app.stock.ikshwaku.stalkmarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ikshwaku on 12/04/15.
 */
public class DB extends SQLiteOpenHelper{
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE stocklist ( symbol TEXT NOT NULL" +
                ",name TEXT NOT NULL,exchsym TEXT NOT NULL,price DOUBLE,curr TEXT)");
        String TAG = "MyActivity";
        Log.v(TAG,"creating db...");


    }
    public void addStock(String symbol,String name,String exchsym,Double price,String curr){
         String TAG = "MyActivity";
        ContentValues value=new ContentValues();
        value.put("symbol",symbol);
        value.put("name",name);
        value.put("exchsym",exchsym);
        value.put("price",price);
        value.put("curr",curr);
        SQLiteDatabase db1=this.getWritableDatabase();
        String query="Select * FROM stocklist"; //WHERE symbol = \""+symbol+"\"";

        Cursor cursor=db1.rawQuery(query,null);
        if (!cursor.moveToFirst()){
            Log.v(TAG,String.valueOf(cursor.getCount()));
            db1.replace("stocklist", null, value);
        }
        db1.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i2) {

    }
}
