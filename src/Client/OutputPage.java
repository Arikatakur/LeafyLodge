package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Server.RestrictDate;
import Server.SQLQueries;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OutputPage {

    public static void OutPutOfMaxLoggedValue() {
        Stage MaxLoggedValue = new Stage();
        
        MaxLoggedValue.setTitle("Max LoggedValue Record!");
        Label label = new Label(Server.SQLQueries.MaxLoggedValueRecord(Client.LoginPage.connection));
        Scene scene = new Scene(label, 500, 200);
    //    LoginPage.sceneStack.push(scene);

        MaxLoggedValue.setScene(scene);
        MaxLoggedValue.show();
    }

    public static void OutPutOfTotalLoggedValue(){
        Stage TotalLoggedValue = new Stage();
        TotalLoggedValue.setTitle("Total logged value based on LineID!");

        Label label = new Label(Server.SQLQueries.TotalLoggedValueForLogID(LoginPage.connection, 1, 6));
        Scene scene = new Scene(label, 500, 500);
    //    LoginPage.sceneStack.push(scene);

        TotalLoggedValue.setScene(scene);
        TotalLoggedValue.show();
    }

    public static void OutputOfMinLoggedValue(){
        Stage stage = new Stage();
        stage.setTitle("LineID where logged value is minimum!");

        Label label = new Label("");
        Scene scene = new Scene(label, 500, 500);

        stage.setScene(scene);
        stage.show();
    }

    // public static void OutputOfMaxProduction(){
    //     Stage stage = new Stage();
    //     stage.setTitle("LineID where production was maximum");

    //     Label label = new Label();
    //     Scene scene = new Scene(label, 500, 500);

    //     stage.setScene(scene);
    //     stage.show();

    // }

    public static void OutputOfLoggedValueEqualsZero(){
        Stage stage = new Stage();
        stage.setTitle("Logged value equals zero");

        Label label = new Label(SQLQueries.LoggedValueEqualsZero(LoginPage.connection));
        Scene scene = new Scene(label, 500, 500);

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
                Scene scene = new Scene(label, 500, 500);
        
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
