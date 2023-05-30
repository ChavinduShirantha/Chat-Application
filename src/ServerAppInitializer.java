import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.playTech.liveChat.controller.ClientManage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 5/29/2023-2:55 PM
 **/

public class ServerAppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static ArrayList<ClientManage> clients = new ArrayList<ClientManage>();
    @Override
    public void start(Stage primaryStage) {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(3000);
            while (true){
                System.out.println("Waiting");
                socket = serverSocket.accept();
                System.out.println("Connected");
                System.out.println("-------------------------------------");
                ClientManage thread = new ClientManage(socket,clients);
                clients.add(thread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
