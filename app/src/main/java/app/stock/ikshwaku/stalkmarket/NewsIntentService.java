package app.stock.ikshwaku.stalkmarket;

import android.app.IntentService;
import android.content.Intent;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class NewsIntentService extends IntentService {

    private static final String TAG = "My3Activity";
    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String symbol=intent.getStringExtra("symbol");

        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httppost=new HttpPost("http://finance.yahoo.com/rss/headline?s="+symbol);
        try {
            HttpResponse response = httpClient.execute(httppost);
            InputStream is = response.getEntity().getContent();
            /*InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String result;
            StringBuffer sb= new StringBuffer();
            while((result= br.readLine())!=null){
                sb.append(result);
            }
            result=sb.toString();
            Log.v(TAG, result);*/
            List<news> newsList=null;
            XMLPullHandlerNews parser=new XMLPullHandlerNews();
            newsList=parser.parse(is);


            /*JSONObject jresponse=new JSONObject(result);
            JSONArray resultset= jresponse.getJSONObject("list").getJSONArray("resources");
            Log.v(TAG,resultset.getJSONObject(0).getJSONObject("resource").toString());
*/
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity2.QuoteReceiver.QUOTE_RESPONSE);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
           // broadcastIntent.putExtra("newsList",newsList );
            //broadcastIntent.putExtra("symbol",quote);
            sendBroadcast(broadcastIntent);

        }catch (ClientProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

}