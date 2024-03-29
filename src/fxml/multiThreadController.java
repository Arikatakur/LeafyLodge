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
    private TableColumn<Product, String> threadNumber;

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
        paused = false;
        stopped = false;
        tasksCompleted.set(0);
        Platform.runLater(() -> {
            progressBar.setProgress(0);
            statusLabel.setText("Running...");
        });
        final int totalTasks = 2;
        executeQueryAndDisplayResults(comboBox1.getValue(), table, "Thread 1", totalTasks);
        executeQueryAndDisplayResults(comboBox2.getValue(), table, "Thread 2", totalTasks);
    }
    private void executeQueryAndDisplayResults(String queryName, TableView<Product> tableView, String threadNumber, int totalTasks) {
        new Thread(() -> {
            ObservableList<Product> results = executeQuery(queryName, fxmlLoginController.connection);
            for (Product product : results) {
                product.setThreadNumber(threadNumber);
            }
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
                    ObservableList<Product> currentItems = tableView.getItems();
                    currentItems.addAll(results);
                    tableView.setItems(currentItems);
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

    comboBox1.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        if (newValue != null && !newValue.equals(oldValue)) {
            clearTableAndResetStatus();
        }
    });

    comboBox2.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        if (newValue != null && !newValue.equals(oldValue)) {
            clearTableAndResetStatus();
        }
    });
    }

    private void clearTableAndResetStatus() {
        table.getItems().clear(); 
        progressBar.setProgress(0); 
        statusLabel.setText("Pending.."); 
        
    }

    
    private void configureTableColums(){

        logId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        lineId.setCellValueFactory(new PropertyValueFactory<>("lineId"));
        logTime.setCellValueFactory(new PropertyValueFactory<>("logTime"));
        loggedValue.setCellValueFactory(new PropertyValueFactory<>("loggedValue"));
        cmdType.setCellValueFactory(new PropertyValueFactory<>("cmdType"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitType.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        threadNumber.setCellValueFactory(new PropertyValueFactory<>("threadNumber"));
    }

}
