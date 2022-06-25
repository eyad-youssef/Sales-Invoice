
package sig.model;

import java.util.ArrayList;
//import java.util.Date;


public class InvoiceHeader { 
    
    private int num; 
    private String date;
     private String cutomername; 
    private ArrayList<InvoiceLine> lines; 
    
    public InvoiceHeader(){}

    public InvoiceHeader(int num, String date, String cutomername) {
        this.num = num;
        this.date = date; 
        this.cutomername = cutomername;
    } 
    
    public  double getinvtotal(){  
        double total=0.0; 
        for (InvoiceLine line : getLines()){
        total+= line.getlinetotal();
        }
        
        return total; } 
    
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCutomername() {
        return cutomername;
    }

    public void setCutomername(String cutomername) {
        this.cutomername = cutomername;
    }

    public ArrayList<InvoiceLine> getLines() { 
        if(lines==null){ 
        
        lines =new ArrayList<>(); 
        }
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num=" + num + ", date=" + date +", cutomername=" + cutomername +  '}';
    } 
    
    
    public  String getAsCsv(){ 
    
    return  num + ","+ date + "," + cutomername; 
            
            
            } 
      
    
    
    
    
    
    
    
}
