package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordFld;

    @FXML
    private TextField userIDFld;

    @FXML
    private Button signInBtn;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                // log in user method call
                Utilities.loginAction(actionEvent, userIDFld.getText(), passwordFld.getText());
            }
        });
    }
}
