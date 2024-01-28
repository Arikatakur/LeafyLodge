package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Server.RestrictDate;
import Server.SQLQueries;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OutputPage {
        public static void createDatePage(Stage primaryStage){
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
                    SQLQueries.MaxProductionLineID(LoginPage.connection, startDateString, endDateString);
                
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
