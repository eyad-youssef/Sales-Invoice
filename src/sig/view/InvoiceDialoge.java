
package sig.view; 

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

   


public class InvoiceDialoge extends JDialog {  
    
  private JLabel customerlabel;
  private JTextField customerField; 
  private JLabel datelabel; 
  
  private JTextField dateField;
  
    
  private JButton cancelBtn; 
    
  private JButton okBtn; 
  
   

    public InvoiceDialoge(Frame1 frame) {   
        
        customerlabel=new JLabel("Customer Name: ");
        customerField=new JTextField(20); 
        
        datelabel=new JLabel("Invoice Date: ");
        dateField=new JTextField(20);
        
        
        okBtn=new JButton("OK");
        cancelBtn=new JButton("Cancel"); 
        
        
        okBtn.setActionCommand("CreatenewInvoiceOK");
        cancelBtn.setActionCommand("CanceleCreatenewInvoice"); 
        
 
        okBtn.addActionListener(frame.getHandler());
        cancelBtn.addActionListener(frame.getHandler());
       
        setLayout(new GridLayout(3,2));
       
        add(datelabel);
        add(dateField);
        
        add(customerlabel);
        add(customerField);
        
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }

    public JTextField getCustomerField() {
        return customerField;
    }

    public JTextField getDateField() {
        return dateField;
    }

   
    
    
    }
   
    

