package gui;

import model.ChatClient;
import model.Message;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextField txtInputName, txtInputMessage;
    JButton btnLogin, btnSend;
    JTextArea txtAreaChat;
    JTable tblLoggedUsers;

    LoggedUsersTabelModel loggedUsersTabelModel;

    ChatClient chatClient;

    public MainFrame(int width, int height, ChatClient chatClient) {
        super("PRO02 Chat client 2021");
        this.chatClient = chatClient;
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initGui();
    }

    private void initGui() {
        JPanel panelMain = new JPanel(new BorderLayout());

        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelChat = new JPanel();
        JPanel panelLoggedUsers = new JPanel();
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //Login
        initLogInPanel(panelLogin);
        //konec loginu

        //Chat
       initChatPanel(panelChat);
        //LoggendUsers
        initLoggedUsersPanel(panelChat);
        //patička
        initFooterPanel(panelFooter);


        panelMain.add(panelLogin, BorderLayout.NORTH);
        panelMain.add(panelChat, BorderLayout.CENTER);
        panelMain.add(panelLoggedUsers, BorderLayout.EAST);
        panelMain.add(panelFooter, BorderLayout.SOUTH);
        add(panelMain);
    }

    private void initLogInPanel(JPanel panel) {
        txtInputName = new JTextField("", 30);
        panel.add(new JLabel("Jméno"));
        panel.add(txtInputName);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chatClient.isAuthenticated()){
                    chatClient.logout();
                    btnLogin.setText("Login");
                    txtInputName.setEditable(true);
                    txtAreaChat.setEnabled(false);
                }
                else{
                    String userName = txtInputName.getText();
                    if(userName.length() < 1) {
                        JOptionPane.showMessageDialog(null, "Enter your user name", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    chatClient.login(userName);
                    btnLogin.setText("Logout");
                    txtInputName.setEditable(false);
                    txtAreaChat.setEnabled(true);
                }
            }
        });
        panel.add(btnLogin);
    }
    private void initChatPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        txtAreaChat = new JTextArea();
        txtAreaChat.setAutoscrolls(true);
        txtAreaChat.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaChat);
        chatClient.addActionListenerNewMessages(e -> {
            refreshMessages();
        });
        panel.add(scrollPane);


    }
    private void initFooterPanel(JPanel panel) {
        txtInputMessage = new JTextField("", 50);
        panel.add(txtInputMessage);

        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = txtInputMessage.getText();
                if(text.length() ==0) {
                    return;
                } if(!chatClient.isAuthenticated()) {
                    return;
                }
                chatClient.sendMessage(text);
                txtInputMessage.setText("");
            }
        });
        panel.add(btnSend);
    }

    private void initLoggedUsersPanel(JPanel panel) {
        /*Object[][] data = new Object[][] {
                {"1:1", "1:2"},
                {"2:1", "2:2"},
                {"3:1", "3:2"},
        };
        String[] colNames = new String[] {"Column1", "Column2"};*/

        //tblLoggedUsers = new JTable(data, colNames);
        loggedUsersTabelModel = new LoggedUsersTabelModel(chatClient);

        tblLoggedUsers = new JTable();
        tblLoggedUsers.setModel(loggedUsersTabelModel);

        chatClient.addActionListenerLoggedUsersChanged(e -> {
            loggedUsersTabelModel.fireTableDataChanged();
        });

        JScrollPane scrollPane = new JScrollPane(tblLoggedUsers);
        scrollPane.setPreferredSize(new Dimension(250,500));
        panel.add(scrollPane);

    }

    private void refreshMessages() {
        txtAreaChat.setText("");
        for(Message msg: chatClient.getMessages()) {
            txtAreaChat.append(msg.toString());
            txtAreaChat.append("\n");
        }
    }
}
