package app.stock.ikshwaku.stalkmarket;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class quoteIntentService extends IntentService {

    private static final String TAG = "MyEXCActivity";
    private static final String TAG1 = "My1EXCActivity";
    public quoteIntentService() {
        super("quoteIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String quote = intent.getStringExtra("quote");
        String exch = intent.getStringExtra("exch");
        HttpClient httpClient = new DefaultHttpClient();
        if (exch.equalsIgnoreCase("bse") || exch.equalsIgnoreCase("nsi")) {


            HttpGet httppost = new HttpGet("http://finance.yahoo.com/webservice/v1/symbols/" + quote + "/quote?format=json");
            try {
                HttpResponse response = httpClient.execute(httppost);
                InputStream is = response.getEntity().getContent();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String result;
                StringBuffer sb = new StringBuffer();
                while ((result = br.readLine()) != null) {
                    sb.append(result);
                }
                result = sb.toString();
                Log.v(TAG, result);
                JSONObject jresponse = new JSONObject(result);
                JSONArray resultset = jresponse.getJSONObject("list").getJSONArray("resources");
                Log.v(TAG, resultset.getJSONObject(0).getJSONObject("resource").toString());
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivity2.QuoteReceiver.QUOTE_RESPONSE);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                broadcastIntent.putExtra("price", resultset.getJSONObject(0).getJSONObject("resource").getJSONObject("fields").getString("price"));
                broadcastIntent.putExtra("symbol", quote);
                broadcastIntent.putExtra("curr","Rs");
                broadcastIntent.putExtra("change","N/A");
                sendBroadcast(broadcastIntent);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {

                HttpGet httppost = new HttpGet("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" + quote + "%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json");
                HttpResponse response = httpClient.execute(httppost);
                InputStream is = response.getEntity().getContent();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String result;
                StringBuffer sb = new StringBuffer();
                while ((result = br.readLine()) != null) {
                    sb.append(result);
                }
                result = sb.toString();
                Log.v(TAG, result);
                JSONObject jresponse =new JSONObject(result).getJSONObject("query").getJSONObject("results").getJSONObject("quote");
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivity2.QuoteReceiver.QUOTE_RESPONSE);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                broadcastIntent.putExtra("price",jresponse.getString("LastTradePriceOnly"));
                Log.v(TAG, jresponse.getString("LastTradePriceOnly"));
                broadcastIntent.putExtra("symbol", quote);
                broadcastIntent.putExtra("curr",jresponse.getString("Currency"));
                broadcastIntent.putExtra("change",jresponse.getString("PercentChange"));
                sendBroadcast(broadcastIntent);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
