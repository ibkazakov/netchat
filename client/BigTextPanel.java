package HomeTask8.client;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class BigTextPanel extends JPanel {

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:MM:ss");

    private JTextArea textArea = new JTextArea();
    public BigTextPanel() {
        setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(textArea);
        add(jsp, BorderLayout.CENTER);
        textArea.setEditable(false);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void printMessage(String message) {
        textArea.append("(" + formatter.format(new Date()).toString() + ")"
                + " " + ": " + message + "\n");
    }

}
