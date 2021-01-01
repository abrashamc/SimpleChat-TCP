package app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ChatFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextArea textArea = new JTextArea();
    JTextField textField = new JTextField();
    JButton btnSend = new JButton("Send");

    private Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ReceiverThread receiverThread;

    public ChatFrame(String title, Socket socket) {
        setTitle(title);
        this.socket = socket;
        setupSocket();
        prepareFrame();
    }

    private void setupSocket() {
        // 1. Open Streams
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch(Exception e) {}

        // 2. Start the receiver thread (for read purposes)
        receiverThread = new ReceiverThread();
        receiverThread.start();
    }

    private void prepareFrame() {
        Container content = getContentPane();

        Box south = Box.createHorizontalBox();
        south.add(textField);
        south.add(btnSend);

        JScrollPane scrollPane = new JScrollPane(textArea);
        content.add(scrollPane, "Center");
        content.add(south, "South");

        setBounds(100, 100, 300, 300);
        setResizable(false);
        addWindowListener(new WindowHandler());
        btnSend.addActionListener(new SendHandler());

    }

    private void onMessage(String message) {
        if ("quit".equalsIgnoreCase(message)) {
            try {
                dataOutputStream.writeUTF("quit");
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            } catch(Exception e) {}
            dispose();
            receiverThread.interrupt();
        } else {
            String appendedText = textArea.getText() + "\n" + "Other: " + message;
            textArea.setText(appendedText);
        }
    }

    class WindowHandler extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            try {
                dataOutputStream.writeUTF("quit");
            } catch(Exception exp) {}
        }
    }

    class SendHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = textField.getText();
            String appendedText = textArea.getText() + "\n" + "You: " + message;
            textArea.setText(appendedText);

            try {
                dataOutputStream.writeUTF(message);
            }catch(Exception exp) {}
        }
    }

    class ReceiverThread extends Thread {
        public void run() {
            while(! interrupted()) {
                try {
                    String message = dataInputStream.readUTF();
                    onMessage(message);
                } catch(Exception e) {}
            }
        }
    }
}