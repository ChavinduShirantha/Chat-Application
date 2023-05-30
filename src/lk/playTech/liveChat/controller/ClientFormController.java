package lk.playTech.liveChat.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static lk.playTech.liveChat.controller.LoginFormController.userName;

/**
 * @author : Chavindu
 * created : 5/29/2023-1:56 PM
 **/

public class ClientFormController extends Thread {
    @FXML
    private TextArea ClientTextArea;
    @FXML
    private TextField txtClient;
    @FXML
    private JFXButton btnSend;
    @FXML
    private Label lblUName;
    @FXML
    private VBox vBoxPane1;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message = "";

    public void initialize() {
        lblUName.setText(userName);
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3000);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (!message.equals("finish")) {
                    message = dataInputStream.readUTF();
                    ClientTextArea.appendText("\nServer : " + message);
                }
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        try {
            dataOutputStream.writeUTF(txtClient.getText().trim());
            dataOutputStream.flush();
            txtClient.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
