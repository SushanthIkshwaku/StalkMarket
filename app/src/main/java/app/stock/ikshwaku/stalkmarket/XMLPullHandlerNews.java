package app.stock.ikshwaku.stalkmarket;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikshwaku on 13/04/15.
 */
public class XMLPullHandlerNews {
    private List<news> newsarray=new ArrayList<news>();
    private news newsitem;
    private String text;
    private String time;

    public List<news> getnews(){
        return newsarray;
    }

    public List<news> parse(InputStream is){
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser=factory.newPullParser();
            parser.setInput(is, null);

            int eventType=parser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String tagname=parser.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if(tagname.equalsIgnoreCase("title")){
                            newsitem=new news();

                        }
                        break;
                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        Log.v("my3",text);
                        break;
                    case XmlPullParser.END_TAG:

                        if(tagname.equalsIgnoreCase("title")){
                            newsitem.setTitle(text);
                            Log.v("my31",newsitem.getTitle());
                        }else if (tagname.equalsIgnoreCase("link")){
                            newsitem.setUrl(text);
                        }else if(tagname.equalsIgnoreCase("item")){
                            newsarray.add(newsitem);
                        }else if (tagname.equalsIgnoreCase("pubDate")){
                            newsitem.setTimee(text);
                        }
                        break;
                    default:
                        break;
                }
                eventType=parser.next();
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsarray;
    }
}
