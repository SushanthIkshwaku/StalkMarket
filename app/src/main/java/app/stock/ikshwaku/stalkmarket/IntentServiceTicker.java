package app.stock.ikshwaku.stalkmarket;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class IntentServiceTicker extends IntentService {

    private static final String TAG = "MyActivity";
    public IntentServiceTicker() {
        super("IntentServiceTicker");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String search=intent.getStringExtra("search");

        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httppost=new HttpPost("http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="+search+"&callback=YAHOO.Finance.SymbolSuggest.ssCallback");
        try{
            HttpResponse response=httpClient.execute(httppost);
            InputStream is= response.getEntity().getContent();
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader br= new BufferedReader(isr);

            String result=br.readLine();
            Log.v(TAG, result.substring(39, result.length() - 1));
            JSONObject jresponse=new JSONObject(result.substring(39,result.length()-1));
            JSONArray resultset= jresponse.getJSONObject("ResultSet").getJSONArray("Result");
            //Log.v(TAG, resultset.toString());
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity2.tickerSearchReceiver.SEARCH_RESPONSE);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra("searchResultJsonArray", resultset.toString());
            sendBroadcast(broadcastIntent);
            /*int i=resultset.length();
            for(int j=0;j<i;j++){
                Log.v(TAG, resultset.getJSONObject(j).toString());
            }*/

        }catch (ClientProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

}
