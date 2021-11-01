package model.chatFileOperrations;

import model.Message;

import java.util.List;
public interface ChatFileOperations {

    List<Message> loadMessages();
    void writeMessagesToFile(List<Message> messages);


    List<String> loadLoggedUsers();
    void writeLoggedUsersToFile(List<String> users);
}
