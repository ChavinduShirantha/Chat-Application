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
import javafx.scene.input.MouseEvent;
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
    private ImageView imgSend;
    @FXML
    private ImageView imgEmoji;
    @FXML
    private ImageView imgGallery;
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
    PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public void initialize() {
        lblUName.setText(userName);
        try {
            socket = new Socket("localhost", 3000);
            System.out.println("Connect With Server");
            System.out.println(userName + " Enter the Chat");
            System.out.println("____________________");
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(dataOutputStream);

            this.start();

        } catch (IOException e) {

        }
    }

    public void imgGalleryOnAction(MouseEvent mouseEvent) {
    }

    public void imgEmojiOnAction(MouseEvent mouseEvent) {
    }

    public void imgSendOnAction(MouseEvent mouseEvent) {
        String msg = txtClient.getText();
        printWriter.println(userName + " : " + msg);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text("Me : " + msg);
        text.setStyle("-fx-font-size: 20px;" + "-fx-font-family : Cambria");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(12,51,108);" + "-fx-background-radius: 15px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.rgb(225, 225, 225));
        hBox.getChildren().add(textFlow);
        vBoxPane1.getChildren().add(hBox);
        printWriter.flush();
        txtClient.setText("");
        if (msg.equalsIgnoreCase("logout")) {
            System.exit(0);
        }
    }
}
