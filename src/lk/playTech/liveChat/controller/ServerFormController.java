package lk.playTech.liveChat.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : Chavindu
 * created : 5/29/2023-1:57 PM
 **/

public class ServerFormController {
    @FXML
    private TextField txtServer;
    @FXML
    private JFXButton btnSend;
    @FXML
    private TextArea ServerTextField;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";

    public String name=LoginFormController.userName;
    public void initialize(){
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(3000);
                ServerTextField.appendText("Server Started");
                socket = serverSocket.accept();
                ServerTextField.appendText("\nClient Connected");
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!message.equals("finish")) {
                    message = dataInputStream.readUTF();
                    ServerTextField.appendText("\nClient : " + message);
                }
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        try {
            dataOutputStream.writeUTF(txtServer.getText().trim());
            dataOutputStream.flush();
            txtServer.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
