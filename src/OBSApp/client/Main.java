package OBSApp.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private static Client client;

    static {
        try {
            client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Client getClient() {
        return client;
    }

    public Main() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.UNDECORATED);

        Parent root = FXMLLoader.load(getClass().getResource("formViews/Login.fxml"));
        Scene scene = new Scene(root, 1024, 768);


        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
