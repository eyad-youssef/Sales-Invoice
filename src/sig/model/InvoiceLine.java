
package sig.model;


public class InvoiceLine { 
     
    private String name; 
    private double price; 
       private int count ; 
        private InvoiceHeader invoice;
  
    
    public InvoiceLine(){
     
    
    } ;

    public InvoiceLine(String name , double price,int count,InvoiceHeader invoice ) {
      
       
        this.name = name;
        this.price = price; 
         this.count = count; 
           this.invoice = invoice;
    } 
    
    
    public  double getlinetotal(){ return price*count ; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "invoice=" + invoice.getNum() +", name=" + name +  ", price=" + price  +", count=" + count + '}';
    }    
   
    
    
    public  String getAsCsv(){ 
    
    return  invoice.getNum() + ","+ name  + "," + count + ", "+ count ; 
            
            
            } 
    
    
}
