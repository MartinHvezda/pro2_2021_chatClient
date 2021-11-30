package database;

import com.sun.jndi.ldap.Connection;
import model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDatabaseOperations implements  DatabaseOperations{
    private final Connection connection;
    private Statement statement;

    public JdbcDatabaseOperations(String driver, String url) {
        Class.forName(driver);
            connection = DriverManager.getConnection(url);

    }

    @Override
    public void addMessage(Message message) {
        try{
            statement = connection.createStatement();

            String sqlInsert = "INSERT INTO ChatClient (author, text, created) VALUES(" + "'" + message.getAuthor() +"'," +
                    "'" + message.getText() + "'," + "'" + Timestamp.valueOf(message.getCreated()) + "')";
            statement.executeUpdate(sqlInsert);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        try{
            statement = connection.createStatement();
            String sqlSelect = "SELECT author, text, createrd FROM ChatMessages";
            ResultSet resultSet = statement.executeQuery(sqlSelect);

            while(resultSet.next()) {
                Message messageItem = new Message(resultSet);
                messages.add(messageItem);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
}
