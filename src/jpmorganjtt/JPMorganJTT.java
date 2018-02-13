package jpmorganjtt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;

/**
 *
 * @author Alberto La Rocca
 */

public class JPMorganJTT {
    public static market [] mkList;
    
    public static void main(String[] args) {
        setMkList();
        viewMarket();
        printReport();
    }
    
    public static void setMkList(){
        System.out.println(" -- Random creation -- ");
        mkList=new market[10];
        int r1 = 0;
        int max1 =3;
        int min1=1;
        int range1 = Math.abs(max1 - min1) + 1;
        int r2 = 0;
        int max2 =2;
        int min2=1;
        int range2 = Math.abs(max2 - min2) + 1;
        int r3 = 0;
        
        int dd=14;
        
        for(int i=0; i<mkList.length; i++){
            r1 = (int)(Math.random() * range1) + (min1 <= max1 ? min1 : max1);
            r2 = (int)(Math.random() * range2) + (min2 <= max1 ? min2 : max2);
            r3 = (int)(Math.random() * range2) + (min2 <= max1 ? min2 : max2);
            //if: r1=1 currency=SGP, r1=2 currency=AED, r1=3 currency=SAR
            //if: r2=1 buy/sell=buy, r2=2 buy/sell=sell
            //if: r3=1 entity=foo, r3=2 entity=bar;
            String curr;
            char bs;
            String ent;
            double a;
            int prize;
            double pu;            
            String dateI="";
            String dateS="";
            if(r1==1){
                curr="SGP";
            }
            else if(r1==2){
                curr="AED";
            }
            else{
                curr="SAR";
            }
            
            if(r2==1){                    
                bs='B';
                prize=200;
            }
            else{
                bs='S';
                prize=450;
            }
            
            if(r3==1){
                ent="foo";
                a=0.50;
                pu=100.25;
            }
            else{
                ent="bar";
                a=0.22;
                pu=150.50;                
            }
            
            //Sun, Mon, Tue, Wed, Thu, Fri, Sat
            if(curr.compareTo("AED")==0 || curr.compareTo("SAR")==0){
                dateI=(dd+i)+" Jan 2018";
                dateS=(dd+i+1)+" Jan 2018";
                switch(dayName(dateS)){
                    case "Fri": dateS=(dd+i+3)+" Jan 2018"; break;
                    case "Sat": dateS=(dd+i+2)+" Jan 2018"; break;
                    default: dateS=(dd+i+1)+" Jan 2018"; break;
                }
            }
            else{
                dateI=(dd+i)+" Jan 2018";
                dateS=(dd+i+1)+" Jan 2018";
                switch(dayName(dateS)){
                    case "Sat": dateS=(dd+i+3)+" Jan 2018"; break;
                    case "Sun": dateS=(dd+i+2)+" Jan 2018"; break;
                    default: dateS=(dd+i+1)+" Jan 2018"; break;
                }
            }         
            
            mkList[i]= new market(ent,bs,a,curr,dateI,dateS,prize,pu);
        }
    }
    
    public static void viewMarket(){        
        System.out.println("| Entity | Buy/Sell | AgreedFx | Currency | "
                + "InstructionDate | SettlementDate | Units | Price per unit |");
        for(int i=0; i<mkList.length;i++){
            System.out.println("   "+mkList[i].getEntity()+"        "+mkList[i].getBuySell()+"        "
                    +mkList[i].getAgreedFx()+"        "+mkList[i].getCurrency()+"        "
                    +mkList[i].getDateInstruction()+"        "+mkList[i].getDateSettlement()
                    +"      "+mkList[i].getUnits()+"        "+mkList[i].getPriceUnits());
        }
    }

    public static void printReport(){        
        System.out.println("");
        System.out.println("--------------------------------------------");
        System.out.println("Daily incoming amount report date: ");
        System.out.println("--------------------------------------------");
        LinkedList lldaily = new LinkedList();
        for(int i=0;i<mkList.length;i++){
            if(lldaily.isEmpty()){
                lldaily.add(mkList[i].getDateSettlement());
            }
            else{
                if(-1==lldaily.indexOf(mkList[i].getDateSettlement())){
                    lldaily.add(mkList[i].getDateSettlement());
                }                
            }
        }        
        double amount=0;
        for(int i=0;i<lldaily.size();i++){
            for(int j=0; j<mkList.length; j++){
                if(lldaily.get(i).toString().compareTo(mkList[j].getDateSettlement())==0 
                        && mkList[j].getBuySell()=='S'){
                    amount=amount+((mkList[j].getPriceUnits()*mkList[j].getUnits())*mkList[j].getAgreedFx());               
                }
            }
            if(amount!=0){          
                System.out.println("");
                System.out.println(lldaily.get(i)+"  -  USD  "+amount);
                amount=0;
            }
        }
        
        System.out.println("");
        System.out.println("--------------------------------------------");
        System.out.println("Daily outgoing amount report date: ");
        System.out.println("--------------------------------------------");
        amount=0;
        for(int i=0;i<lldaily.size();i++){
            for(int j=0; j<mkList.length; j++){
                if(lldaily.get(i).toString().compareTo(mkList[j].getDateSettlement())==0 
                        && mkList[j].getBuySell()=='B'){
                    amount=amount+((mkList[j].getPriceUnits()*mkList[j].getUnits())*mkList[j].getAgreedFx());               
                }
            }
            if(amount!=0){          
                System.out.println("");
                System.out.println(lldaily.get(i)+"  -  USD  "+amount);
                amount=0;
            }
        }
        
        System.out.println("");
        System.out.println("--------------------------------------------");
        System.out.println("Entity incoming rank: ");
        System.out.println("--------------------------------------------");        
        LinkedList llentity = new LinkedList();
        LinkedList llentitybuy = new LinkedList();
        LinkedList llentitysell = new LinkedList();
        int rankb;
        int ranks;
        for(int i=0;i<mkList.length;i++){
            if(llentity.isEmpty()){
                llentity.add(mkList[i].getEntity());
            }
            else{
                if(-1==llentity.indexOf(mkList[i].getEntity())){
                    llentity.add(mkList[i].getEntity());
                }                
            }
        }
        
        for(int i=0;i<llentity.size();i++){
            rankb=0;
            ranks=0;
            for(int j=0; j<mkList.length; j++){
                if(llentity.get(i).toString().compareTo(mkList[j].getEntity())==0){
                    if(mkList[j].getBuySell()=='B'){
                        rankb++;
                    }
                    else{
                        ranks++;
                    }                   
                }
            }
            llentity.set(i, llentity.get(i)+" B "+rankb+" S "+ranks);
        }
        System.out.println("| Rank | Entity |");
        Collections.sort(llentity, Comparator.comparingInt((String e) -> Integer.parseInt(e.split(" ")[2])).reversed());
        for(int i=0; i<llentity.size();i++){
            String [] spl = llentity.get(i).toString().split(" ");
            llentitybuy.add((i+1)+" "+spl[0]+" "+spl[2]);
            System.out.println("   "+(i+1)+"      "+spl[0]);
        }
        
        System.out.println("");
        System.out.println("--------------------------------------------");
        System.out.println("Entity outgoing rank: ");
        System.out.println("--------------------------------------------");
        System.out.println("| Rank | Entity |");
        Collections.sort(llentity, Comparator.comparingInt((String e) -> Integer.parseInt(e.split(" ")[4])).reversed());
        for(int i=0; i<llentity.size();i++){
            String [] spl = llentity.get(i).toString().split(" ");
            llentitysell.add((i+1)+" "+spl[0]+" "+spl[4]);
            System.out.println("   "+(i+1)+"      "+spl[0]);
        }
        
        System.out.println("");
    } 
    
    public static String dayName(String inputDate){
        String format="dd MM yyyy";
        
        String month=null;
        String [] s=inputDate.split(" ");
        switch (s[1]){
            case "Jan": month = "01"; break;
            case "Feb": month = "02"; break;
            case "Mar": month = "03"; break;
            case "Apr": month = "04"; break;
            case "May": month = "05"; break;
            case "Jun": month = "06"; break;
            case "Jul": month = "07"; break;
            case "Aug": month = "08"; break;
            case "Sep": month = "09"; break;
            case "Oct": month = "10"; break;
            case "Nov": month = "11"; break;
            case "Dec": month = "12"; break;
            default: month = s[1]; break;
        }
        String date=s[0]+" "+month+" "+s[2];
        
        String dayName=LocalDate.parse(date , DateTimeFormatter.ofPattern(format)
        ).getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE , Locale.UK);
        return dayName;
    }   
}