package cz.uhk.pro2;

import gui.MainFrame;
import model.ChatClient;
import model.InMemoryChatClient;
import model.Message;

public class Main {

    public static void main(String[] args) {
        ChatClient chatClient = new InMemoryChatClient();

        MainFrame mainFrame = new MainFrame(800, 600, chatClient);
        mainFrame.setVisible(true);

        testChat();

    }

    private static void testChat() {
        ChatClient chatClient = new InMemoryChatClient();

        System.out.println("Loging in");
        chatClient.login("Jaroslav Langer");
        System.out.println("user is logged: " + chatClient.isAuthenticated());

        System.out.println("Currently logged users:");
        for (String user: chatClient.getLoggedUsers()) {
            System.out.println(user);
        }

        System.out.println("Sending msg 1");
        chatClient.sendMessage("Hello world");

        System.out.println("sending msg2");
        chatClient.sendMessage("Hello world");

        System.out.println("Messages:");
        for (Message msg: chatClient.getMessages()) {
            System.out.println(msg);
        }
    }
}
