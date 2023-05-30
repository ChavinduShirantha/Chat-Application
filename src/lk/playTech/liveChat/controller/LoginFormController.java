package lk.playTech.liveChat.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 5/29/2023-1:47 PM
 **/

public class LoginFormController {
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXButton btnLogin;
    public static String userName;
    Parent scene;
    public ArrayList<String> Users = new ArrayList();
    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtUserName.getText();
        if (Users.contains(userName)) {
            System.out.println("Already Exists That User Name !..");
            new Alert(Alert.AlertType.ERROR, "Already Exists That User Name !..").show();
        } else {
            Stage stage = new Stage();
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        Users.add(userName);
    }
}
