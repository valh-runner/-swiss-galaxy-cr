/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

import java.awt.BorderLayout;
import java.util.Random;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.paint.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Carbon
 */
public class SwingFXExample {
    private JFrame mainFrame ;
    private JFrame fxFrame ;
    private JFXPanel fxPanel ;

    public SwingFXExample() {
        // must be on Swing thread...
        if (! SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Not on Event Dispatch Thread");
        }

        mainFrame = new JFrame();
        JButton showFX = new JButton("Show FX Window");
        JPanel content = new JPanel();
        content.add(showFX);
        mainFrame.add(content, BorderLayout.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fxFrame = new JFrame();
        fxPanel = new JFXPanel();
        fxFrame.add(fxPanel);
        fxFrame.setSize(640, 640);
        fxFrame.setLocationRelativeTo(null);


        Platform.runLater(() -> initFX()); 

        showFX.addActionListener(event -> fxFrame.setVisible(true));
    }

    private void initFX() {
        // must be on FX Application Thread...
        if (! Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Not on FX Application Thread");
        }

        fxPanel.setScene(new Scene(new Browser(),750,500, Color.web("#666970")));
    }

    public void showMainWindow() {
        mainFrame.setSize(350, 120);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingFXExample app = new SwingFXExample();
            app.showMainWindow();
        });
    }*/
}