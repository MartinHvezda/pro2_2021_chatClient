package model;

import java.util.List;
import java.util.ArrayList;

public class InMemoryChatClient implements ChatClient{
    private String loggedUser;
    private List<Message> messages;
    private List<String> loggedUsers;

    public InMemoryChatClient() {
        messages = new ArrayList<>();
        loggedUser = new ArrayList<>();
    }

    @Override
    public Boolean isAuthenticated() {
        return loggedUser!=null;
    }

    @Override
    public void login(String userName) {
        loggedUser = userName;
        loggedUsers = loggedUsers.add(userName);
    }

    @Override
    public void logout() {
        loggedUsers.remove(loggedUser);
        loggedUser = null;
    }

    @Override
    public void sendMessage(String text) {

    }

    @Override
    public List<String> getLoggedUsers() {
        return null;
    }

    @Override
    public List<String> getMessages() {
        return null;
    }
}
