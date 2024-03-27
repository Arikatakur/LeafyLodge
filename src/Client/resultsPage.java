package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Server.*;
import fxml.fxmlLoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class resultsPage {



    @SuppressWarnings("unchecked")
    public static void OutPutOfTotalLoggedValue(Scene optionsScene){
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Input Window");

            ChoiceBox<Integer> startLogIdBox = new ChoiceBox<>();
            ChoiceBox<Integer> endLogIdBox = new ChoiceBox<>();

            endLogIdBox.getItems().addAll(1,3,4,5,6);
            endLogIdBox.setValue(1);
            startLogIdBox.getItems().addAll(1,3,4,5,6);
            startLogIdBox.setValue(1);


            Button backButton = new Button("Back");
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> {
                try {
                    int startLogId = startLogIdBox.getValue();
                    int endLogId = endLogIdBox.getValue();
                    
                    primaryStage.setTitle("Pick an option!");

                    Button showCharts = new Button("Show Charts");
                    Button showList = new Button("Show List");
                    HBox OptionsBox = new HBox(10);
                    OptionsBox.getChildren().addAll(showCharts, showList);
                    OptionsBox.setAlignment(Pos.CENTER);

                    Scene showScene = new Scene(OptionsBox, 250, 170);

                    showCharts.setOnAction(e1 -> {
                       Charts.createBarChartPage(primaryStage, showScene, startLogId, endLogId); 
                    });
                    showList.setOnAction(e1 -> {
                        primaryStage.setTitle("Total loggedValue List based on LogID");
                        TableView<Product> table;

                        TableColumn<Product, Integer> logIdColumn = new TableColumn<>("logID");
                        logIdColumn.setMinWidth(50);
                        logIdColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));
                        TableColumn<Product, Double> loggedValueColumn = new TableColumn<>("Logged Value");
                        loggedValueColumn.setMinWidth(50);
                        loggedValueColumn.setCellValueFactory(new PropertyValueFactory<>("loggedValue"));

                        table = new TableView<>();
                        table.setItems(SQLQueries.TotalLoggedValueForLogID(fxmlLoginController.connection,startLogId, endLogId));
                        table.getColumns().addAll(logIdColumn, loggedValueColumn);

                        VBox resultVbox = new VBox(table);
                        Scene resultScene = new Scene (resultVbox, 200, 200);
                        resultVbox.setAlignment(Pos.CENTER);

                        primaryStage.setScene(resultScene);
                        backButton.setOnAction(e2 -> {
                            primaryStage.setScene(showScene);
                        });
                    }); 

                    primaryStage.setScene(showScene);
                    primaryStage.show();
                    

                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Please enter a valid username or password.");
                    alert.showAndWait();
                }
            });

        primaryStage.show();
       Label startlogIdLabel = new Label("Start LogID:");
       Label endLogIdLabel = new Label("End LogID:");
       HBox buttonBox = new HBox(10);
       buttonBox.getChildren().addAll(submitButton);

       VBox inputLayout = new VBox(startlogIdLabel,startLogIdBox,endLogIdLabel,endLogIdBox,buttonBox);
       inputLayout.setPadding(new Insets(10, 10, 10, 10));
       inputLayout.setSpacing(10);
   
       primaryStage.setScene(new Scene(inputLayout, 250, 170));
       primaryStage.show();
   }

    public static void OutputOfMaxProduction(Scene optionsScene){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Production Line Page");

        DatePicker startDatePicker = restrictDate.createRestrictedDatePicker();
        DatePicker endDatePicker = restrictDate.createRestrictedDatePicker();
        Button submitButton = new Button("Submit");


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitButton);

        Label startLabel = new Label("Start Date");
        Label endLabel = new Label("End Date");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startLabel, startDatePicker, endLabel, endDatePicker, buttonBox);

        Scene dateScene = new Scene(vbox, 300, 200);
        primaryStage.setScene(dateScene);


        submitButton.setOnAction(event -> {

            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (startDate != null && endDate != null) {
                String startDateString = startDate.format(DateTimeFormatter.ISO_DATE);
                String endDateString = endDate.format(DateTimeFormatter.ISO_DATE);

                Stage stage = new Stage();
                stage.setTitle("LineID where production was maximum");
                Label label = new Label(SQLQueries.MaxProductionLineID(fxmlLoginController.connection, startDateString, endDateString));
                VBox dateVbox = new VBox(label);
                Scene scene = new Scene(dateVbox, 500, 50);
                dateVbox.setAlignment(Pos.CENTER);
                primaryStage.setScene(scene);
                primaryStage.show();
                
                
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Date Selection");
                alert.setHeaderText("Please select both start and end dates.");
                alert.showAndWait();
            }
        });

        primaryStage.show();
        
    }

}
