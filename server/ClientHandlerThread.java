package HomeTask8.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientHandlerThread extends Thread {
    private DataInputStream in;
    private MyServer myServer;
    private ClientHandler clientHandler;
    private Socket socket;

    public ClientHandlerThread(DataInputStream in, MyServer myServer,
                               ClientHandler clientHandler, Socket socket) {
        this.in = in;
        this.myServer = myServer;
        this.clientHandler = clientHandler;
        this.socket = socket;
    }

    private void autorization() throws IOException {
        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String parts[] = str.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);

                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        clientHandler.setName(nick); //give nick!
                        clientHandler.sendMsg("/authok " + nick);
                        myServer.broadcastMsg(nick + " зашел в чат");
                        myServer.subscribe(clientHandler);
                        break;
                    }
                    else {
                        clientHandler.sendMsg("Учетная запись уже используется.");
                    }

                } else {
                    clientHandler.sendMsg("Неверный логин и пароль!");
                }
            }
        }
    }

    private void accept_messages() throws IOException {
        while(true) {
            try {
                String str = in.readUTF();
                System.out.println("from: " + clientHandler.getName() + ": " + str);
                if (str.equals("/end")) {
                    System.out.println(clientHandler.getName() + " отключился");
                    break;
                }

                // myServer.broadcastMsg(clientHandler.getName() + ": " + str);

                //personal messages!
                if (str.startsWith("/w ")) {
                    String[] tokens = str.split("\\s");
                    String nick = tokens[1];
                    String msg;
                    try {
                        msg = str.substring(4 + nick.length());
                    }
                    catch (Exception e) {
                        msg = null;
                    }
                    if (msg != null) {
                        myServer.sendMsgToClient(clientHandler, nick, msg);
                    }
                } else {
                    myServer.broadcastMsg(clientHandler.getName() + ": " + str);
                }
            }

            catch (SocketException e) {
                System.out.println(clientHandler.getName() + " отключился");
                break;
            }
        }
    }
    @Override
    public void run() {
        try {
            socket.setSoTimeout(1000 * 120); //hometask8.2
            autorization();
            socket.setSoTimeout(0); //save from timeout
            accept_messages();
        }
        catch (SocketTimeoutException e) {
            System.out.println("Timeout!");
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            myServer.unsubscribe(clientHandler);
            myServer.broadcastMsg(clientHandler.getName() + " вышел из чата.");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
