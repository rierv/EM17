package em17.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ToastMessage {
    public static final int WIDTH = 1024, HEIGHT = 600;
    
    public static void makeDoneToast(Stage primaryStage, String message) {
        int duration = 1500, startAnimationDuration = 500, endAnimationDuration = 500,
                red = 0, green = 150, blue = 0;
        double opacity = 0.5d;
        makeText(primaryStage, message, duration, startAnimationDuration,
                endAnimationDuration, red, green, blue, opacity);
    }
    
    public static void makeErrorToast(Stage primaryStage, String message) {
        int duration = 5000, startAnimationDuration = 500, endAnimationDuration = 500,
                red = 150, green = 0, blue = 0;
        double opacity = 0.9d;
        makeText(primaryStage, message, duration, startAnimationDuration,
                endAnimationDuration, red, green, blue, opacity);
    }
    
    public static void makeInfoToast(Stage primaryStage, String message) {
        int duration = 10000, startAnimationDuration = 500, endAnimationDuration = 500,
                red = 30, green = 30, blue = 30;
        double opacity = 0.75d;
        makeText(primaryStage, message, duration, startAnimationDuration,
                endAnimationDuration, red, green, blue, opacity);
    }
    
    private static void makeText(Stage ownerStage, String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay, int red, int green, int blue, double opacity) {
        toastMsg = toastMsg + "\n\nClick here to dismiss.";
        
        Stage toastStage=new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Arial Black", 18));
        text.setFill(Color.WHITE);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(" + red + ", " + green + ", " + blue + ", " + opacity + "); -fx-padding: 48px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        Timeline fadeOutTimeline = new Timeline();
        KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 0)); 
        fadeOutTimeline.getKeyFrames().add(fadeOutKey1);   
        fadeOutTimeline.setOnFinished((aeb) -> toastStage.close()); 
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1)); 
        fadeInTimeline.getKeyFrames().add(fadeInKey1);   
        fadeInTimeline.setOnFinished((ae) -> {
            new Thread(() -> {
                try {
                    Thread.sleep(toastDelay);
                }
                catch (InterruptedException ignore) {
                }
                fadeOutTimeline.play();
            }).start();
        }); 
        
        toastStage.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent mouseEvent) -> {
            fadeOutTimeline.play();
        });
        
        fadeInTimeline.play();
    }
    
}