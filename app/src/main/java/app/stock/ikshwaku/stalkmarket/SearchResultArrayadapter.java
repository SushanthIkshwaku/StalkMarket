package app.stock.ikshwaku.stalkmarket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ikshwaku on 12/04/15.
 */
public class SearchResultArrayadapter extends ArrayAdapter<String> {
    private final Context context;

    int ViewResourceId;
    private static final String TAG = "MyActivity-ArrayAdapter";
    private JSONArray searchJsonArray=null;




    public SearchResultArrayadapter(Context context,int ViewResourceId, JSONArray searchJsonArray){
        super(context, ViewResourceId,new String[searchJsonArray.length()]);
        this.context=context;
        this.searchJsonArray=searchJsonArray;
        this.ViewResourceId=ViewResourceId;
       /* try {
            this.searchJsonArray=new JSONArray(this.result);
            Log.v(TAG, searchJsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        //reuse vi
        RowHolder holder=null;

        if(rowView==null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            rowView = inflater.inflate(ViewResourceId,parent, false);
            holder=new RowHolder();
            holder.cmpname=(TextView) rowView.findViewById(R.id.cmpname);
            //holder.cmpname.setTypeface(Typeface.defaultFromStyle(BOLD));
            holder.textName=(TextView) rowView.findViewById(R.id.tickername);
            holder.exch=(TextView) rowView.findViewById(R.id.exchname);
            holder.type=(TextView) rowView.findViewById(R.id.typename);
            holder.exchname=(TextView) rowView.findViewById(R.id.exchnamefull);
            rowView.setTag(holder);
        }

        else{
            holder=(RowHolder)rowView.getTag();
        }
        try {
            JSONObject searchElement=searchJsonArray.getJSONObject(position);
            Log.v(TAG, searchElement.toString());

            //holder.cmpname.setText(searchElement.getString("name"));
            //holder.cmpname.setTypeface(Typeface.defaultFromStyle(BOLD));
            SpannableString spanString = new SpannableString(searchElement.getString("name"));
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            holder.cmpname.setText(spanString);
            holder.textName.setText(searchElement.getString("symbol"));
            holder.exch.setText(searchElement.getString("exch"));
            holder.type.setText(searchElement.getString("typeDisp"));
            holder.exchname.setText(searchElement.getString("exchDisp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }




        return rowView;
    }
    static class RowHolder{
        TextView cmpname;
        TextView textName;
        TextView exch;
        TextView type;
        TextView exchname;
    }
}
