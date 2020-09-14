/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Carbon
 */
public class WebViewFrais {
    private JFrame fxFrame ;
    private JFXPanel fxPanel ;

    public WebViewFrais() {
        // must be on Swing thread...
        if (! SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Not on Event Dispatch Thread");
        }

        fxFrame = new JFrame();
        fxPanel = new JFXPanel();
        fxFrame.add(fxPanel);
        fxFrame.setSize(1024, 600);
        fxFrame.setLocationRelativeTo(null);
        fxFrame.setVisible(true);

        Platform.runLater(() -> initFX());
    }

    private void initFX() {
        // must be on FX Application Thread...
        if (! Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Not on FX Application Thread");
        }
        fxPanel.setScene(new Scene(new Browser(),1024,600, Color.web("#666970")));
    }
}