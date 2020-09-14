/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Home
 */
public class Window extends JFrame {

    private final JPanel container = new JPanel();
    private final JLabel label1 = new JLabel("Identifiez vous");
    //private JTextField field1 = new JTextField("");
    private JTextField field1 = new JTextField("Andre"); //TODO :enlever texte par défaut
    //private JTextField field2 = new JTextField("");
    private JTextField field2 = new JTextField("1991-08-26"); //TODO :enlever texte par défaut
    private final JButton button1 = new JButton("OK");
    private JLabel label2 = new JLabel();

    public Window() {

        this.setTitle("VisiGo");
        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        container.setLayout(new BorderLayout());
        //container.setBackground(Color.white);

        JPanel center = new JPanel();
        Box box1 = Box.createVerticalBox();
        field1.setPreferredSize(new Dimension(100, 30));
        field2.setPreferredSize(new Dimension(100, 30));

        //action du bouton
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {

                boolean isAuth = ConnectionManager.auth(field1.getText(), field2.getText());
                if (isAuth == false) {
                    label2.setText("Identifiants incorrects");
                } else {
                    Menu menu = new Menu();
                    close();
                }
            }
        });

        box1.add(label1);
        box1.add(field1);
        box1.add(field2);
        box1.add(button1);
        box1.add(label2);
        center.add(box1);
        container.add(center, BorderLayout.CENTER);

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("logo.jpg"));
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        container.add(thumb, BorderLayout.WEST);

        this.setContentPane(container);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void close() {
        this.dispose();  //fermeture de la présente JFrame (class Window)
    }

}
