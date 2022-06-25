/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author 20114
 */
public class LineTableModel extends AbstractTableModel {  
    
    
    private ArrayList<InvoiceLine> lines ;  
     private String []cols = {"No","Item Name","Item Price "," Item count","Total"}; 
      
     
    

    public LineTableModel(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    public ArrayList<InvoiceLine> getlines() {
        return lines;
    }

    @Override
    public int getRowCount() {
         return  lines.size();
   }

    @Override
    public int getColumnCount() { 
        return cols.length; 
     } 
    @Override
    public String getColumnName(int column ){ 
        return cols[column]; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { 
        InvoiceLine line= lines.get(rowIndex);  
        switch(columnIndex){  
            case 0 : return line.getInvoice().getNum(); 
            case 1 : return line.getName(); 
            case 2: return line.getPrice(); 
            case 3 : return line.getCount(); 
            case 4 : return line.getlinetotal(); 
            default: return ""; 
        
        
        }
    }
    
}
