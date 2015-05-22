package app.stock.ikshwaku.stalkmarket;

/**
 * Created by ikshwaku on 12/04/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TestOpenHelper extends SQLiteOpenHelper {

    TestOpenHelper(Context context){
        super(context,"mydb.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase database){
        //database.execSQL("create table test_1 " + " (field1 integer primary key, field2 integer)");
        database.execSQL("CREATE TABLE stocklist ( symbol TEXT NOT NULL" +
                ",name TEXT NOT NULL,exchsym TEXT NOT NULL,price DOUBLE,curr TEXT NOT NULL,chg TEXT NOT NULL)");
        String TAG = "MyActivity";
        Log.v(TAG, "creating db...");
    }
    public void addStock(String symbol,String name,String exchsym,Double price,String curr){
        String TAG = "MyActivity";
        ContentValues value=new ContentValues();
        value.put("symbol",symbol);
        value.put("name",name);
        value.put("exchsym",exchsym);
        value.put("price",price);
        value.put("curr",curr);
        value.put("chg","N/A");
        SQLiteDatabase db1=this.getWritableDatabase();
        String query="Select * FROM stocklist WHERE symbol = \""+symbol+"\"";

        Cursor cursor=db1.rawQuery(query,null);
        if (!cursor.moveToFirst()){
            Log.v(TAG,String.valueOf(cursor.getCount()));
            db1.replace("stocklist", null, value);
        }
        db1.close();
    }

    public void addPrice(String symbol,String price,String curr,String change){
        String TAG = "MyActivity";
        ContentValues value=new ContentValues();
        value.put("symbol",symbol);
        value.put("price",price);
        value.put("curr",curr);
        value.put("chg",change);
        SQLiteDatabase db1=this.getWritableDatabase();
        String query="Select * FROM stocklist WHERE symbol = \""+symbol+"\"";
        Cursor cursor=db1.rawQuery(query,null);
        if (cursor.moveToFirst()){
            Log.v(TAG,String.valueOf(cursor.getCount()));
            value.put("name",cursor.getString(1));
            value.put("exchsym",cursor.getString(2));
            db1.update("stocklist",value,"symbol=?",new String[]{symbol});
        }
        db1.close();

    }
    public void onUpgrade (SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("drop table if exists test1");
        onCreate(database);
    }
}