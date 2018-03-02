package HomeTask8.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class AuthThread extends Thread {
    private Socket sock;

    private String login;
    private String pass;

    private boolean isAuth = false;

    private DataInputStream in;
    private DataOutputStream out;

    public boolean isAuth() {
        return isAuth;
    }

    public AuthThread(Socket sock, String login, String pass) throws IOException {
        this.sock = sock;
        this.in = new DataInputStream(sock.getInputStream());
        this.out = new DataOutputStream(sock.getOutputStream());
        this.login = login;
        this.pass = pass;
    }


    @Override
    public void run() {
        try {
            out.writeUTF("/auth " + login + " " + pass);
           // System.out.println("/auth " + login + " " + pass);
            while(true) {
                String message = in.readUTF();
                    if (message.startsWith("/authok")) {
                        isAuth = true;
                        break;
                } else break;

            }
        }
        catch (IOException e) {
            System.exit(0); //close if disconnected! particularly if timeout
        }
    }
}
