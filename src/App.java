import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {

         /*  
            Saleem Yousef - 213523418
            Romaisa Suliman - 207674243  
        */

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox loginVBox = Client.loginPage.createLoginVBox(primaryStage);
        Scene loginScene = new Scene(loginVBox, 300, 220);
        loginScene.getStylesheets().addAll("/Style/style.css");

        primaryStage.setTitle("Login to Database");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}
