package fxml;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import Server.SQLCreateTable;
import Server.SQLDatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import javafx.stage.Stage;

public class fxmlLoginController {
    public static Connection connection;
    static Stage primaryStage;
    @FXML
    private Pane VboxALL;

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
    void handleButtonClicked(ActionEvent event)  {
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
                        SQLDatabaseConnection.executeSqlScript(connection,"sql/information.sql");
                        SQLDatabaseConnection.truncateTable(connection);

                        Parent root = FXMLLoader.load(getClass().getResource("optionsPage.fxml"));
                        Scene optionsScene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(optionsScene);
                        stage.show();
                        
                    }

                    
                }catch(IOException e){
                    e.printStackTrace();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                    
             
            
                
        }

    }

}
