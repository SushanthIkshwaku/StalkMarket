package app.stock.ikshwaku.stalkmarket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ikshwaku on 12/04/15.
 */
public class libraryArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    int ViewResourceId;
    private static final String TAG = "MyActivityLibrary-ArrayAdapter";
    private libraryDataType[] searchJsonArray=null;
    public libraryArrayAdapter(Context context,int ViewResourceId, libraryDataType[] searchJsonArray){
        super(context, ViewResourceId,new String[searchJsonArray.length]);
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
            holder.cmpname=(TextView) rowView.findViewById(R.id.cmpname1);
            holder.textName=(TextView) rowView.findViewById(R.id.tickername1);
            holder.exch=(TextView) rowView.findViewById(R.id.exchname1);
            holder.price=(TextView) rowView.findViewById(R.id.typename1);
            holder.chg=(TextView) rowView.findViewById(R.id.chg);
            holder.cur=(TextView) rowView.findViewById(R.id.curr);
            rowView.setTag(holder);
        }

        else{
            holder=(RowHolder)rowView.getTag();
        }
            libraryDataType searchElement=searchJsonArray[position];
            Log.v(TAG, searchElement.toString());
        SpannableString spanString = new SpannableString(searchElement.name);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new ForegroundColorSpan(Color.BLUE), 0,spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.cmpname.setText(spanString);
           // holder.cmpname.setText(searchElement.name);

        if(searchElement.change.charAt(0)=='-') {

            SpannableString spanString2 = new SpannableString(searchElement.change);
            spanString2.setSpan(new ForegroundColorSpan(Color.RED), 0,spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.chg.setText(spanString2);

            if ((searchElement.price.indexOf('.') != -1) && (searchElement.price.length() > (searchElement.price.indexOf('.') + 3))) {

                SpannableString spanString1 = new SpannableString(searchElement.price.substring(0, searchElement.price.indexOf('.') + 3));
                spanString1.setSpan(new ForegroundColorSpan(Color.RED), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.price.setText(spanString1);
            } else {
                holder.price.setText(searchElement.price);
                SpannableString spanString1 = new SpannableString(searchElement.price);
                spanString1.setSpan(new ForegroundColorSpan(Color.RED), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.price.setText(spanString1);
            }
        }else{
            if(searchElement.change.charAt(0)=='+'){
                SpannableString spanString2 = new SpannableString(searchElement.change);
                spanString2.setSpan(new ForegroundColorSpan(Color.parseColor("#33CC33")), 0,spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.chg.setText(spanString2);
                if ((searchElement.price.indexOf('.') != -1) && (searchElement.price.length() > (searchElement.price.indexOf('.') + 3))) {

                    SpannableString spanString1 = new SpannableString(searchElement.price.substring(0, searchElement.price.indexOf('.') + 3));
                    spanString1.setSpan(new ForegroundColorSpan(Color.parseColor("#33CC33")), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.price.setText(spanString1);
                } else {
                    holder.price.setText(searchElement.price);
                    SpannableString spanString1 = new SpannableString(searchElement.price);
                    spanString1.setSpan(new ForegroundColorSpan(Color.parseColor("#33CC33")), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.price.setText(spanString1);
                }
            }else{
                SpannableString spanString2 = new SpannableString(searchElement.change);
                spanString2.setSpan(new ForegroundColorSpan(Color.GRAY), 0,spanString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.chg.setText(spanString2);
                if ((searchElement.price.indexOf('.') != -1) && (searchElement.price.length() > (searchElement.price.indexOf('.') + 3))) {

                    SpannableString spanString1 = new SpannableString(searchElement.price.substring(0, searchElement.price.indexOf('.') + 3));
                    spanString1.setSpan(new ForegroundColorSpan(Color.GRAY), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.price.setText(spanString1);
                } else {
                    holder.price.setText(searchElement.price);
                    SpannableString spanString1 = new SpannableString(searchElement.price);
                    spanString1.setSpan(new ForegroundColorSpan(Color.GRAY), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.price.setText(spanString1);
                }
            }
        }
            holder.textName.setText(searchElement.symbol);
            holder.exch.setText(searchElement.exch);
            //holder.price.setText(searchElement.price);
            holder.cur.setText(searchElement.curr);
        //holder.chg.setText(searchElement.change);






        return rowView;
    }
    static class RowHolder{
        TextView cmpname;
        TextView textName;
        TextView exch;
        TextView price;
        TextView cur;
        TextView chg;
    }


}
