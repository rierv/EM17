/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui.authentication;

import em17.gui.ToastMessage;
import em17.logic.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Raffolox
 */
public class AuthenticationScreenAdapter implements Initializable {
    
    /**
     * The user's email's text field
     */
    @FXML
    private TextField emailTextField;
    
    /**
     * The user's password's text field
     */
    @FXML
    private PasswordField passwordField;
    
    /**
     * Handles the click on the login button
     * @param event
     */
    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        SessionManager sm = SessionManager.getInstance();
        System.out.println("email: "+emailTextField.getText()+" password: "+passwordField.getText());
        sm.authenticate(emailTextField.getText(), passwordField.getText(), event);
        if (sm.isUserAuthenticated()) {
            sm.showMainScreen(event);
        }
        else {
            ToastMessage.makeErrorToast(((Stage)(emailTextField.getScene().getWindow())), "Couldn't find an user with those email and password. Please retry.");
        }
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nothing to do here...
    }    
    
}
