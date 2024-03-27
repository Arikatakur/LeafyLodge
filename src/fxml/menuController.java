package fxml;

import java.io.IOException;

import Server.Product;
import Server.SQLQueries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class menuController {


    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<Product, Integer> cmdType;

    @FXML
    private TableColumn<Product, String> description;

    @FXML
    private TableColumn<Product, String> lineId;

    @FXML
    private TableColumn<Product, Integer> logId;

    @FXML
    private TableColumn<Product, String> logTime;

    @FXML
    private TableColumn<Product, Double> loggedValue;

    @FXML
    private TableColumn<Product, String> unitType;

    @FXML
    private MenuItem maxLoggedItem;

    @FXML
    private MenuItem totalLoggedItem;

    @FXML
    private MenuItem loggedValueZero;

    @FXML
    private MenuItem maxProduction;

    @FXML
    private MenuItem minLineId;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHide;


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
    void handleMaxLogged(ActionEvent event) {
        ObservableList<Product> data = SQLQueries.MaxLoggedValueRecord(fxmlLoginController.connection);
        table.setItems(data);

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

    @FXML
    void handleMaxProduction(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("optionsPage.fxml"));
            Scene optionsScene = new Scene(root);
            Client.resultsPage.OutputOfMaxProduction(optionsScene);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void handleMinLineId(ActionEvent event){
        ObservableList<Product> data = SQLQueries.lineIdWhereLoggedValueMin(fxmlLoginController.connection);
        table.setItems(data);
    }

    @FXML
    void handleLoggedValueZero(ActionEvent event){
        ObservableList<Product> data = SQLQueries.LoggedValueEqualsZero(fxmlLoginController.connection);
        table.setItems(data);
    }

    @FXML
    public void initialize() {
        logId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        lineId.setCellValueFactory(new PropertyValueFactory<>("lineId"));
        logTime.setCellValueFactory(new PropertyValueFactory<>("logTime"));
        loggedValue.setCellValueFactory(new PropertyValueFactory<>("loggedValue"));
        cmdType.setCellValueFactory(new PropertyValueFactory<>("cmdType"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitType.setCellValueFactory(new PropertyValueFactory<>("unitType"));
    }
}
