package app.stock.ikshwaku.stalkmarket;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;


public class MainActivity2 extends Activity {

    SearchView search;
    private static final String TAG = "MyActivity";
    private static final String TAG1 = "My1Activity";
    private tickerSearchReceiver receiver;
    private QuoteReceiver receiver1;
    private ListView listView1;
    JSONArray searchJsonArray;
    libraryArrayAdapter adapter;
    TestOpenHelper db=new TestOpenHelper(MainActivity2.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        IntentFilter filter = new IntentFilter(tickerSearchReceiver.SEARCH_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new tickerSearchReceiver();
        registerReceiver(receiver, filter);

        IntentFilter filter1 = new IntentFilter(QuoteReceiver.QUOTE_RESPONSE);
        filter1.addCategory(Intent.CATEGORY_DEFAULT);
        receiver1 = new QuoteReceiver();
        registerReceiver(receiver1, filter1);

        reloadAllData();
        updateData();

        search=(SearchView) findViewById(R.id.searchView1);
        SpannableString spanString = new SpannableString("Search Stock");
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0,spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        search.setQueryHint(spanString);

        search.setIconifiedByDefault(false);
        search.clearFocus();

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

              //  Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                //        Toast.LENGTH_SHORT).show();
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                query=query.replaceAll(" ","%20");
               // Toast.makeText(getBaseContext(), query,
                 //       Toast.LENGTH_SHORT).show();
                Log.v(TAG,query);
                Intent intentService =new Intent(getApplicationContext(),
                        IntentServiceTicker.class);
                intentService.putExtra("search",query);
                startService(intentService);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                	//Toast.makeText(getBaseContext(), newText,
                //Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    private void updateData(){
        SQLiteDatabase db2 = new TestOpenHelper(this).getWritableDatabase();
        String query1="SELECT *FROM stocklist ORDER BY name ASC";
        Cursor stocklistquery=db2.rawQuery(query1, null);
        Log.v(TAG,stocklistquery.toString());
        libraryDataType libraryArray[]=new libraryDataType[stocklistquery.getCount()];
        if(stocklistquery.moveToFirst()) {
            int j = 0;
            do {
                libraryArray[j] = new libraryDataType(stocklistquery.getString(1), stocklistquery.getString(0),
                        stocklistquery.getString(2), String.valueOf(stocklistquery.getDouble(3)),stocklistquery.getString(4),stocklistquery.getString(5));
                Log.v(TAG1, stocklistquery.getString(0));
                Log.v(TAG1, stocklistquery.getString(1));
                Log.v(TAG1, stocklistquery.getString(2));
                Log.v(TAG1, String.valueOf(stocklistquery.getDouble(3)));
                Intent intentService = new Intent(getApplicationContext(),
                        quoteIntentService.class);
                intentService.putExtra("quote", stocklistquery.getString(1));
                intentService.putExtra("exch", stocklistquery.getString(2));
                startService(intentService);

                j++;
            } while (stocklistquery.moveToNext());

        } stocklistquery.close();
            db2.close();
        reloadAllData();
    }

    private void reloadAllData(){
        TextView t1=(TextView)findViewById(R.id.library);
        t1.setVisibility(View.VISIBLE);
        SQLiteDatabase db2 = new TestOpenHelper(this).getWritableDatabase();
        String query1="SELECT *FROM stocklist ORDER BY name ASC";
        Cursor stocklistquery=db2.rawQuery(query1, null);
        Log.v(TAG,stocklistquery.toString());
        final libraryDataType libraryArray[]=new libraryDataType[stocklistquery.getCount()];
        if(stocklistquery.moveToFirst()){
            int j=0;
            do{
                libraryArray[j]=new libraryDataType(stocklistquery.getString(1),stocklistquery.getString(0),
                        stocklistquery.getString(2),String.valueOf(stocklistquery.getDouble(3)),stocklistquery.getString(4),stocklistquery.getString(5));
                /*Log.v(TAG,stocklistquery.getString(0));
                Log.v(TAG,stocklistquery.getString(1));
                Log.v(TAG,stocklistquery.getString(2));
                Log.v(TAG,String.valueOf(stocklistquery.getDouble(3)));
*/
                j++;
            }while(stocklistquery.moveToNext());

            adapter=new libraryArrayAdapter(MainActivity2.this,
                    R.layout.layout,
                    libraryArray);
            listView1= (ListView)findViewById(R.id.listresult);
            listView1.setAdapter(adapter);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String symbol = libraryArray[i].symbol;
                    Intent news = new Intent(MainActivity2.this, MainActivity3.class);
                    news.putExtra("symbol", symbol);
                    startActivity(news);
                }
            });
            listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String symbol = libraryArray[i].symbol;
                    SQLiteDatabase db1 = new TestOpenHelper(MainActivity2.this).getWritableDatabase();
                    db1.delete("stocklist", "exchsym=?", new String[]{symbol});
                    db1.close();
                    return false;
                }
            });

        }
            stocklistquery.close();
        db2.close();
    }

    public class tickerSearchReceiver extends BroadcastReceiver {

        public static final String SEARCH_RESPONSE = "app.stock.ikshwaku.stocknewsapp.intent.action.SEARCH_RESPONSE";
        @Override
        public void onReceive(Context context, final Intent intent) {
            String searchResult= intent.getStringExtra("searchResultJsonArray");
            Log.v(TAG1, searchResult);
            TextView t1=(TextView)findViewById(R.id.library);
            t1.setVisibility(View.GONE);
            try {
                searchJsonArray=new JSONArray(searchResult);
                SearchResultArrayadapter adapter=new SearchResultArrayadapter(MainActivity2.this,
                        R.layout.searchresultrow,
                        searchJsonArray);
                listView1= (ListView)findViewById(R.id.listresult);
                listView1.setAdapter(adapter);
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {

                            ImageView imgv=(ImageView)view.findViewById(R.id.img);
                            imgv.setImageResource(R.drawable.gdollar);
                            String ticker=searchJsonArray.getJSONObject(i).getString("symbol");
                           // Toast.makeText(getBaseContext(), ticker,
                             //       Toast.LENGTH_SHORT).show();
                            //TestOpenHelper db=new TestOpenHelper(MainActivity2.this);
                            if(searchJsonArray.getJSONObject(i).getString("exch").equalsIgnoreCase("bse")||searchJsonArray.getJSONObject(i).getString("exch").equalsIgnoreCase("nsi")){
                                db.addStock(ticker, searchJsonArray.getJSONObject(i).getString("name"),
                                        searchJsonArray.getJSONObject(i).getString("exch"), 0.0,"Rs");
                            }else {
                                db.addStock(ticker, searchJsonArray.getJSONObject(i).getString("name"),
                                        searchJsonArray.getJSONObject(i).getString("exch"), 0.0,"N/A");
                            }

                            Intent intentService =new Intent(getApplicationContext(),
                                    quoteIntentService.class);
                            intentService.putExtra("quote",ticker);
                            intentService.putExtra("exch",searchJsonArray.getJSONObject(i).getString("exch"));
                            startService(intentService);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public class QuoteReceiver extends BroadcastReceiver {

        public static final String QUOTE_RESPONSE = "app.stock.ikshwaku.stocknewsapp.intent.action.QUOTE_RESPONSE";
        @Override
        public void onReceive(Context context, Intent intent) {
            String price= intent.getStringExtra("price");
            String symbol=intent.getStringExtra("symbol");
            String curr=intent.getStringExtra("curr");
            String change=intent.getStringExtra("change");
            Log.v(TAG1, price);

            db.addPrice(symbol,price,curr,change);
            reloadAllData();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
