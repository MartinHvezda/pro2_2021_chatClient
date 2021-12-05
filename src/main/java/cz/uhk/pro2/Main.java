package cz.uhk.pro2;

import database.DatabaseChatClient;
import database.DatabaseOperations;
import database.DbInitilazer;
import database.JdbcDatabaseOperations;
import gui.MainFrame;
import model.*;

public class Main {

    public static void main(String[] args) {
        String databaseDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String databaseUrl = "jdbc:derby:ChatClientDB";

        try{
            //ChatClient chatClient = new WebChatClient();

            DbInitilazer dbInitilazer = new DbInitilazer(databaseDriver, databaseUrl);
            //dbInitilazer.init();

            DatabaseOperations databaseOperations = new JdbcDatabaseOperations(databaseDriver, databaseUrl);
            ChatClient chatClient = new DatabaseChatClient(databaseOperations);

            MainFrame mainFrame = new MainFrame(800, 600, chatClient);
            mainFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
