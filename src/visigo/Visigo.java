/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

import java.awt.Color;
import javax.swing.UIManager;

/**
 *
 * @author Home
 */
public class Visigo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UIManager.put("CheckBox.disabledText", Color.BLACK);
        Window win = new Window();
    }
    
}
