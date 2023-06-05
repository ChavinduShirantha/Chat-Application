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
import javafx.scene.layout.Pane;
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
import java.nio.charset.StandardCharsets;


import static lk.playTech.liveChat.controller.LoginFormController.userName;

/**
 * @author : Chavindu
 * created : 5/29/2023-1:56 PM
 **/

public class ClientFormController extends Thread {
    @FXML
    private ImageView imgsunglassEmoji;
    @FXML
    private ImageView imgthermometerEmoji;
    @FXML
    private ImageView imgHearteyeEmoji;
    @FXML
    private ImageView imgGhostEmoji;
    @FXML
    private ImageView imgexhaustEmoji;
    @FXML
    private ImageView imgmeltingEmoji;
    @FXML
    private ImageView imghideEmoji;
    @FXML
    private ImageView imgZancyEmoji;
    @FXML
    private ImageView imgsneezingEmoji;
    @FXML
    private ImageView imghandoverEmoji;
    @FXML
    private ImageView imgheadEmoji;
    @FXML
    private ImageView imgsalutingEmoji;
    @FXML
    private ImageView imgLikeEmoji;
    @FXML
    private ImageView imgmaskEmoji;
    @FXML
    private ImageView imgmonocoleEmoji;
    @FXML
    private ImageView imgCrossFingersEmoji;
    @FXML
    private ImageView imgcoldEmoji;
    @FXML
    private ImageView imgCelibrateEmoji;
    @FXML
    private ImageView imgClappingEmoji;
    @FXML
    private ImageView imgsleepingEmoji;
    @FXML
    private ImageView imgheartEmoji;
    @FXML
    private ImageView imgCryingEmoji;
    @FXML
    private ImageView imgCryingEmoji1;
    @FXML
    private ImageView imgLaughingEmoji1;
    @FXML
    private ImageView imgLaughingEmoji;
    @FXML
    private ImageView imgHappyWithTearsEmoji;
    @FXML
    private ImageView imgHappyEmoji;
    @FXML
    private ImageView imgAngryEmoji;
    @FXML
    private Pane emojiPane;
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
            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(dataOutputStream);

            this.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        emojiPane.setVisible(false);
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

            Text text = new Text("Me : " + " ");
            text.setStyle("-fx-font-size: 20px;" + "-fx-font-family : Cambria");
            ImageView imageView = new ImageView();
            Image image = new Image(String.valueOf(url));
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            TextFlow textFlow = new TextFlow(text, imageView);
            textFlow.setStyle("-fx-background-color: rgb(12,51,108);" + "-fx-background-radius: 15px");
            text.setFill(Color.rgb(225, 225, 225));
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            textFlow.setMaxWidth(150);
            hBox.getChildren().add(textFlow);

            VBox vBox = new VBox(textFlow);
            vBox.setAlignment(Pos.CENTER_RIGHT);
            vBox.setPadding(new Insets(5, 10, 5, 5));
            vBoxPane1.getChildren().add(vBox);
            printWriter.flush();
        }

    }

    public void imgEmojiOnAction(MouseEvent mouseEvent) {
        emojiPane.setVisible(true);
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
                        text.setStyle("-fx-font-size: 20px;" + "-fx-font-family : Cambria");
                        ImageView imageView = new ImageView();
                        Image image = new Image(String.valueOf(fullMsg));
                        imageView.setImage(image);
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        text.setFill(Color.rgb(225, 225, 225));
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

    public void imgHappyEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE0A");
        emojiPane.setVisible(false);
    }

    public void imgAngryEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE21");
        emojiPane.setVisible(false);
    }

    public void imgHappyWithTearsEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE02");
        emojiPane.setVisible(false);
    }

    public void imgLaughingEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD23");
        emojiPane.setVisible(false);
    }

    public void imgLaughingEmoji1OnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE04");
        emojiPane.setVisible(false);
    }

    public void imgCryingEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE22");
        emojiPane.setVisible(false);
    }

    public void imgCryingEmoji1OnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE2D");
        emojiPane.setVisible(false);
    }

    public void imgsunglassEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE0E");
        emojiPane.setVisible(false);
    }

    public void imgthermometerEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD12");
        emojiPane.setVisible(false);
    }

    public void imgHearteyeEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE0D");
        emojiPane.setVisible(false);
    }

    public void imgGhostEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDC7B");
        emojiPane.setVisible(false);
    }

    public void imgexhaustEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE24");
        emojiPane.setVisible(false);
    }

    public void imgmeltingEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD29");
        emojiPane.setVisible(false);
    }

    public void imghideEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE36");
        emojiPane.setVisible(false);
    }

    public void imgZanyEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD2A");
        emojiPane.setVisible(false);
    }

    public void imgsneezingEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD27");
        emojiPane.setVisible(false);
    }

    public void imghandoverEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD2D");
        emojiPane.setVisible(false);
    }

    public void imgheadEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD2F");
        emojiPane.setVisible(false);
    }

    public void imgsalutingEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDC4B");
        emojiPane.setVisible(false);
    }

    public void imgLikeEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDC4D");
        emojiPane.setVisible(false);
    }

    public void imgmaskEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE37");
        emojiPane.setVisible(false);
    }

    public void imgmonocoleEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDDD0");
        emojiPane.setVisible(false);
    }

    public void imgCrossFingersEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD1E");
        emojiPane.setVisible(false);
    }

    public void imgcoldEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD76");
        emojiPane.setVisible(false);
    }

    public void imgCelibrateEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83E\uDD73");
        emojiPane.setVisible(false);
    }

    public void imgClappingEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDC4F");
        emojiPane.setVisible(false);
    }

    public void imgsleepingEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDE34");
        emojiPane.setVisible(false);
    }

    public void imgheartEmojiOnAction(MouseEvent mouseEvent) {
        txtClient.appendText("\uD83D\uDC96");
        emojiPane.setVisible(false);
    }
}
