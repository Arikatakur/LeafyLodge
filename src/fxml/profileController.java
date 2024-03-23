package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class profileController {

    @FXML
    private TextField PhoneText;

    @FXML
    private TextField dateText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField nameText;

    @FXML
    private Button saveChanges;

    @FXML
    private TextField usernameText;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHide;

    @FXML
    void handleButtonClicked(MouseEvent event) {
        if(event.getSource().equals(btnExit)){
            System.exit(0);
        } else if (event.getSource() == btnHide) {
            Stage stage = (Stage) btnHide.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    @FXML
    void handleActions(ActionEvent event) {

    }

}
