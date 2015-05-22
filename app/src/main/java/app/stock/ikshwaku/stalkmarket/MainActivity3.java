package app.stock.ikshwaku.stalkmarket;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity3 extends Activity {
    ArrayAdapter<news> adapter;
    private static final String TAG = "My3Activity";
    String TAG1="post";
    private ListView listView1;
    List<news> newsList=null;
    newslistadapter adapter1;
    String symbol;
    TextView cmpname;
    TextView textName;
    TextView exch;
    TextView price;
    TextView cur;
    TextView chg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);
        news n1=new news();
        n1.setUrl("url1");
        n1.setTitle("title");
        List<news> newslist1 = new ArrayList<news>();
        newslist1.add(n1);

        Intent i=getIntent();
        symbol=i.getStringExtra("symbol");
        Log.v(TAG, symbol);
        listView1=(ListView)findViewById(R.id.listnews);

        new getNews().execute();

        SQLiteDatabase db1 = new TestOpenHelper(this).getWritableDatabase();
        String query="Select * FROM stocklist WHERE symbol = \""+symbol+"\"";
         Cursor cursor=db1.rawQuery(query,null);
        if (cursor.moveToFirst()){
            cmpname=(TextView) findViewById(R.id.cmpname2);
            textName=(TextView) findViewById(R.id.tickername2);
            exch=(TextView) findViewById(R.id.exchname2);
            price=(TextView)findViewById(R.id.typename2);
            chg=(TextView)findViewById(R.id.chg2);
            cur=(TextView)findViewById(R.id.curr2);

            SpannableString spanString = new SpannableString(cursor.getString(1));
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0,spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cmpname.setText(spanString);
            // holder.cmpname.setText(searchElement.name);

            if(cursor.getString(5).charAt(0)=='-') {

                SpannableString spanString2 = new SpannableString(cursor.getString(5));
                spanString2.setSpan(new ForegroundColorSpan(Color.RED), 0,spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                chg.setText(spanString2);

                if ((cursor.getString(3).indexOf('.') != -1) && (cursor.getString(3).length() > (cursor.getString(3).indexOf('.') + 3))) {

                    SpannableString spanString1 = new SpannableString(cursor.getString(3).substring(0, cursor.getString(3).indexOf('.') + 3));
                    spanString1.setSpan(new ForegroundColorSpan(Color.RED), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    price.setText(spanString1);
                } else {

                    SpannableString spanString1 = new SpannableString(cursor.getString(3));
                    spanString1.setSpan(new ForegroundColorSpan(Color.RED), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    price.setText(spanString1);
                }
            }else{
                if(cursor.getString(5).charAt(0)=='+'){
                    SpannableString spanString2 = new SpannableString(cursor.getString(5));
                    spanString2.setSpan(new ForegroundColorSpan(Color.parseColor("#33CC33")), 0,spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    chg.setText(spanString2);
                    if ((cursor.getString(3).indexOf('.') != -1) && (cursor.getString(3).length() > (cursor.getString(3).indexOf('.') + 3))) {

                        SpannableString spanString1 = new SpannableString(cursor.getString(3).substring(0, cursor.getString(3).indexOf('.') + 3));
                        spanString1.setSpan(new ForegroundColorSpan(Color.parseColor("#33CC33")), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        price.setText(spanString1);
                    } else {

                        SpannableString spanString1 = new SpannableString(cursor.getString(3));
                        spanString1.setSpan(new ForegroundColorSpan(Color.parseColor("#33CC33")), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        price.setText(spanString1);
                    }
                }else{
                    SpannableString spanString2 = new SpannableString(cursor.getString(5));
                    spanString2.setSpan(new ForegroundColorSpan(Color.GRAY), 0,spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    chg.setText(spanString2);
                    if ((cursor.getString(3).indexOf('.') != -1) && (cursor.getString(3).length() > (cursor.getString(3).indexOf('.') + 3))) {

                        SpannableString spanString1 = new SpannableString(cursor.getString(3).substring(0, cursor.getString(3).indexOf('.') + 3));
                        spanString1.setSpan(new ForegroundColorSpan(Color.GRAY), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        price.setText(spanString1);
                    } else {

                        SpannableString spanString1 = new SpannableString(cursor.getString(3));
                        spanString1.setSpan(new ForegroundColorSpan(Color.GRAY), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        price.setText(spanString1);
                    }
                }
            }
            SpannableString spanString2 = new SpannableString(cursor.getString(0));
           // spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString2.setSpan(new ForegroundColorSpan(Color.WHITE), 0,spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cmpname.setText(spanString);
            textName.setText(spanString2);
            SpannableString spanString3 = new SpannableString(cursor.getString(2));
           // spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString3.setSpan(new ForegroundColorSpan(Color.WHITE), 0,spanString3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            exch.setText(spanString3);
            //holder.price.setText(searchElement.price);
            SpannableString spanString4 = new SpannableString(cursor.getString(4));
            //spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString4.setSpan(new ForegroundColorSpan(Color.WHITE), 0,spanString4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cur.setText(spanString4);

            Log.v(TAG,String.valueOf(cursor.getCount()));

        }
        db1.close();

        /*adapter1=new newslistadapter(MainActivity3.this,R.layout.newslayout,newslist1);
        listView1.setAdapter(adapter1);

        /*adapter=new ArrayAdapter<news>(MainActivity3.this,
                R.layout.newslayout,newslist1);
        listView1.setAdapter(adapter);
        /*Intent intentService =new Intent(getApplicationContext(),
                NewsIntentService.class);
        intentService.putExtra("symbol",symbol);
        startService(intentService);*/
        //new getNews().execute();
        /*Log.v(TAG1, "postExec");
        Log.v(TAG1,newsList.get(0).getTitle().toString());*/



    }

    private class getNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet("http://finance.yahoo.com/rss/headline?s=" + symbol);
            try {
                Log.v(TAG,"background");
                HttpResponse response = httpClient.execute(httppost);
                InputStream is = response.getEntity().getContent();

                XMLPullHandlerNews parser = new XMLPullHandlerNews();
                newsList = parser.parse(is);
                Log.v(TAG, "postExec");
                //listView1=(ListView)findViewById(R.id.listnews);
                //ArrayAdapter<news>
                //adapter=new ArrayAdapter<news>(MainActivity3.this,
                //        R.layout.newslayout,newsList);
                //listView1.setAdapter(adapter);
                //Log.v(TAG, newsList.get(0).toString());
                //adapter1=new newslistadapter(MainActivity3.this,R.layout.newslayout,newsList);
                //listView1.setAdapter(adapter1);

                return null;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void voids){
            adapter1=new newslistadapter(MainActivity3.this,R.layout.newslayout,newsList);
            //listView1.setAdapter(adapter1);
            listView1.setAdapter(adapter1);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String url=newsList.get(i).getUrl();
                    Intent i1=new Intent(MainActivity3.this,MainActivity4.class);
                    i1.putExtra("url",url);
                    startActivity(i1);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
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
