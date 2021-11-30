package cz.uhk.pro2;

import database.DatabaseChatClient;
import database.DatabaseOperations;
import database.DbInitilazer;
import database.JdbcDatabaseOperations;
import gui.MainFrame;
import model.*;

public class Main {

    public static void main(String[] args) {
        String databaseDriver = "org.apache.derby.jdbc.EmvededDriver";
        String databaseUrl = "jdbc:derby:ChatClientDb";


            ChatClient chatClient = new WebChatClient();
        try{
            DbInitilazer dbInitilazer = new DbInitilazer(databaseDriver, databaseUrl);
            //dbInitilazer.init();

            DatabaseOperations databaseOperations = new JdbcDatabaseOperations(databaseDriver, databaseUrl);
            chatClient = new DatabaseChatClient(databaseOperations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainFrame mainFrame = new MainFrame(800, 600, chatClient);
        mainFrame.setVisible(true);



    }


}
