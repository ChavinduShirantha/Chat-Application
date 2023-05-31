package lk.playTech.liveChat.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;


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
            throw new RuntimeException(e);
        }
    }


    public void imgGalleryOnAction(MouseEvent mouseEvent) throws MalformedURLException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Image");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            printWriter.println(userName + ": " + file.toURI().toURL());
        }
        if (file != null) {
            System.out.println("File Was Selected");
            URL url = file.toURI().toURL();
            System.out.println(url);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 10, 5, 10));
            ImageView imageView = new ImageView();
            Image image = new Image(String.valueOf(url));
            imageView.setImage(image);
            imageView.setFitWidth(75);
            imageView.setFitHeight(75);
            VBox vBox = new VBox(imageView);
            vBox.setAlignment(Pos.CENTER_RIGHT);
            vBox.setPadding(new Insets(5, 10, 5, 5));
            vBoxPane1.getChildren().add(vBox);
            printWriter.flush();
        }

    }

    public void imgEmojiOnAction(MouseEvent mouseEvent) {
    }

    public void run() {
        try {
            while (true) {
                String msg = bufferedReader.readLine();
                System.out.println("Message : " + msg);
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println("cmd : " + cmd);
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]);
                }
                System.out.println("fullMsg : " + fullMsg);
                System.out.println();
                if (cmd.equalsIgnoreCase(userName + ":")) {
                    continue;
                } else if (fullMsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }

                Platform.runLater(() -> {
                    HBox hBox = new HBox();
                    if (fullMsg.toString().endsWith(".png") || fullMsg.toString().endsWith(".jpg") || fullMsg.toString().endsWith(".jpeg") || fullMsg.toString().endsWith(".gif")) {
                        System.out.println(fullMsg);
                        hBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setPadding(new Insets(5, 10, 5, 5));
                        Text text = new Text(cmd + " ");
                        text.setStyle("-fx-font-size: 15px");
                        ImageView imageView = new ImageView();
                        Image image = new Image(String.valueOf(fullMsg));
                        imageView.setImage(image);
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        TextFlow textFlow = new TextFlow(text, imageView);
                        textFlow.setStyle("-fx-background-color: rgb(139,0,139);" + "-fx-background-radius: 15px");
                        textFlow.setPadding(new Insets(5, 10, 5, 10));
                        hBox.getChildren().add(textFlow);
                        vBoxPane1.getChildren().add(hBox);

                    } else {
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setPadding(new Insets(5, 10, 5, 5));
                        Text text = new Text(msg);
                        text.setStyle("-fx-font-size: 20px;" + "-fx-font-family : Cambria");
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setStyle("-fx-background-color: rgb(139,0,139);" + "-fx-background-radius: 15px");
                        textFlow.setPadding(new Insets(5, 10, 5, 10));
                        text.setFill(Color.rgb(225, 225, 225));
                        hBox.getChildren().add(textFlow);
                        vBoxPane1.getChildren().add(hBox);
                    }
                });

            }

            bufferedReader.close();
            printWriter.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imgSendOnAction(MouseEvent mouseEvent) {
        send();
    }

    public void send() {
        String msg = txtClient.getText();
        printWriter.println(userName + ": " + msg);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 20, 5, 10));
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
        if ((msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    public void txtClientOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            send();
        }
    }
}
