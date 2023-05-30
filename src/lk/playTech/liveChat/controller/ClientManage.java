package lk.playTech.liveChat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 5/30/2023-10:55 AM
 **/

public class ClientManage {
    private ArrayList<ClientManage> clients;
    private Socket socket;
    public BufferedReader bufferedReader;
    public PrintWriter printWriter;

    public ClientManage(Socket socket, ArrayList<ClientManage> clients) throws IOException {
        this.clients=clients;
        this.socket=socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);
    }
    public void run() {
        try {
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                if (msg.equalsIgnoreCase("logout")) {
                    break;
                }
                for (ClientManage cl : clients) {
                    cl.printWriter.println(msg);
                    System.out.println(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                printWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
