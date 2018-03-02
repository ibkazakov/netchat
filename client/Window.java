package HomeTask8.client;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;

class Window extends JFrame {
    public Window(InputStream serverInputStream, OutputStream serverOutputStream) {
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("chat");
        setLocationRelativeTo(null);
        setSize(new Dimension(600, 400));
        setResizable(false);

        BigTextPanel textPanel = new BigTextPanel();
        textPanel.setBounds(0,0,600, 325);
        add(textPanel);

        SendPanel sendPanel = new SendPanel(textPanel, "default", serverOutputStream);
        sendPanel.setBounds(0, 325, 600, 75);
        add(sendPanel);

        Thread acceptMessage = new AcceptMessageThread(textPanel, serverInputStream, sendPanel);
        acceptMessage.start();


        setVisible(true);
    }
}
