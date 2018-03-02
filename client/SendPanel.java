package HomeTask8.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class SendPanel extends JPanel {
    private BigTextPanel textPanel;
    private JTextField textField = new JTextField(46);
    private JButton sendButton = new JButton("Send");
    private String myNickName;

    public void setMyNickName(String myNickName) {
        this.myNickName = myNickName;
    }

    //connection to server
     private DataOutputStream out;

   public SendPanel(BigTextPanel textPanel, String myNickName, OutputStream serverOutput) {
       this.myNickName = myNickName;
       this.textPanel = textPanel;
       this.out = new DataOutputStream(serverOutput);

       setLayout(new FlowLayout());

       add(textField);
       add(sendButton);

       sendButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               sendText();
           }
       });

       textField.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               sendText();
           }
       });

   }


   private void sendText() {
       if (!("".equals(textField.getText())) && textField.getText() != null) {
           //sending to server
           try {
               out.writeUTF(textField.getText());
               // textPanel.printMessage(textField.getText(), myNickName);
               textField.setText(null);
           } catch (IOException e) {
               System.out.println("Ошибка отправки сообщения");
           }
       }
   }
}
