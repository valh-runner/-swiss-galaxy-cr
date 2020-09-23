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
    
    /**
     * Sélectionne l'item Key de la comboBox passée en argumment
     * @param comboBox
     * @param key
     */
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
    
    /**
     * Affiche un message temporaire dans le JLabel passé en arguement
     * @param jLabel
     * @param message
     */
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
    
    /**
     * Rend non éditable la ComboBox passée en argument
     * @param jComboBox
     */
    public static void setComboBoxReadOnly(JComboBox jComboBox){
        ((JTextField)jComboBox.getEditor().getEditorComponent()).setDisabledTextColor(new Color(51,51,51));
        jComboBox.setEnabled(false);
        jComboBox.setEditable(true);
    }
    
    /**
     * Rend éditable la ComboBox passée en argument
     * @param jComboBox
     */
    public static void setComboBoxNormal(JComboBox jComboBox){
        ((JTextField)jComboBox.getEditor().getEditorComponent()).setDisabledTextColor(new Color(184, 207, 229));
        jComboBox.setEditable(false);
        jComboBox.setEnabled(true);
    }
    
    /**
     * Vérifie si une chaîne représente un entier
     * @param str
     * @return isValid
     */
    public static boolean isNumeric(String str) {
        boolean isValid = true;
        try{ Integer.parseInt(str); }
        catch(NumberFormatException nfe){ 
            isValid = false;
        }
        return isValid;
    }
}