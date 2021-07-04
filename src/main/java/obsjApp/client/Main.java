package obsjApp.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        Parent root = FXMLLoader.load(getClass().getResource("formViews/test.fxml"));

        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
