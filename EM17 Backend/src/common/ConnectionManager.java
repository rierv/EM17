/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.stage.Stage;

/**
 *
 * @author Raffolox
 */
public class ConnectionManager {
    
    private static boolean stopped = false;
    
    private static boolean isConnectionAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            return false;
        }
    }
    
    private static void stop() {
        stopped = true;
    }
    
    private static void showError() {
        Platform.runLater(
            new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Connection error");
                    alert.setHeaderText(null);
                    alert.setContentText("An internet connection is needed in order to use this program, but it is unavailable at the moment. Please check your connection and reopen the program.");
                    alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                        @Override
                        public void handle(DialogEvent event) {
                            closeApplication();
                        }
                    });
                    alert.showAndWait();
                    if (alert.getResult() != null) {
                        closeApplication();
                    }
                }
            }
        );
    }
    
    private static void closeApplication() {
        Platform.exit();
        System.exit(0);
    }
    
    public static void start() {
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    while(!stopped) {
                        if (!isConnectionAvailable()) {
                            ConnectionManager.stop();
                            showError();
                        }
                        else {
                            try {
                                Thread.sleep(5000);
                            }
                            catch (InterruptedException ignored) {
                                
                            }
                        }
                    }
                }
            }
        ).start();
    }
}
