/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Carbon
 */
public class Tools {
    
    public static void setSelectedValue(JComboBox comboBox, int key){
        ComboBoxItem item;
        for (int i = 0; i < comboBox.getItemCount(); i++){
            item = (ComboBoxItem)comboBox.getItemAt(i);
            if (item.getKey().equals(String.valueOf(key))){
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    public static void displayMessageTemporary(JLabel jLabel, String message){
        
        Timer autoHideTimer = new Timer(5000, new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if(jLabel.getText() == message){
                jLabel.setText("");
            }
          }
        });
        autoHideTimer.setRepeats(false);
        
        jLabel.setText(message);
        autoHideTimer.restart();
    }
    
    public static void setComboBoxReadOnly(JComboBox jComboBox){
//            jComboBox2.getEditor().getEditorComponent().setBackground(new Color(40,60,90));
        ((JTextField)jComboBox.getEditor().getEditorComponent()).setDisabledTextColor(new Color(51,51,51));
        jComboBox.setEnabled(false);
        jComboBox.setEditable(true);
    }
    
    public static void setComboBoxNormal(JComboBox jComboBox){
        ((JTextField)jComboBox.getEditor().getEditorComponent()).setDisabledTextColor(new Color(184, 207, 229));
        jComboBox.setEditable(false);
        jComboBox.setEnabled(true);
    }
    
    public static boolean isNumeric(String str) {
        boolean isValid = true;
        try{ Integer.parseInt(str); }
        catch(NumberFormatException nfe){ 
            System.out.println(str +" est pas num");
            isValid = false;
        }
        System.out.println(str +" est num");
        return isValid;
    }
}