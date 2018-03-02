package HomeTask8.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class AcceptMessageThread extends Thread {
    private BigTextPanel textPanel;
    private DataInputStream in;
    private SendPanel sendPanel;

    private boolean autorized = false;

    public AcceptMessageThread(BigTextPanel textPanel, InputStream inputStream, SendPanel sendPanel) {
        this.textPanel = textPanel;
        this.sendPanel = sendPanel;
        this.in = new DataInputStream(inputStream);
       // textPanel.printMessage("init", "init");
    }

    public void autorization() throws IOException {
        while (true) {
            String current = in.readUTF();
            if (current.startsWith("/authok")) {
                autorized = true;
                String[] parts = current.split("\\s");
                sendPanel.setMyNickName(parts[1]);
                break;
            }
                textPanel.printMessage(current);
        }
    }

    public void working() throws IOException {
        while (true) {
            try {
                String message = in.readUTF();
                textPanel.printMessage(message);
            }
            catch (IOException e) {
                break;
            }
        }
    }

    @Override
    public void run() {
        try {
            // autorization();
            working();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            autorized = false;
        }
    }

}
