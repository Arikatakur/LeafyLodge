package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

import Server.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OutputPage {

    public static void OutPutOfMaxLoggedValue() {
        Stage MaxLoggedValue = new Stage();
        
        MaxLoggedValue.setTitle("Max LoggedValue Record!");
        Label label = new Label(SQLQueries.MaxLoggedValueRecord(Client.LoginPage.connection));
        Scene scene = new Scene(label, 500, 50);
    //    LoginPage.sceneStack.push(scene);

        MaxLoggedValue.setScene(scene);
        MaxLoggedValue.show();
    }

    public static void OutPutOfTotalLoggedValue(){
        Stage inputStage = new Stage();
            inputStage.setTitle("Input Window");

            TextField startLogIdField = new TextField();
            TextField endLogIdField = new TextField();

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> {
                int startLogId = Integer.parseInt(startLogIdField.getText());
                int endLogId = Integer.parseInt(endLogIdField.getText());

                List<String> resultsList = Server.SQLQueries.TotalLoggedValueForLogID(LoginPage.connection, startLogId, endLogId);

                Stage resultsStage = new Stage();
                resultsStage.setTitle("Total logged value based on LineID");

                String resultsString = String.join("\n", resultsList);
                Label label = new Label(resultsString);
                Scene scene = new Scene(label, 300, 150);
                resultsStage.setScene(scene);
                resultsStage.show();

                inputStage.close();
        });

        VBox inputLayout = new VBox(new Label("Start LogID:"), startLogIdField, new Label("End LogID:"), endLogIdField, submitButton);
        inputStage.setScene(new Scene(inputLayout, 200, 150));
        inputStage.show();
    }

    public static void OutputOfMinLoggedValue(){
        Stage stage = new Stage();
        stage.setTitle("LineID where logged value is minimum!");

        Label label = new Label(SQLQueries.LindIDwhereLoggedValueMin(LoginPage.connection));
        Scene scene = new Scene(label, 500, 50);

        stage.setScene(scene);
        stage.show();
    }

    public static void OutputOfLoggedValueEqualsZero(){
        Stage stage = new Stage();
        stage.setTitle("Logged value equals zero");
        List<String> resultList = SQLQueries.LoggedValueEqualsZero(LoginPage.connection);
        String resulString = String.join("\n", resultList);
        Label label = new Label(resulString);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(label);
        Scene scene = new Scene(new VBox(scrollPane),500, 400);

        

        stage.setScene(scene);
        stage.show();
    }

    public static void OutputOfMaxProduction(Stage primaryStage){
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
                Scene scene = new Scene(label, 500, 50);
        
                stage.setScene(scene);
                stage.show();
               
                
            } else {
                System.out.println("Please select both start and end dates.");
            }
        });

        backButton.setOnAction(event -> {
            if (!LoginPage.sceneStack.isEmpty()) {
                Scene previousScene = LoginPage.sceneStack.pop();
                primaryStage.setScene(previousScene);
            }
        });

        primaryStage.show();
        
    }

}
