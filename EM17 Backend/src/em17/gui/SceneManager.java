package em17.gui;

import common.ConnectionManager;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Raffolox
 */
public class SceneManager extends Application {
    
    /**
     * Public constructor
     */
    public SceneManager() {
    }
    
    /**
     * Changes the active scene with the provided one
     * @param event the event that caused the need of a scene change
     * @param newSceneFXMLPath the path of the FXML file of the new scene
     */
    public static void changeScene(ActionEvent event, String newSceneFXMLPath) {
        Stage stageTheEventSourceNodeBelongs = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader myLoader = new FXMLLoader(SceneManager.class.getResource(newSceneFXMLPath));
        Pane myPane = null;
        try {
            myPane = (Pane)myLoader.load();
        }
        catch(IOException ioe) {
            System.out.println("IOEXCEPTION " + ioe.getMessage());
            ioe.printStackTrace();
        }
        stageTheEventSourceNodeBelongs.getScene().setRoot(myPane);
    }
    
    /**
     * Starts the application
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("EM-17");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("em17_color.png")));
        
        Parent root = FXMLLoader.load(getClass().getResource("/em17/gui/authentication/AuthenticationScreen.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
        ConnectionManager.start();
    }

    /**
     * ENTRY_POINT
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
