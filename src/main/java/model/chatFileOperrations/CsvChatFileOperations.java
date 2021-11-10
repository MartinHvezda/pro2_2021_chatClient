package model.chatFileOperrations;

import com.google.gson.Gson;
import model.Message;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvChatFileOperations implements ChatFileOperations {

    private static final String MESSAGES_PATH = "./messages.csv";
    private static final String USERS_PATH = "./users.csv";

    Gson gson;

    public CsvChatFileOperations() {

    }

    @Override
    public List<Message> loadMessages() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MESSAGES_PATH));
            String line;
            List<Message> messages = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                Message message = new Message(values[0], values[2], LocalDateTime.parse(values[1]));
                messages.add(message);
            }
            return messages;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void writeMessagesToFile(List<Message> messages) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(MESSAGES_PATH));
            for (Message message : messages){
                String[] values = {message.getAuthor(), message.getCreated().toString(), message.getText()};
                writer.append(String.join(";", values));
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> loadLoggedUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USERS_PATH));
            String line;
            List<String> users = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                users.add(line.split(";")[0]);
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void writeLoggedUsersToFile(List<String> users) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_PATH));
            for (String user : users){
                writer.append(user).append(";");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
