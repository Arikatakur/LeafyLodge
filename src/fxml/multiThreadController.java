package fxml;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

import Server.Product;
import Server.SQLQueries;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class multiThreadController {

    @FXML
    private ChoiceBox<String> comboBox1;

    @FXML
    private ChoiceBox<String> comboBox2;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHide;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private TableView<Product> table1;

    @FXML
    private TableColumn<Product, Integer> cmdType1;

    @FXML
    private TableColumn<Product, String> description1;

    @FXML
    private TableColumn<Product, String> lineId1;

    @FXML
    private TableColumn<Product, Integer> logId1;

    @FXML
    private TableColumn<Product, String> logTime1;

    @FXML
    private TableColumn<Product, Double> loggedValue1;

    @FXML
    private TableColumn<Product, String> unitType1;

    @FXML
    private TableView<Product> table2;

    @FXML
    private TableColumn<Product, Integer> cmdType2;

    @FXML
    private TableColumn<Product, String> description2;

    @FXML
    private TableColumn<Product, String> lineId2;

    @FXML
    private TableColumn<Product, Integer> logId2;

    @FXML
    private TableColumn<Product, String> logTime2;

    @FXML
    private TableColumn<Product, Double> loggedValue2;

    @FXML
    private TableColumn<Product, String> unitType2;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button startbtn;

    @FXML
    private Label statusLabel;

    private final Object pauseLock = new Object();
    private boolean paused = false;
    private boolean stopped = false;

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
    private void onStopButtonClick() {
        stopped = true;
        statusLabel.setText("Stopped");
    }
    
    @FXML
    private void onPauseButtonClick() {
        paused = !paused;
        if (paused) {
            statusLabel.setText("Paused");
        } else {
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
            statusLabel.setText("Running..");
        }
    }
    
    @FXML
    private void onStartButtonClick() {
        paused = false;
        stopped = false;
        startExecution();
        statusLabel.setText("Running..");
    }
    
    private AtomicInteger tasksCompleted = new AtomicInteger(0);

    private void startExecution() {
        stopped = false;
        paused = false;
        tasksCompleted.set(0);
        Platform.runLater(() -> {
            progressBar.setProgress(0);
            statusLabel.setText("Running...");
        });
        final int totalTasks = 2;
        executeQueryAndDisplayResults(comboBox1.getValue(), table2, totalTasks);
        executeQueryAndDisplayResults(comboBox2.getValue(), table1, totalTasks);
    }
    
    private void executeQueryAndDisplayResults(String queryName, TableView<Product> tableView, int totalTasks) {
        new Thread(() -> {
            ObservableList<Product> results = executeQuery(queryName, fxmlLoginController.connection);
            synchronized (pauseLock) {
                while (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (stopped) return; 
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
    
            Platform.runLater(() -> {
                if (!stopped) {
                    tableView.setItems(results);
                    double progress = (double) tasksCompleted.incrementAndGet() / totalTasks;
                    progressBar.setProgress(progress);
                    if (progress >= 1) {
                        statusLabel.setText("Finished");
                    }
                }
            });
        }).start();
    }

    private ObservableList<Product> executeQuery(String queryName, Connection connection) {
        return switch (queryName) {
            case "MaxLoggedValueRecord" -> SQLQueries.MaxLoggedValueRecord(connection);
            case "lineIdWhereLoggedValueMin" -> SQLQueries.lineIdWhereLoggedValueMin(connection);
            case "LoggedValueEqualsZero" -> SQLQueries.LoggedValueEqualsZero(connection);
            default -> FXCollections.observableArrayList();
        };
    }  

    @FXML
    public void initialize() {
        comboBox1.getItems().addAll("MaxLoggedValueRecord", "lineIdWhereLoggedValueMin", "LoggedValueEqualsZero");
        comboBox1.getSelectionModel().selectFirst();
        comboBox2.getItems().addAll("MaxLoggedValueRecord", "lineIdWhereLoggedValueMin", "LoggedValueEqualsZero");
        comboBox2.getSelectionModel().selectFirst();
  
        configureTableColums();
    }

    
    private void configureTableColums(){
        logId1.setCellValueFactory(new PropertyValueFactory<>("logId"));
        lineId1.setCellValueFactory(new PropertyValueFactory<>("lineId"));
        logTime1.setCellValueFactory(new PropertyValueFactory<>("logTime"));
        loggedValue1.setCellValueFactory(new PropertyValueFactory<>("loggedValue"));
        cmdType1.setCellValueFactory(new PropertyValueFactory<>("cmdType"));
        description1.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitType1.setCellValueFactory(new PropertyValueFactory<>("unitType"));

        logId2.setCellValueFactory(new PropertyValueFactory<>("logId"));
        lineId2.setCellValueFactory(new PropertyValueFactory<>("lineId"));
        logTime2.setCellValueFactory(new PropertyValueFactory<>("logTime"));
        loggedValue2.setCellValueFactory(new PropertyValueFactory<>("loggedValue"));
        cmdType2.setCellValueFactory(new PropertyValueFactory<>("cmdType"));
        description2.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitType2.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        }

}
