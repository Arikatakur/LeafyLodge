package fxml;

import java.io.IOException;
import java.sql.SQLException;

import Client.resultsPage;
import Server.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class fxmlOptionsController {
    

    @FXML
    private Button LoggedValueZeroBtn;

    @FXML
    private Label ZeroLoggedValueLabel;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaxProduction;

    @FXML
    private Button btnMinLineID;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnTotalLoggedValue;

    @FXML
    private Pane centerVBoxPane;

    @FXML
    private Label dateLabel;

    @FXML
    private Button maxLoggedValueBtn;

    @FXML
    private Label maxLoggedValueLabel;

    @FXML
    private Label minLineIDLabel;

    @FXML
    private VBox optionsVBOX;

    @FXML
    private Label totalLoggedValueLabel;
    ///// ----- from here down

        @FXML
    private MenuItem maxLoggedMenu;

    @FXML
    private MenuItem maxProductionMenu;

    @FXML
    private MenuItem minLoggedMenu;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private MenuItem totalLoggedMenu;

    @FXML
    private MenuItem zeroLoggedMenu;

    @FXML
    private MenuButton menu;


    @FXML
    void handleButtonClicked(ActionEvent event) throws IOException, SQLException{
                    Parent root = FXMLLoader.load(getClass().getResource("optionsPage.fxml"));
                    Scene optionsScene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    if (event.getSource().equals(maxLoggedMenu)) {
                        resultsPage.OutPutOfMaxLoggedValue(stage, optionsScene);
                    }else if(event.getSource().equals(maxProductionMenu)){
                        resultsPage.OutputOfMaxProduction(stage, optionsScene);
                    }else if(event.getSource().equals(minLoggedMenu)){
                        resultsPage.resultOfLineIdMin(stage,optionsScene);
                        }
                        menu.
                    }

        // if(event.getSource().equals(btnExit)){
        //     System.exit(0);
        // }else if(event.getSource().equals(btnBack)){
        //     try{
        //             Parent loginRoot = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        //             Scene loginScene = new Scene(loginRoot);
        //             Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //             loginStage.setScene(loginScene);
        //             loginStage.show();
        //             fxmlLoginController.connection.close();

        //             }catch(IOException e){
        //             e.printStackTrace();
        //         }
        // }else if(event.getSource().equals(maxLoggedValueBtn)){
        //     resultsPage.OutPutOfMaxLoggedValue(stage,optionsScene);
        // }  
        // else if(event.getSource().equals(btnTotalLoggedValue)){
        //     resultsPage.OutPutOfTotalLoggedValue(stage, optionsScene);
        // }
        // else if(event.getSource().equals(btnMinLineID)){
        //     resultsPage.resultOfLineIdMin(stage, optionsScene);
        // }
        // else if (event.getSource().equals(btnMaxProduction)){
        //     resultsPage.OutputOfMaxProduction(stage, optionsScene);    
        // }
        // else if (event.getSource().equals(LoggedValueZeroBtn)){
        //     resultsPage.resultsOfLoggedValueEqualsZero(stage, optionsScene);
        // }
        // else if (event.getSource() == btnMinimize) {
        //         Stage stage1 = (Stage) btnMinimize.getScene().getWindow();
        //         stage1.setIconified(true);
        // }
        
    // }   
}
//     @FXML
//     private void handleProfileClicked(MouseEvent event){
//         // try {
//         //     Node profilePage = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
//         //     centerPane.getChildren().clear();
//         //     centerPane.getChildren().add(profilePage);
//         // } catch (IOException e) {
//         //     e.printStackTrace();
//         // }
//     }

//     @FXML
//     private void handleSettingsClicked(MouseEvent event){
//         // try{
//         //     FXMLLoader loader = FXMLLoader.load(getClass().getResource("OptionsPage.fxml"));
//         //     Parent optionsPageRoot = loader.load();
//         //     Node optionsPane = (Node) optionsPageRoot.lookup("#optionsVBOX");
//         //     if (optionsPane != null) {
//         //     centerPane.getChildren().clear();
//         //     centerPane.getChildren().add(optionsPane);
//         //     }
//         // }catch(IOException e){
//         //     e.printStackTrace();
//         // }
//         try {
//             Node settings = FXMLLoader.load(getClass().getResource("settings.fxml"));
//             centerPane.getChildren().clear();
//             centerPane.getChildren().add(settings);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//     @FXML
//     private void handleOptionsClicked(MouseEvent event){}

// }
