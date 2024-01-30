package Client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OptionsPage {
     public static void createOptionsPage(Stage primaryStage) {

        
        VBox optionsVBox = new VBox(10);
        optionsVBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Welcome to the Database");
        optionsVBox.getChildren().add(titleLabel);
        Scene optionsScene = new Scene(optionsVBox, 400, 300);
        LoginPage.sceneStack.push(optionsScene);


        // Create buttons with different actions
        addButton(optionsVBox, "Option 1", "Print the Maximum value of LoggedValue", () -> {
            OutputPage.OutPutOfMaxLoggedValue(primaryStage,optionsScene);
        });

        addButton(optionsVBox, "Option 2", "Prints all the LoggedValue based on the LogID", () -> {
            OutputPage.OutPutOfTotalLoggedValue(primaryStage, optionsScene);
            
        });

        addButton(optionsVBox, "Option 3", "Prints the LineID when the LoggedValue is minimum", () -> {
            OutputPage.OutputOfMinLoggedValue(primaryStage, optionsScene);
        });

        addButton(optionsVBox, "Option 4", "Pick a date to display where production was maximum", () -> {
            OutputPage.OutputOfMaxProduction(primaryStage, optionsScene);
        });

        addButton(optionsVBox, "Option 5", "Display when LoggedValue is equal to 0", () -> {
            OutputPage.OutputOfLoggedValueEqualsZero(primaryStage, optionsScene);
        });
        addButton(optionsVBox, "Quit", "Close the program!", () -> {
            Server.SQLDatabaseConnection.exitProgram(LoginPage.connection);
        });

        // Scene for options page
        

        primaryStage.setTitle("Database options");
        primaryStage.setScene(optionsScene);
    }

    private static void addButton(VBox vbox, String buttonText, String description, Runnable action) {
        HBox buttonBox = new HBox(10); 
        buttonBox.setAlignment(Pos.CENTER_LEFT); 

        Button button = new Button(buttonText);

        button.setOnAction(e -> {
            System.out.println(buttonText + " clicked");
            System.out.println("Description: " + description);
            action.run();
        });

        Tooltip tooltip = new Tooltip(description);
        button.setTooltip(tooltip);

        Label descriptionLabel = new Label(description);

        buttonBox.getChildren().addAll(button, descriptionLabel);

        vbox.getChildren().addAll(buttonBox);
    }   
}
