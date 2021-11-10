package model.chatFileOperrations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonChatFileOperations implements  ChatFileOperations {

    private static final String MESSAGES_PATH = "./messages.json";
    private static final String USERS_PATH = "./users.json";

    Gson gson;

    public JsonChatFileOperations() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<Message> loadMessages() {
        try{
            List<Message> messages;
            BufferedReader reader = new BufferedReader(new FileReader(USERS_PATH));
            StringBuilder jsonText = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                jsonText.append(line);
            }

            Type targetType = new TypeToken<ArrayList<Message>>(){}.getType();

            messages = gson.fromJson(jsonText.toString(), targetType);
            return messages;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void writeMessagesToFile(List<Message> messages) {
        String jsonText = gson.toJson(messages);

        try {
            FileWriter writer = new FileWriter(MESSAGES_PATH);
            writer.write(jsonText);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> loadLoggedUsers() {
        try{
            List<String> loggedUsers;
            BufferedReader reader = new BufferedReader(new FileReader(USERS_PATH));
            StringBuilder jsonText = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                jsonText.append(line);
            }

            Type targetType = new TypeToken<ArrayList<Message>>(){}.getType();

            loggedUsers = gson.fromJson(jsonText.toString(), targetType);
            return loggedUsers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    @Override
    public void writeLoggedUsersToFile(List<String> users) {
        String jsonText = gson.toJson(users);

        try {
            FileWriter writer= new FileWriter(MESSAGES_PATH);
            writer.write(jsonText);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
