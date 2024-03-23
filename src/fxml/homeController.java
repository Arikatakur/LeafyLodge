package fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class homeController {

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

}
