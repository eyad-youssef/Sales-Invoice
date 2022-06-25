
package sig.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
            
                case "New Invoice": 
                    newInv();
                    break;
                case "Delete Invoice":  
                    deleteInv();
                    break;
                case "New Item": 
                    newItem(); 
                    break; 
                case"Delete Item ": 
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

    private void newInv() { 
        
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
        if (selectedindex != -1 ){
       InvoiceHeader currentinv =  frame.getInvoices().get(selectedindex);  
       frame.getNumLabel().setText("" + currentinv.getNum()); 
       
       frame.getCustomerLabel().setText(currentinv.getCutomername()); 
       frame.getDateLabel().setText( currentinv.getDate()); 
       frame.getTotalLabel().setText("" + currentinv.getinvtotal());   
       
       LineTableModel ltm =  new LineTableModel(currentinv.getLines());  
       frame.getLineTable().setModel(ltm); 
       ltm.fireTableDataChanged();
    } 
    } 
    
    
    private void loadfile() { 
        JFileChooser fc= new JFileChooser(); 
        
        try { 
            
        int result = fc.showOpenDialog(frame); 
        if(result == JFileChooser.APPROVE_OPTION){
        
        File headerFile = fc.getSelectedFile();  
        
         Path headerpath = Paths.get(headerFile.getAbsolutePath());  
         List<String>  headerlines= Files.readAllLines(headerpath) ; 
         ArrayList<InvoiceHeader> invoicesarray =new ArrayList<>();
         for(String headerline : headerlines){  
                
           try{  
           String [] headerparts = headerline.split(",");
                 int invnum =Integer.parseInt(headerparts[0]) ; 
                 String invdate = headerparts[1];  
                 String customername = headerparts[2];
                 
                 
                 
                 InvoiceHeader inv =new InvoiceHeader(invnum, invdate,customername );
                 invoicesarray.add(inv);  
          } 
           
           
           
           catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame,"Line format error","Error",JOptionPane.ERROR_MESSAGE);
                }
            
         
         }
         result =fc.showOpenDialog(frame);  
         if (result == JFileChooser.APPROVE_OPTION){ 
           File linefile= fc.getSelectedFile();  
           Path linepath=Paths.get(linefile.getAbsolutePath());  
           List<String>  linelines= Files.readAllLines(linepath) ;   
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
                 
                 }
                 
                 InvoiceLine line =new InvoiceLine(itemname,itemprice, itemcount,innv );
                 innv.getLines().add(line);
         
         }catch(Exception ex)
                    {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,"Line format error","Error",JOptionPane.ERROR_MESSAGE);
                    } 
           }
         }
      
         
         
         
         
         frame.setInvoices(invoicesarray) ; 
         
         
         InvTableModel invtablemodel =new InvTableModel(invoicesarray); 
        frame.setInvtableModel(invtablemodel);  
         
         frame.getHeaderTable().setModel(invtablemodel);  
         frame.getInvtableModel().fireTableDataChanged();
         
                 
        }
    }          
        catch(IOException ex) {ex.printStackTrace(); }
              
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
        
        String date =invdailoge.getDateField().getText(); 
        String customername =  invdailoge.getCustomerField().getText();  
        int num = frame.getNextInvNum(); 
        InvoiceHeader invoice= new InvoiceHeader(num , date , customername );  
        
        frame.getInvoices().add(invoice); 
        frame.getInvtableModel().fireTableDataChanged(); 
        invdailoge.setVisible(false); 
        invdailoge.dispose(); 
        invdailoge= null ;
                
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
         InvoiceLine line = new InvoiceLine(item , price , count,invoice  ); 
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
