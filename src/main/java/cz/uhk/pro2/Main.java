package cz.uhk.pro2;

import gui.MainFrame;
import model.ChatClient;
import model.InMemoryChatClient;
import model.Message;
import model.ToFileChatClient;

public class Main {

    public static void main(String[] args) {
        ChatClient chatClient = new ToFileChatClient();

        MainFrame mainFrame = new MainFrame(800, 600, chatClient);
        mainFrame.setVisible(true);



    }


}
