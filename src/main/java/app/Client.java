package app;

import app.view.ChatFrame;

import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 9000);
        ChatFrame chatFrame = new ChatFrame("Client:Window", socket);
        chatFrame.setVisible(true);
    }

}