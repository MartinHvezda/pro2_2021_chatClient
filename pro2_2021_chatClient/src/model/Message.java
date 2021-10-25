package model;

import java.time.LocalDateTime;

public class Message {
    private String author;
    private String text;
    private LocalDateTime created;

    public final static int USER_LOGGED_IN = 1;
    public final static int USER_LOGGED_OUT = 2;

    private final String AUTHOR_SYSTEM = "System";

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
        created = LocalDateTime.now();
    }

    public Message(int type, String userName) {
        if(type == USER_LOGGED_IN) {
            author = AUTHOR_SYSTEM;
            created = LocalDateTime.now();
            text = userName + "has entered the chat";
        }
        else if(type == USER_LOGGED_OUT) {
            author = userName;
            created = LocalDateTime.now();
            text = userName + "has left the chat";
        }
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    @Override
    public String toString() {
        if(author == AUTHOR_SYSTEM) {

        }
        String msg = author + " ["+created+"]\n";
        msg += text + "\n";
        return msg;
    }
}
