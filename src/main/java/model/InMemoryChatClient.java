package model;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class InMemoryChatClient implements ChatClient{
    private String loggedUser;
    private List<Message> messages;
    private List<String> loggedUsers;

    private List<ActionListener> listenersLoggedUsersChanged = new ArrayList<>();
    private List<ActionListener> listenersNewMessages = new ArrayList<>();

    public InMemoryChatClient() {
        messages = new ArrayList<>();
        loggedUsers = new ArrayList<>();
    }

    @Override
    public Boolean isAuthenticated() {
        return loggedUser!=null;
    }

    @Override
    public void login(String userName) {
        messages.add(new Message(Message.USER_LOGGED_IN, userName));
        loggedUser = userName;
        loggedUsers.add(userName);
        raiseEventLoggedUsersChanged();
        raiseEventNewMessages();
    }

    @Override
    public void logout() {
        messages.add(new Message(Message.USER_LOGGED_OUT, loggedUser));
        loggedUsers.remove(loggedUser);
        loggedUser = null;
        raiseEventLoggedUsersChanged();
        raiseEventNewMessages();
    }

    @Override
    public void sendMessage(String text) {
        messages.add(new Message(loggedUser, text));
        raiseEventNewMessages();
    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addActionListenerLoggedUsersChanged(ActionListener toAdd) {
        listenersLoggedUsersChanged.add(toAdd);
    }

    @Override
    public void addActionListenerNewMessages(ActionListener toAdd) {
        listenersNewMessages.add(toAdd);
    }

    private void raiseEventLoggedUsersChanged() {
        for (ActionListener al: listenersLoggedUsersChanged) {
            al.actionPerformed(new ActionEvent(this, 1, "listenersLoggedUsers"));
        }
    }

    private void raiseEventNewMessages() {
        for(ActionListener al: listenersNewMessages) {
            al.actionPerformed(new ActionEvent(this, 1, "listenersNewMessages"));
        }
    }
}
