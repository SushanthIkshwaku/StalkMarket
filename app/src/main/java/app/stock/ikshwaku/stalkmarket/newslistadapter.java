package app.stock.ikshwaku.stalkmarket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ikshwaku on 13/04/15.
 */
public class newslistadapter extends ArrayAdapter<String> {
    private final Context context;
    int ViewResourceId;
    List<news> searchJsonArray;
    private static final String TAG = "MyActivityNews-ArrayAdapter";
    public newslistadapter(Context context,int ViewResourceId, List<news> searchJsonArray){
        super(context,ViewResourceId,new String[searchJsonArray.size()]);
        this.context=context;
        this.searchJsonArray=searchJsonArray;
        this.ViewResourceId=ViewResourceId;
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
            holder.title=(TextView) rowView.findViewById(R.id.title);
            holder.link=(TextView) rowView.findViewById(R.id.link);
            holder.time=(TextView) rowView.findViewById(R.id.time);
            rowView.setTag(holder);
        }

        else{
            holder=(RowHolder)rowView.getTag();
        }
        news searchElement=searchJsonArray.get(position);
        Log.v(TAG, searchElement.toString());
        SpannableString spanString = new SpannableString(searchElement.getTitle());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new RelativeSizeSpan(1f),0,spanString.length(),0);
        //spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0,spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.title.setText(spanString);
        Log.v("news12",searchElement.getTime());
        SpannableString spanString1 = new SpannableString(searchElement.getTime());
        spanString1.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString1.length(), 0);
        spanString1.setSpan(new ForegroundColorSpan(Color.GRAY), 0,spanString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString1.setSpan(new RelativeSizeSpan(0.75f),0,spanString1.length(),0);
        holder.time.setText(spanString1);

       // holder.title.setText(searchElement.getTitle());
        //holder.link.setText(searchElement.getUrl());


        return rowView;
    }
    static class RowHolder{
        TextView title;
        TextView link;
        TextView time;

    }


}

