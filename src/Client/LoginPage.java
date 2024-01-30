package Client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;

import Server.SQLCreateTable;
import Server.SQLDatabaseConnection;
import Server.SQLQueries;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage {

    
    public static final Stack<Scene> sceneStack = new Stack<>();
    public static Connection connection;

        public static VBox createLoginVBox(Stage primaryStage) {
        VBox loginVBox = new VBox();
        loginVBox.setPadding(new Insets(10,10,10,10));
        loginVBox.setSpacing(10);
        loginVBox.setAlignment(Pos.CENTER);
            
        Label titleLabel = new Label("Enter Database Connection Details");
        Label userLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField userTextField = new TextField("root");
        userTextField.setPromptText("username");
        GridPane.setConstraints(userTextField, 1, 0);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String user = userTextField.getText();
            String password = passwordField.getText();

            if (!user.isEmpty()) {
                try {
                    connection = SQLDatabaseConnection.connectToDatabase(Server.SQLDatabaseConnection.url, user, password);
                    SQLDatabaseConnection.createDatabaseSQL(connection);
                    SQLDatabaseConnection.useDatabaseSQL(connection);
                    SQLCreateTable.createTable(connection);
                    SQLQueries.truncateTable(connection);
                    SQLDatabaseConnection.executeSqlScript(connection, SQLDatabaseConnection.filePath);

                    OptionsPage.createOptionsPage(primaryStage);
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
    
}
