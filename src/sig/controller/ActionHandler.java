
package sig.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sig.model.InvTableModel;
import sig.model.InvoiceHeader;
import sig.model.InvoiceLine;
import sig.model.LineTableModel;
import sig.view.Frame1;
import sig.view.InvoiceDialoge;
import sig.view.LineDialog;


public class ActionHandler implements ActionListener , ListSelectionListener {  
    
    
    
    
    private Frame1 frame;   
    private InvoiceDialoge invdailoge; 
    private LineDialog linedialoge; 
    
    public  ActionHandler(Frame1 frame){ 
          this.frame = frame; 
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()){ 
            
                case "New Invoice ": 
                    newInv();
                    break;
                case "Delete Invoice":  
                    deleteInv();
                    break;
                case "New Item": 
                    newItem(); 
                    break; 
                case"Delete Item    ": 
                         deleteItem();
                    break;
                case "load file": 
                    loadfile();
                    break;
                 case "save file": 
                     savefile();
                    break;   
                 case "CreatenewInvoiceOK": 
                     CreatenewInvoiceOK();
                    break;  
                 case "CanceleCreatenewInvoice":  
                     
                     CanceleCreatenewInvoice();
                    break;  
                
                 case "CreateNewLineOK": 
                     CreateNewLineOK();
                    break;  
                 case "CancelNewLine": 
                     CancelNewLine();
                    break; 
                    

            
            
            
            }
    
    }

    private void newInv(){ 
        
        invdailoge = new InvoiceDialoge(frame);  
        invdailoge.setVisible(true);  
        
           }

    private void deleteInv() { 
        
         int selectedrow = frame.getHeaderTable().getSelectedRow();
        if (selectedrow != -1 ){  
            frame.getInvoices().remove(selectedrow); 
            frame.getInvtableModel().fireTableDataChanged();
        }
       }
        
        

    private void newItem() { 
        linedialoge = new LineDialog(frame);  
        linedialoge.setVisible(true);  
       }

    private void deleteItem() {   
       int selectedinv =  frame.getHeaderTable().getSelectedRow();
          int selectedrow = frame.getLineTable().getSelectedRow();
        if ( selectedinv != -1 && selectedrow != -1 ){   
            
            InvoiceHeader  invoice = frame.getInvoices().get(selectedinv) ; 
            invoice.getLines().remove(selectedrow);  
            LineTableModel ltm= new LineTableModel(invoice.getLines());  
            frame.getLineTable().setModel(ltm);
            ltm.fireTableDataChanged(); 
            frame.getInvtableModel().fireTableDataChanged();
          
        
        }
        
        
        
        
        
          }

    
     @Override
    public void valueChanged(ListSelectionEvent e) { 
        int selectedindex = frame.getHeaderTable().getSelectedRow(); 
        
        
        
       /* ArrayList<InvoiceLine> lines = frame.getInvoices().get(selectedindex).getLines(); 
        
         frame.getLineTable().setModel( new LineTableModel(lines)); 
         
         */
         
        if (selectedindex != -1 ){ 
           // System.out.println("row selected" +selectedindex );
       InvoiceHeader currentinv =  frame.getInvoices().get(selectedindex);  
       frame.getNumLabel().setText("" + currentinv.getNum()); 
       frame.getDateLabel().setText( currentinv.getDate());
       frame.getCustomerLabel().setText(currentinv.getCutomername()); 
       frame.getTotalLabel().setText("" + currentinv.getinvtotal());   
       
      LineTableModel ltm =  new LineTableModel(currentinv.getLines());  
       frame.getLineTable().setModel(  ltm);
       ltm.fireTableDataChanged();
    } 
    } 
    
    
    private void loadfile() { 
        JFileChooser fc= new JFileChooser();
        //  fc.showOpenDialog(frame);
        
        try { 
            
        int result = fc.showOpenDialog(frame); 
        if(result == JFileChooser.APPROVE_OPTION){
        
        File headerFile = fc.getSelectedFile();  
        
         Path headerpath = Paths.get(headerFile.getAbsolutePath()); 
         
         List<String>  headerlines= Files.lines(headerpath).collect(Collectors.toList());//Files.readAllLines(headerpath) ; 
         ArrayList<InvoiceHeader> invoicesarray =new ArrayList<>(); 
          
         for(String headerline : headerlines){  
                
           try{  
           String [] headerparts = headerline.split(",");
                 int invnum =Integer.parseInt(headerparts[0]) ; 
                 String invdate = headerparts[1];  
                 String customername = headerparts[2];
                 
                 
                 
                 InvoiceHeader inv =new InvoiceHeader(invnum, invdate,customername );
                 invoicesarray.add(inv);  
          }catch(Exception ex){
                    ex.printStackTrace();
                       JOptionPane.showMessageDialog(frame,"can't read file ","Error",JOptionPane.ERROR_MESSAGE);
                }
            
         
         }
         result =fc.showOpenDialog(frame);  
         if (result == JFileChooser.APPROVE_OPTION){  
           File linefile= fc.getSelectedFile();  
           Path linepath=Paths.get(linefile.getAbsolutePath());  
           List<String>  linelines= Files.lines(linepath).collect(Collectors.toList()) ;   
           for(String lineline : linelines){  
               try{
                String [] lineparts = lineline.split(",");
                
                
                
                 int invnum =Integer.parseInt(lineparts[0]) ; 
                 String itemname = lineparts[1];
                 double itemprice = Double.parseDouble(lineparts[2])  ; 
                 int itemcount =Integer.parseInt(lineparts[3]); 
               
                 
                 InvoiceHeader innv =null ;   
                 for(InvoiceHeader invoice  : invoicesarray ){ 
                 if(invoice.getNum() == invnum ){  
                     
                     
                     innv= invoice;  
                     break ; 
                 
                 }
                 InvoiceLine line =new InvoiceLine( innv,itemname,itemprice , itemcount);
                    innv.getLines().add(line);
                 
                 }
                 frame.setInvoices(invoicesarray);
                 
                   }catch(Exception ex)
                    { 
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,"Line format error","Error",JOptionPane.ERROR_MESSAGE);
                    } 
           }
         }
      
         
         
         
         
         //frame.setInvoices(invoicesarray) ; 
         
         
         InvTableModel invtablemodel =new InvTableModel(invoicesarray); 
        frame.setInvtableModel(invtablemodel);  
         
         frame.getHeaderTable().setModel(invtablemodel);  
         frame.getInvtableModel().fireTableDataChanged();
         
                 
        }
    }          
        catch(IOException ex) {ex.printStackTrace(); 
        
        JOptionPane.showMessageDialog(frame,"format error","Error",JOptionPane.ERROR_MESSAGE);
        
        
        }
              
          } 
   

    private void savefile() { 
        
        
        ArrayList<InvoiceHeader> invoices = frame.getInvoices(); 
        String headers="";  
        String lines="";
        for (InvoiceHeader invoice : invoices) {  
            String invCSV=invoice.getAsCsv(); 
             headers+=invCSV;
            headers+="\n";  
            for(InvoiceLine line:invoice.getLines()){
                String itCSV=line.getAsCsv();
                lines+=itCSV;
                lines+="\n";
            }
        }  
        try{
            
        JFileChooser fc=new JFileChooser(); 
        int result=fc.showSaveDialog(frame);
           if(result==JFileChooser.APPROVE_OPTION){
               File headerFile=fc.getSelectedFile();
               FileWriter fwh =new FileWriter(headerFile);
               fwh.write(headers);
               fwh.flush();
              fwh.close();
               result=fc.showSaveDialog(frame); 
               if(result==JFileChooser.APPROVE_OPTION){
                   File lineFile=fc.getSelectedFile();  
                   FileWriter fwl=new FileWriter(lineFile);
               fwl.write(lines);
               fwl.flush();
               fwl.close();
               }
                   
                   
     }
   } 
        catch(Exception ex){
            
        }
        
        
        
    }

    

    private void CreatenewInvoiceOK() {    
        DateFormat df = new SimpleDateFormat("DD-MM-YYYY") ;
                
        
        String date =invdailoge.getDateField().getText(); 
        String customername =  invdailoge.getCustomerField().getText();  
        int num = frame.getNextInvNum(); 
         
        
        try{ 
            String [] dparts=date.split("-"); 
            if(dparts.length<3){
                JOptionPane.showMessageDialog(frame,date+" is wrong date format","Error",JOptionPane.ERROR_MESSAGE);
            
            }else {  
                int dd=Integer.parseInt(dparts[0]);
             int mm=Integer.parseInt(dparts[1]);
             int yy=Integer.parseInt(dparts[2]);
             if(dd>31||mm>12||1980>yy&&yy>2022){
                    JOptionPane.showMessageDialog(frame,date+" is wrong date format","Error",JOptionPane.ERROR_MESSAGE); 
             }
                
                
                
            
            InvoiceHeader invoice= new InvoiceHeader(num , date , customername );  
        
        frame.getInvoices().add(invoice); 
        frame.getInvtableModel().fireTableDataChanged(); 
        invdailoge.setVisible(false); 
        invdailoge.dispose(); 
        invdailoge= null ;
            
            }
        
                
        } 
        catch (Exception ex ){ 
            JOptionPane.showMessageDialog(frame,date+" is wrong date format","Error",JOptionPane.ERROR_MESSAGE);
 
         
        }
      
         } 
    private void CanceleCreatenewInvoice() {  
        invdailoge.setVisible(false); 
        invdailoge.dispose(); 
        invdailoge= null ; 
        
    }

    private void CreateNewLineOK() {  
        String item = linedialoge.getItNameField().getText();  
        String countstr = linedialoge.getItCountField().getText();  
        String pricestr = linedialoge.getItPriceField().getText();  
        int count  =  Integer.parseInt(countstr); 
         double price  =  Double.parseDouble(pricestr); 
         int selectedinv= frame.getHeaderTable().getSelectedRow(); 
         if(selectedinv != - 1 ){
         InvoiceHeader invoice= frame.getInvoices().get(selectedinv); 
         InvoiceLine line = new InvoiceLine(invoice ,item, price , count  ); 
          invoice.getLines().add(line); 
          LineTableModel ltm =(LineTableModel) frame.getLineTable().getModel();  
           
          ltm.fireTableDataChanged(); 
          frame.getInvtableModel().fireTableDataChanged();
          
         
         
         
         }
         
         
        
        linedialoge.setVisible(false); 
        linedialoge.dispose(); 
        linedialoge= null ; 
        
        
    }

    private void CancelNewLine() { 
        linedialoge.setVisible(false); 
        linedialoge.dispose(); 
        linedialoge= null ; 
        
     }

   
    
}
