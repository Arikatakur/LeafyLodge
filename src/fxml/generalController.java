package fxml;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class generalController {

    @FXML
    private HBox HomeHBox;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private HBox optionsHBox;

    @FXML
    private HBox profilehbox;

    @FXML
    private HBox multiThreadHBox;

    @FXML
    private HBox settingshbox;
    
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
    void handleThreadClicked(MouseEvent event){
        try{
            Node ThreadMenu = FXMLLoader.load(getClass().getResource("multiThreadMenu.fxml"));
            centerPane.getChildren().clear();
            centerPane.getChildren().add(ThreadMenu);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfileClicked(MouseEvent event){
        try{
            Node profile = FXMLLoader.load(getClass().getResource("profile.fxml"));
            centerPane.getChildren().clear();
            centerPane.getChildren().add(profile);
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

    @FXML
    public void initialize() {
        makeStageDraggable();
    }

    private double xOffset = 0;
    private double yOffset = 0;

    private void makeStageDraggable() {
        // Assuming mainLayout is the container you wish to attach the drag functionality to
        mainPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        mainPane.setOnMouseDragged(event -> {
            // Get the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Update the stage position based on the drag
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
