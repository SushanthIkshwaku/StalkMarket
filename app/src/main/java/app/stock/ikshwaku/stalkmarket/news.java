package app.stock.ikshwaku.stalkmarket;

/**
 * Created by ikshwaku on 13/04/15.
 */
public class news {
    private String title;
    private String url;
    private String time;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
        //Log.v("my31", this.title);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void  setTimee(String time){
        this.time=time;
    }
    public String getTime(){
        return time;
    }

}
