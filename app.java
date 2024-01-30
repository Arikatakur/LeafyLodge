import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class app extends Application {

    public void start(Stage primaryStage) {
        VBox loginVBox = Client.LoginPage.createLoginVBox(primaryStage);
        Scene loginScene = new Scene(loginVBox, 300, 200);

        Client.LoginPage.sceneStack.push(loginScene);
        primaryStage.setTitle("Login to Database");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }


    public static void main(String args[]) {


        /*  
            Saleem Yousef - 213523418
            Romaisa Suliman - 207674243  
        */
        
        launch(args);
    }
}
