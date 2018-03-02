package HomeTask8.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class ConnectWindow extends JFrame {
    private Socket sock;
    private ErrorWindow errorWindow = new ErrorWindow();
    private AuthWindow nextWindow;

    public ConnectWindow() {
        setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
        setTitle("connect");
        setLocationRelativeTo(null);
        setSize(new Dimension(250, 150));
        setResizable(false);
        setLayout(new GridLayout(3,1));

        JPanel server_addr_panel = new JPanel();
        server_addr_panel.setLayout(new FlowLayout());
        JTextField server_addr = new JTextField(10);
        JLabel label1 = new JLabel("SERVER_ADDR: ");
        server_addr_panel.add(label1);
        server_addr_panel.add(server_addr);
        add(server_addr_panel);

        JPanel port_panel = new JPanel();
        port_panel.setLayout(new FlowLayout());
        JTextField port = new JTextField(10);
        JLabel label2 = new JLabel("PORT:                  ");
        port_panel.add(label2);
        port_panel.add(port);
        add(port_panel);

        JPanel connect_panel = new JPanel();
        connect_panel.setLayout(new FlowLayout());
        JButton connect = new JButton("Connect");
        connect_panel.add(connect);
        add(connect_panel);

        //connect function
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    errorWindow.setVisible(false);
                    sock = new Socket(server_addr.getText(), Integer.parseInt((port.getText())));
                    //successful connection:
                    setVisible(false);
                    nextWindow = new AuthWindow(sock);
                }
                catch (Exception ex) {
                    errorWindow.setVisible(true);
                }
            }
        });

        setVisible(true);
    }

/*
    public static void main(String[] args) {
        ConnectWindow window = new ConnectWindow();
    }
*/
    private class ErrorWindow extends JFrame{
        public ErrorWindow() {
            // setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
            setTitle("error!");
            setLocationRelativeTo(null);
            setSize(new Dimension(200, 50));
            setResizable(false);
            setLayout(new BorderLayout());
            JButton ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });
            add(ok, BorderLayout.CENTER);
        }
    }
}


