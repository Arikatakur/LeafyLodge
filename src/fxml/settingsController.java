package fxml;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class settingsController {

    @FXML
    private Button Logout;

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private Button btnHide;

    @FXML
    private Button btnExit;

    @FXML
    private void handleButtonClicked(MouseEvent event){
        if(event.getSource().equals(btnExit)){
            System.exit(0);
        } else if (event.getSource().equals(btnHide)) {
            Stage stage = (Stage) btnHide.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    @FXML
    void handleButtonClicked(ActionEvent event) throws SQLException {
        if (event.getSource().equals(Logout)) {
            try{
                    Parent loginRoot = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
                    Scene loginScene = new Scene(loginRoot);
                    Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    loginStage.setScene(loginScene);
                    loginStage.show();
                    fxmlLoginController.connection.close();

                    }catch(IOException e){
                    e.printStackTrace();
                }
        }
    }

}
