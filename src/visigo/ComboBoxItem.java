/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

/**
 *
 * @author Carbon
 */
public class ComboBoxItem {
    
    private String key;
    private String value;

    public ComboBoxItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
}
