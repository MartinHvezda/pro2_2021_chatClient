package database;

import com.sun.jndi.ldap.Connection;
import model.ChatClient;
import model.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DatabaseChatClient implements ChatClient {
    private String loggedUser;
    private final List<Message> messages;
    private final List<String> loggedUsers;

    private final List<ActionListener> listenersLoggedUsersChanged = new ArrayList<>();
    private final List<ActionListener> listenersNewMessages = new ArrayList<>();
    //implementace DatabaseOperations interface
    private DatabaseOperations databaseOperations;

    public DatabaseChatClient(DatabaseOperations databaseOperations) {
        this.databaseOperations = databaseOperations;
        messages = databaseOperations.getMessages();
        loggedUsers = new ArrayList<>();
    }

    @Override
    public Boolean isAuthenticated() {
        return loggedUser!=null;
    }

    @Override
    public void login(String userName) {
        loggedUser = userName;
        loggedUsers.add(userName);
        //raiseEventMessagesNewMessages
        raiseEventLoggedUsersChanged();
        addMessage(new Message(Message.USER_LOGGED_IN, userName));
    }

    @Override
    public void logout() {
        addMessage(new Message(Message.USER_LOGGED_OUT, loggedUser));
        loggedUsers.remove(loggedUser);
        loggedUser = null;
        raiseEventLoggedUsersChanged();
        //raiseEventMessagesNewMessages
    }

    @Override
    public void sendMessage(String text) {
        addMessage(new Message(loggedUser, text));
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
    private void addMessage(Message message) {
        messages.add(message);
        databaseOperations.addMessage(message);
        raiseEventNewMessages();
    }

}
