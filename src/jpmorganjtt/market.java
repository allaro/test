package jpmorganjtt;

/**
 *
 * @author Alberto La Rocca
 */

public class market {
    String entity;
    char bs;
    double afx;
    String curr;
    String dateInstruct;
    String dateSettl;
    int units;
    double priceUnits;
    
    public market(String e, char b, double a, String c, String i, String s, int u, double p){
        this.entity=e;
        this.bs=b;
        this.afx=a;
        this.curr=c;
        this.dateInstruct=i;
        this.dateSettl=s;
        this.units=u;
        this.priceUnits=p;
    }
    
    public String getEntity(){
        return entity;
    }
    
    public char getBuySell(){
        return bs;
    }
    
    public double getAgreedFx(){
        return afx;
    }
    
    public String getCurrency(){
        return curr;
    }
    
    public String getDateInstruction(){
        return dateInstruct;
    }
    
    public String getDateSettlement(){
        return dateSettl;
    }
    
    public int getUnits(){
        return units;
    }
    
    public double getPriceUnits(){
        return priceUnits;
    }
}
