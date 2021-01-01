package app;

import app.view.ChatFrame;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("Waiting for client...");
            Socket socket = serverSocket.accept();
            ChatFrame chatFrame = new ChatFrame("Server:Window", socket);
            chatFrame.setVisible(true);
        }

    }

}