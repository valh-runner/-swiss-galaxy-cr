/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;

/**
 *
 * @author Home
 */
public class Menu extends JFrame {

    private final JPanel container = new JPanel();
    private final JButton button1 = new JButton();
    private final JButton button2 = new JButton();
    private final JButton button3 = new JButton();
    private final JButton button4 = new JButton();
    private final JButton button5 = new JButton();
    private final JButton button7 = new JButton();
    private final JButton button8 = new JButton();
    private final JLabel label1 = new JLabel("Comptes-Rendus");
    private final JLabel label8 = new JLabel("Statistiques");
    private final JLabel label2 = new JLabel("Visiteurs");
    private final JLabel label3 = new JLabel("Praticiens");
    private final JLabel label4 = new JLabel("Medicaments");
    private final JLabel label7 = new JLabel("GestionFrais");
    private final JLabel label5 = new JLabel("Quitter");
    
    public Menu(){
        
        this.setTitle("VisiGo menu");
        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        container.setLayout(new BorderLayout());
        //container.setBackground(Color.white);

        JPanel center = new JPanel();
        GridLayout gridLayout = new GridLayout(5, 1);
        //gridLayout.preferredLayoutSize();
        center.setLayout(gridLayout);
        
        button1.setPreferredSize(new Dimension(20, 20));
        button2.setPreferredSize(new Dimension(20, 20));
        button3.setPreferredSize(new Dimension(20, 20));
        button4.setPreferredSize(new Dimension(20, 20));
        button5.setPreferredSize(new Dimension(20, 20));
        button7.setPreferredSize(new Dimension(20, 20));
        button8.setPreferredSize(new Dimension(20, 20));
        
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                Report report = new Report();
                report.setVisible(true);
            }
        });
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {                
                SwingUtilities.invokeLater(() -> {
                    WebViewFrais webViewSample2 = new WebViewFrais();
                });
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                System.exit(0);
            }
        });
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                ReportStat reportStat = new ReportStat();
                reportStat.setVisible(true);
            }
        });
        
        JPanel panWrap1 = new JPanel();
        panWrap1.setLayout(new FlowLayout(FlowLayout.LEFT));  
        panWrap1.add(button1);
        panWrap1.add(label1);
        JPanel panWrap8 = new JPanel();
        panWrap8.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        panWrap8.add(button8);
        panWrap8.add(label8);
        JPanel panWrap2 = new JPanel();
        panWrap2.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        panWrap2.add(button2);
        panWrap2.add(label2);
        JPanel panWrap3 = new JPanel();
        panWrap3.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        panWrap3.add(button3);
        panWrap3.add(label3);
        JPanel panWrap4 = new JPanel();
        panWrap4.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        panWrap4.add(button4);
        panWrap4.add(label4);
        JPanel panWrap7 = new JPanel();
        panWrap7.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        panWrap7.add(button7);
        panWrap7.add(label7);
        JPanel panWrap5 = new JPanel();
        panWrap5.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        panWrap5.add(button5);
        panWrap5.add(label5);
        
        center.setBorder(new EmptyBorder(new Insets(30, 30, 30, 30)));
        center.add(panWrap1);
        center.add(panWrap8);
        center.add(panWrap2);
        center.add(panWrap3);
        center.add(panWrap4);
        center.add(panWrap7);
        center.add(panWrap5);
        container.add(center, BorderLayout.CENTER);

        //ImageIcon icon = new ImageIcon("logo.jpg");
        /*Image image = new ImageIcon("logo.jpg").getImage().getScaledInstance(113, 72, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);*/
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("logo.jpg")); //chargement path friendly
        Image image = imageIcon.getImage().getScaledInstance(113, 72, Image.SCALE_SMOOTH); //redimensionnement
        ImageIcon imageIcon2 = new ImageIcon(image); //remise en ImageIcon
        JLabel thumb = new JLabel();
        thumb.setIcon(imageIcon2);
        JPanel panWest = new JPanel();
        panWest.setBorder(new EmptyBorder(new Insets(30, 30, 30, 30)));
        panWest.setBackground(new Color(96, 149, 213));
        panWest.add(thumb);
        container.add(panWest, BorderLayout.WEST);
        
        JLabel labelTitle = new JLabel("Gestion des comptes rendus");
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        labelTitle.setForeground(Color.white);
        JPanel panNorth = new JPanel();
        panNorth.setBorder(new EmptyBorder(new Insets(10, 0, 0, 0)));
        panNorth.setBackground(new Color(96, 149, 213));
        panNorth.add(labelTitle);
        container.add(panNorth, BorderLayout.NORTH);

        this.setContentPane(container);
        this.setResizable(false); 
        this.setVisible(true);
    }
    
}
