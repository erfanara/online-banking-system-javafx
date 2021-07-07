package OBSApp.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static Client client = new Client();

    final double[] xOffset = new double[1];
    final double[] yOffset = new double[1];

    public static Client getClient() {
        return client;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //debugging purposes:
//        client.loginRequest("123456789","testtest321");
        Parent root = FXMLLoader.load(getClass().getResource("formViews/Login.fxml"));
        Scene scene = new Scene(root, 1024, 768);
        scene.setFill(Color.TRANSPARENT);


        root.setOnMousePressed(event -> { // for dragging the window
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset[0]);
            primaryStage.setY(event.getScreenY() - yOffset[0]);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
