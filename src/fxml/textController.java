package fxml;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class textController {

    @FXML
    private MenuItem maxLoggedItem;
    
    @FXML
    private MenuItem totalLoggedItem;

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
    void handleMaxLogged(ActionEvent event) throws IOException {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("optionsPage.fxml"));
            Scene optionsScene = new Scene(root);
            Client.resultsPage.OutPutOfMaxLoggedValue(optionsScene);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void handleTotalLogged(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("optionsPage.fxml"));
            Scene optionsScene = new Scene(root);
            Client.resultsPage.OutPutOfTotalLoggedValue(optionsScene);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
