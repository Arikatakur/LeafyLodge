package fxml;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import Server.Exceptions;
import Server.SQLCreateTable;
import Server.SQLDatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import javafx.stage.Stage;

public class fxmlLoginController {
    public static Connection connection;
    static Stage primaryStage;
    @FXML
    private Pane VboxALL;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label WebsiteLabel;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHide;

    @FXML
    private Button btnLogin;

    @FXML
    private Label dblabel;

    @FXML
    private ImageView dblogo;

    @FXML
    private Label leafylodgelabel;

    @FXML
    private Line line1;

    @FXML
    private Line line2;

    @FXML
    private Label loginlabel;

    @FXML
    private PasswordField pwTxtField;

    @FXML
    private TextField usernametxtfield;
    

    @FXML
    void handleButtonClicked(ActionEvent event) throws Exceptions {
        Alert alert;
        if(event.getSource().equals(btnExit)){
            System.exit(0);
        } else if (event.getSource() == btnHide) {
            Stage stage = (Stage) btnHide.getScene().getWindow();
            stage.setIconified(true);
        } else if (event.getSource().equals(btnLogin)) {
    
            String user = usernametxtfield.getText();
            String password = pwTxtField.getText();
                try{
                    if (!user.isEmpty()) {
                        connection = SQLDatabaseConnection.connectToDatabase(Server.SQLDatabaseConnection.url, user, password);
                        SQLDatabaseConnection.createDatabaseSQL(connection);
                        SQLDatabaseConnection.useDatabaseSQL(connection);
                        SQLCreateTable.createTable(connection);
                        SQLDatabaseConnection.truncateTable(connection);
                        SQLDatabaseConnection.executeSqlScript(connection,SQLDatabaseConnection.filePath);

                        Parent root = FXMLLoader.load(getClass().getResource("general.fxml"));
                        Scene optionsScene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(optionsScene);
                        stage.show();
                        
                    }

                    
                }catch(IOException e){
                    e.printStackTrace();
                } catch (SQLException e) {
                    if (e.getMessage().contains("Access denied for user")) {
                        // Show an alert with a custom message
                        alert = errorAlert("Input Error", "Invalid Input", "Please enter a valid username or password.");
                        alert.showAndWait();
                    } else {
                        alert = errorAlert("Database Error", null , "A database error occurred. Please contact support.");
                        alert.showAndWait();

                    }
                } catch (Exception e) {
                    // Handle non-SQL exceptions
                    alert = errorAlert("Input Error", "Invalid Input" , "Please enter a valid username or password.");
                    alert.showAndWait();

                }   
            }
        }
        public static Alert errorAlert(String title, String header, String content){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            // alert.showAndWait();

            return alert;
        }

        @FXML
    public void initialize() {
        makeStageDraggable();
    }

    private double xOffset = 0;
    private double yOffset = 0;

    private void makeStageDraggable() {
        // Assuming mainLayout is the container you wish to attach the drag functionality to
        mainPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        mainPane.setOnMouseDragged(event -> {
            // Get the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Update the stage position based on the drag
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
    }
