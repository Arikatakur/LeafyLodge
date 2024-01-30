package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;


import Server.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OutputPage {

    public static void OutPutOfMaxLoggedValue(Stage primaryStage, Scene optionsScene) {
        primaryStage.setTitle("Max LoggedValue Record!");
        Label label = new Label(SQLQueries.MaxLoggedValueRecord(Client.LoginPage.connection));
        Button backButton = new Button("Back");
        VBox resultVbox = new VBox(label, backButton);
        resultVbox.setAlignment(Pos.CENTER);
        Scene resultScene = new Scene(resultVbox, 500, 50);
    //    LoginPage.sceneStack.push(scene);

        primaryStage.setScene(resultScene);
        primaryStage.show();

        backButton.setOnAction(e -> {
            primaryStage.setScene(optionsScene);
        }); 
    }

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

                List<String> resultsList = Server.SQLQueries.TotalLoggedValueForLogID(LoginPage.connection, startLogId, endLogId);

                primaryStage.setTitle("Total logged value based on LineID");

                String resultsString = String.join("\n", resultsList);
                Label label = new Label(resultsString);
                VBox resultVBox = new VBox(label, backButton);
                Scene scene = new Scene(resultVBox, 300, 150);

                resultVBox.setAlignment(Pos.CENTER);
                primaryStage.setScene(scene);

                backButton.setOnAction(event -> {
                    primaryStage.setScene(optionsScene);
                });

                primaryStage.show();

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
        Label label = new Label(SQLQueries.LindIDwhereLoggedValueMin(LoginPage.connection));
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

    public static void OutputOfLoggedValueEqualsZero(Stage primaryStage, Scene optionsScene){
        primaryStage.setTitle("Logged value equals zero");

        List<String> resultList = SQLQueries.LoggedValueEqualsZero(LoginPage.connection);

        Button backButton = new Button("Back");
        ScrollPane scrollPane = new ScrollPane();
        VBox resultVbox = new VBox(scrollPane, backButton);
        Scene resultScene = new Scene (resultVbox ,500, 400);
        resultVbox.setAlignment(Pos.CENTER);
        backButton.setPrefSize(100, 50);
        String resulString = String.join("\n", resultList);

        Label label = new Label(resulString);
        scrollPane.setContent(label);
                
        primaryStage.setScene(resultScene);
        
        backButton.setOnAction(e -> {
            primaryStage.setScene(optionsScene);
        });
        


    }

    public static void OutputOfMaxProduction(Stage primaryStage, Scene optionsScene){
        primaryStage.setTitle("Production Line Page");

        DatePicker startDatePicker = RestrictDate.createRestrictedDatePicker();
        DatePicker endDatePicker = RestrictDate.createRestrictedDatePicker();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitButton, backButton);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startDatePicker, endDatePicker, buttonBox);

        Scene dateScene = new Scene(vbox, 300, 200);
        LoginPage.sceneStack.push(dateScene);
        primaryStage.setScene(dateScene);


        submitButton.setOnAction(event -> {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (startDate != null && endDate != null) {
                String startDateString = startDate.format(DateTimeFormatter.ISO_DATE);
                String endDateString = endDate.format(DateTimeFormatter.ISO_DATE);

                Stage stage = new Stage();
                stage.setTitle("LineID where production was maximum");
                Label label = new Label(SQLQueries.MaxProductionLineID(LoginPage.connection, startDateString, endDateString));
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

}
