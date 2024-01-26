import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class app extends Application {

    private Connection connection; 
    private final Stack<Scene> sceneStack = new Stack<>();


    public void start(Stage primaryStage) {
        VBox loginVBox = createLoginVBox(primaryStage);
        Scene loginScene = new Scene(loginVBox, 400, 200);

        sceneStack.push(loginScene);
        primaryStage.setTitle("Login to Database");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private VBox createLoginVBox(Stage primaryStage) {
        VBox loginVBox = new VBox(10);
        loginVBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Enter Database Connection Details");
        Label userLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField userTextField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String user = userTextField.getText();
            String password = passwordField.getText();

            if (!user.isEmpty()) {
                try {
                    connection = SQLDatabaseConnection.connectToDatabase(SQLDatabaseConnection.url, user, password);
                    SQLDatabaseConnection.createDatabaseSQL(connection);
                    SQLDatabaseConnection.useDatabaseSQL(connection);
                    SQLCreateTable.createTable(connection);
                    SQLQueries.truncateTable(connection);
                    SQLDatabaseConnection.executeSqlScript(connection, SQLDatabaseConnection.filePath);

                    createOptionsPage(primaryStage);
                } catch (SQLException ex) {
                        ex.printStackTrace();
                    
                }catch(IOException ex1){
                    ex1.printStackTrace();
                }
            }
        });

        loginVBox.getChildren().addAll(
                titleLabel, userLabel, userTextField,
                passwordLabel, passwordField, loginButton
        );

        return loginVBox;
    }

    private void createOptionsPage(Stage primaryStage) {

        VBox optionsVBox = new VBox(10);
        optionsVBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Welcome to the Database");
        optionsVBox.getChildren().add(titleLabel);


        // Create buttons with different actions
        addButton(optionsVBox, "Option 1", "Print the Maximum value of LoggedValue", () -> {
            SQLQueries.MaxLoggedValueRecord(connection);
        });

        addButton(optionsVBox, "Option 2", "Prints all the LoggedValue based on the LogID", () -> {
            SQLQueries.TotalLoggedValueForLogID(connection, 1, 6);
        });

        addButton(optionsVBox, "Option 3", "Prints the LineID when the LoggedValue is minimum", () -> {
            SQLQueries.LindIDwhereLoggedValueMin(connection);
        });

        addButton(optionsVBox, "Option 4", "Pick a date to display where production was maximum", () -> {
            createDatePage(primaryStage);
        });

        addButton(optionsVBox, "Option 5", "Display when LoggedValue is equal to 0", () -> {
            SQLQueries.LoggedValueEqualsZero(connection);
        });
        addButton(optionsVBox, "Quit", "Close the program!", () -> {
            SQLQueries.truncateTable(connection);
            SQLDatabaseConnection.exitProgram(connection);
        });
        addButton(optionsVBox, "Yousef", "Print Yousef's name", () -> {
            System.out.println("Yousef");
        });

        // Scene for options page
        Scene optionsScene = new Scene(optionsVBox, 400, 300);
        sceneStack.push(optionsScene);

        primaryStage.setTitle("Database options");
        primaryStage.setScene(optionsScene);
    }

    private void addButton(VBox vbox, String buttonText, String description, Runnable action) {
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


    public void createDatePage(Stage primaryStage){
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
        sceneStack.push(dateScene);
        primaryStage.setScene(dateScene);


        submitButton.setOnAction(event -> {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (startDate != null && endDate != null) {
                String startDateString = startDate.format(DateTimeFormatter.ISO_DATE);
                String endDateString = endDate.format(DateTimeFormatter.ISO_DATE);
                    SQLQueries.MaxProductionLineID(connection, startDateString, endDateString);
                
            } else {
                System.out.println("Please select both start and end dates.");
            }
        });

        backButton.setOnAction(event -> {
            if (!sceneStack.isEmpty()) {
                Scene previousScene = sceneStack.pop();
                primaryStage.setScene(previousScene);
            }
        });

        primaryStage.show();
        
    }


    public static void main(String args[]) {
        launch(args);
    }

}
