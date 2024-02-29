package fxml;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class generalController {

    @FXML
    private HBox HomeHBox;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private HBox optionsHBox;

    @FXML
    private HBox profilehbox;

    @FXML
    private HBox settingshbox;

    @FXML
    private void handleHomeClicked(MouseEvent event){
        try {
            Node home = FXMLLoader.load(getClass().getResource("Home.fxml"));
            centerPane.getChildren().clear();
            centerPane.getChildren().add(home);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleOptionsClicked(MouseEvent event) {
        try{
            Node menu = FXMLLoader.load(getClass().getResource("optionsPage.fxml"));
            centerPane.getChildren().clear();
            centerPane.getChildren().add(menu);
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handleProfileClicked(MouseEvent event){
        try{
            Node menu = FXMLLoader.load(getClass().getResource("profile.fxml"));
            centerPane.getChildren().clear();
            centerPane.getChildren().add(menu);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleSettingsClicked(MouseEvent event) {
        try {
            Node settings = FXMLLoader.load(getClass().getResource("settings.fxml"));
            centerPane.getChildren().clear();
            centerPane.getChildren().add(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
