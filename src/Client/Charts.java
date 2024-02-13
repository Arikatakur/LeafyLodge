package Client;

import fxml.fxmlLoginController;
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

        ObservableList<Product> productList = SQLQueries.TotalLoggedValueForLogID(fxmlLoginController.connection,startLogId,endLogId);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        // for (Product product : productList) {
        //     series.getData().add(new XYChart.Data<>(String.valueOf(product.getLogId()), product.getLoggedValue()));
        // }
        for(int i = 0; i < productList.size(); i++){
            Product product = productList.get(i);
            XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(product.getLogId()), product.getLoggedValue());
            if (data.nodeProperty().get() != null) {
                data.nodeProperty().get().setStyle("-fx-bar-fill: " + getColorCode(i));
            }
            series.getData().add(data);
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

    private static String getColorCode(int index) {
        String[] colors = {"#1f78b4", "#33a02c", "#e31a1c", "#ff7f00", "#6a3d9a", "#b15928"};
        return colors[index % colors.length];
    }

}
