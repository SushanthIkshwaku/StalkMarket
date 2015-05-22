package app.stock.ikshwaku.stalkmarket;

/**
 * Created by ikshwaku on 12/04/15.
 */
public class libraryDataType {
    String name;
    String symbol;
    String exch;
    String price;
    String curr;
    String change;
    public libraryDataType(String name,String symbol,
                           String exch,
                           String price,String curr,String change){
        this.name=name;
        this.symbol=symbol;
        this.exch=exch;
        this.price=price;
        this.curr=curr;
        this.change=change;
    }
}
