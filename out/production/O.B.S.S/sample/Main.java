package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("object.java"));

        //Test
        User reza = new User("Reza", "aezami", "0925", "0915", "dgd@email.com");
        reza.getAccounts().add(new Account(9111));
        reza.getAccounts().add(new Account(9112));
        reza.getAccounts().add(new Account(9113));

        User ali = new User("Ali", "Alian", "0930", "0920", "aba@email.com");
        ali.getAccounts().add(new Account(9121));
        ali.getAccounts().add(new Account(9122));
        ali.getAccounts().add(new Account(9123));

        stream.writeObject(reza);
        stream.writeObject(ali);
        stream.close();

        launch(args);
    }
}
