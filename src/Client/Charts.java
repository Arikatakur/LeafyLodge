package Client;

import Server.Product;
import Server.SQLQueries;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Charts {
    
    public static void createBarChartPage(Stage primaryStage, Scene optionScene, int startLogId, int endLogId){

        Button backButton = new Button("Back");
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        ObservableList<Product> productList = SQLQueries.TotalLoggedValueForLogID(loginPage.connection,startLogId,endLogId);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Product product : productList) {
            series.getData().add(new XYChart.Data<>(String.valueOf(product.getLogId()), product.getLoggedValue()));
        }

        barChart.getData().add(series);

        VBox vbox = new VBox(barChart, backButton);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 600, 400);

        primaryStage.setTitle("Total loggedValue BarChart based on LineID");
        primaryStage.setScene(scene);

        backButton.setOnAction(e -> {
            primaryStage.setScene(optionScene);
        });
        
        primaryStage.show();

    }

}
