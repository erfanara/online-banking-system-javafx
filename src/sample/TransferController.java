package Controllers;

import Form_Views.Message;
import core.Account;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransferController implements Initializable {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    JFXTextField source_user = new JFXTextField();

    @FXML
    JFXTextField target_user = new JFXTextField();

    @FXML
    JFXTextField source_account = new JFXTextField();

    @FXML
    JFXTextField target_account = new JFXTextField();

    @FXML
    JFXTextField amount = new JFXTextField();

    @FXML
    JFXSlider slider = new JFXSlider();

    @FXML
    JFXCheckBox checkBox = new JFXCheckBox();

    Account account;

    public TransferController() throws Exception {
    }

    public void InitAccount(Account account) {
        this.account = account;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        slider.setMax(Double.parseDouble(account.getBalance().toString()));
        checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> target_account.setEditable(!newValue));
    }

    @FXML
    public void DoTransfer(ActionEvent event) {
        if (isValidTransfer()) {
            //the transfer code
        }
    }

    public boolean isValidTransfer() {
        Message message = new Message();

        if (source_user.getText() == null)
            message.AddStatement("لطفا کاربر مبدأ را مشخص کنید!");

        if (target_user.getText() == null && !checkBox.isSelected())
            message.AddStatement("لطفا کاربر مقصد را مشخص کنید!");
        else
            if (source_user.getText().equals(target_user.getText()))
                message.AddStatement("برای انتقال بین حساب های یک کاربر، چک باکس را فعال کنید!");

        if (source_account.getText() == null)
            message.AddStatement("لطفا حساب مبدا را مشخص کنید!");

        if (target_account.getText() == null)
            message.AddStatement("لطفا حساب مقصد را مشخص کنید!");
        else {
            if (source_account.getText().equals(target_account.getText()))
                message.AddStatement("حساب های مبدا و مقصد یکسان وارد شده اند!");
        }

        if (message.getMessage() != null) {
            message.ShowFinalMessage();
            return false;
        }

        return true;
    }

    @FXML
    public void SwitchToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../Form_Views/Dashboard.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToServices(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../Form_Views/Services.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToAccountManagement(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../Form_Views/AccountManagement.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
