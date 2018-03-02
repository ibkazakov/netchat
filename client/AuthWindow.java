package HomeTask8.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class AuthWindow extends JFrame {
    private Socket sock;
    private ErrorWindow errorWindow = new ErrorWindow();
    private Window nextWindow;

    public AuthWindow(Socket sock) {
        this.sock = sock;

        setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
        setTitle("auth");
        setLocationRelativeTo(null);
        setSize(new Dimension(250, 150));
        setResizable(false);
        setLayout(new GridLayout(3,1));

        JPanel login_panel = new JPanel();
        login_panel.setLayout(new FlowLayout());
        JTextField login = new JTextField(10);
        JLabel label3 = new JLabel("LOGIN:                 ");
        login_panel.add(label3);
        login_panel.add(login);
        add(login_panel);

        JPanel password_panel = new JPanel();
        password_panel.setLayout(new FlowLayout());
        JTextField password = new JTextField(10);
        JLabel label4 = new JLabel("PASSWORD:      ");
        password_panel.add(label4);
        password_panel.add(password);
        add(password_panel);

        JPanel submit_panel = new JPanel();
        submit_panel.setLayout(new FlowLayout());
        JButton submit = new JButton("Submit");
        submit_panel.add(submit);
        add(submit_panel);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AuthThread authThread = new AuthThread(sock, login.getText(), password.getText());
                    authThread.start();
                    authThread.join();

                    if (authThread.isAuth()) {
                        //successful auth
                        errorWindow.setVisible(false);
                        nextWindow = new Window(sock.getInputStream(), sock.getOutputStream());
                        setVisible(false);
                    } else {
                        //unsuccessful auth
                        errorWindow.setVisible(true);
                    }
                }
                catch (Exception ex) {

                }
            }
        });

        setVisible(true);
    }

    /*
    public static void main(String[] args) throws Exception {
        AuthWindow auth = new AuthWindow(new Socket("localhost", 8189));
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
