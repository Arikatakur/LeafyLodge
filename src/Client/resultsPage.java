package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Server.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class resultsPage {

    public static void OutPutOfMaxLoggedValue(Stage primaryStage, Scene optionsScene) {
        primaryStage.setTitle("Max LoggedValue Record!");
        Label label = new Label(SQLQueries.MaxLoggedValueRecord(Client.loginPage.connection));
        Button backButton = new Button("Back");
        VBox resultVbox = new VBox(label, backButton);
        resultVbox.setAlignment(Pos.CENTER);
        Scene resultScene = new Scene(resultVbox, 500, 50);

        primaryStage.setScene(resultScene);
        primaryStage.show();

        backButton.setOnAction(e -> {
            primaryStage.setScene(optionsScene);
        }); 
    }

    @SuppressWarnings("unchecked")
    public static void OutPutOfTotalLoggedValue(Stage primaryStage, Scene optionsScene){
            primaryStage.setTitle("Input Window");

            TextField startLogIdField = new TextField();
            TextField endLogIdField = new TextField();

            Button backButton = new Button("Back");
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> {
                try {
                    int startLogId = Integer.parseInt(startLogIdField.getText());
                    int endLogId = Integer.parseInt(endLogIdField.getText());
                    
                    primaryStage.setTitle("Pick an option!");

                    Button showCharts = new Button("Show Charts");
                    Button showList = new Button("Show List");
                    
                    HBox OptionsBox = new HBox(10);
                    OptionsBox.getChildren().addAll(showCharts, showList, backButton);
                    OptionsBox.setAlignment(Pos.CENTER);

                    Scene showScene = new Scene(OptionsBox, 250, 170);

                    showCharts.setOnAction(e1 -> {
                       Charts.createBarChartPage(primaryStage, optionsScene, startLogId, endLogId); 
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
                        table.setItems(SQLQueries.TotalLoggedValueForLogID(loginPage.connection,startLogId, endLogId));
                        table.getColumns().addAll(logIdColumn, loggedValueColumn);
;
                        VBox resultVbox = new VBox(table, backButton);
                        Scene resultScene = new Scene (resultVbox, 170, 170);
                        resultVbox.setAlignment(Pos.CENTER);

                        primaryStage.setScene(resultScene);
                        backButton.setOnAction(e2 -> {
                            primaryStage.setScene(showScene);
                        });
                    }); 
                    
                    backButton.setOnAction(e1 -> {
                        primaryStage.setScene(showScene);
                    });


                    primaryStage.setScene(showScene);
                    primaryStage.show();

                    //primaryStage.setTitle("Total logged value based on LineID");
                    //Charts.createBarChartPage(primaryStage, optionsScene, startLogId, endLogId);
                    

                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Please enter valid integer values for Log IDs.");
                    alert.showAndWait();
                }
            });

        primaryStage.show();

        backButton.setOnAction(e -> {
            primaryStage.setScene(optionsScene); 
       });

       HBox buttonBox = new HBox(10);
       buttonBox.getChildren().addAll(submitButton, backButton);
   
       VBox inputLayout = new VBox(
               new Label("Start LogID:"), startLogIdField,
               new Label("End LogID:"), endLogIdField,
               buttonBox 
       );
       inputLayout.setPadding(new Insets(10, 10, 10, 10));
       inputLayout.setSpacing(10);
   
       primaryStage.setScene(new Scene(inputLayout, 250, 170));
       primaryStage.show();
   }

    public static void OutputOfMinLoggedValue(Stage primaryStage, Scene optionsScene){
        primaryStage.setTitle("LineID where logged value is minimum!");

        Button backButton = new Button("Back");
        Label label = new Label(SQLQueries.LindIDwhereLoggedValueMin(loginPage.connection));
        VBox resultVbox = new VBox(label, backButton);
        Scene resultScene = new Scene(resultVbox, 300, 70);
        resultVbox.setAlignment(Pos.CENTER);
        backButton.setPrefSize(100, 30);
        primaryStage.setScene(resultScene);
        primaryStage.show();

        backButton.setOnAction(e -> {
            primaryStage.setScene(optionsScene);
            
        });
    }


    public static void OutputOfMaxProduction(Stage primaryStage, Scene optionsScene){
        primaryStage.setTitle("Production Line Page");

        DatePicker startDatePicker = restrictDate.createRestrictedDatePicker();
        DatePicker endDatePicker = restrictDate.createRestrictedDatePicker();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitButton, backButton);

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
                Label label = new Label(SQLQueries.MaxProductionLineID(loginPage.connection, startDateString, endDateString));
                VBox dateVbox = new VBox(label, backButton);
                Scene scene = new Scene(dateVbox, 500, 50);
                dateVbox.setAlignment(Pos.CENTER);
                primaryStage.setScene(scene);
                backButton.setOnAction(e -> {
                    primaryStage.setScene(optionsScene);
                });
                primaryStage.show();
               
                
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Date Selection");
                alert.setHeaderText("Please select both start and end dates.");
                alert.showAndWait();
            }
        });

        backButton.setOnAction(event -> {
            primaryStage.setScene(optionsScene);
        }); 

        primaryStage.show();
        
    }
        //test method
    @SuppressWarnings("unchecked")
    public static void resultOfLineIdMin(Stage primaryStage, Scene optionsScene){
        primaryStage.setTitle("Line ID When LoggedValue is Minimum!");
        TableView<Product> table;

        TableColumn<Product, String> lineIdColumn = new TableColumn<>("lineID");
        lineIdColumn.setMinWidth(70);
        lineIdColumn.setCellValueFactory(new PropertyValueFactory<>("lineId"));

        TableColumn<Product, Double> loggedValueColumn = new TableColumn<>("Logged Value");
        loggedValueColumn.setMinWidth(50);
        loggedValueColumn.setCellValueFactory(new PropertyValueFactory<>("loggedValue"));

        table = new TableView<>();
        table.setItems(SQLQueries.lineIdWhereLoggedValueMin(loginPage.connection));
        table.getColumns().addAll(lineIdColumn, loggedValueColumn);

        Button backButton = new Button("Back");
        VBox resultVbox = new VBox(table, backButton);
        Scene resultScene = new Scene (resultVbox, 170, 120);
        resultVbox.setAlignment(Pos.CENTER);

        primaryStage.setScene(resultScene);
        backButton.setOnAction(e -> {
            primaryStage.setScene(optionsScene);
        });


    }


    @SuppressWarnings("unchecked")
    public static void resultsOfLoggedValueEqualsZero(Stage primaryStage, Scene optionsScene){
        primaryStage.setTitle("Logged value equals zero");
        TableView<Product> table;

        TableColumn<Product, Integer> logIdColumn = new TableColumn<>("logID");
        logIdColumn.setMinWidth(50);
        logIdColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));
        

        TableColumn<Product, String> lineIdColumn = new TableColumn<>("lineID");
        lineIdColumn.setMinWidth(70);
        lineIdColumn.setCellValueFactory(new PropertyValueFactory<>("lineId"));

        TableColumn<Product, String> logTimeColumn = new TableColumn<>("LogTime");
        logTimeColumn.setMinWidth(170);
        logTimeColumn.setCellValueFactory(new PropertyValueFactory<>("logTime"));

        TableColumn<Product, Double> loggedValueColumn = new TableColumn<>("Logged Value");
        loggedValueColumn.setMinWidth(50);
        loggedValueColumn.setCellValueFactory(new PropertyValueFactory<>("loggedValue"));
       
        table = new TableView<>();
        table.setItems(SQLQueries.LoggedValueEqualsZero(loginPage.connection));
        table.getColumns().addAll(logIdColumn, lineIdColumn, logTimeColumn, loggedValueColumn);

        Button backButton = new Button("Back");
        VBox resultVbox = new VBox(table, backButton);
        Scene resultScene = new Scene (resultVbox);
        resultVbox.setAlignment(Pos.CENTER);

        primaryStage.setScene(resultScene);
        backButton.setOnAction(e -> {
            primaryStage.setScene(optionsScene);
        });

    }
}
