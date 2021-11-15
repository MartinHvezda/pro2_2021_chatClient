package cz.uhk.pro2;

import gui.MainFrame;
import model.*;

public class Main {

    public static void main(String[] args) {
        ChatClient chatClient = new WebChatClient();

        MainFrame mainFrame = new MainFrame(800, 600, chatClient);
        mainFrame.setVisible(true);



    }


}
