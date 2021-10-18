package model;

public interface ChatClient {
    Boolean isAuthenticated();
    void login(String userName);
    void logout();
    void sendMessage(String text);
    List<String> getLoggedUsers();
    List<String> getMessages();
}
