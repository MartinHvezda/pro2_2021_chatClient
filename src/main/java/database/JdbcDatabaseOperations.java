package database;

import model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDatabaseOperations implements  DatabaseOperations{
    private final Connection connection;
    private Statement statement;

    public JdbcDatabaseOperations(String driver, String url) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
            connection = DriverManager.getConnection(url);

    }

    @Override
    public void addMessage(Message message) {
        try{
            statement = connection.createStatement();

            String sqlInsert = "INSERT INTO ChatMessages (author, text, created) VALUES (" + "'" + message.getAuthor() +"', " +
                    "'" + message.getText() + "', " + "'" + Timestamp.valueOf(message.getCreated()) + "')";
            statement.executeUpdate(sqlInsert);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        try{
            statement = connection.createStatement();

            String sqlSelect = "SELECT author, text, created FROM ChatMessages";
            ResultSet resultSet = statement.executeQuery(sqlSelect);

            while(resultSet.next()) {
                Message messageItem = new Message(resultSet.getString("author"),
                        resultSet.getString("text"),
                        resultSet.getTimestamp("created").toLocalDateTime());
                messages.add(messageItem);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
}
