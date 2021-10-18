package model;

import java.time.LocalDateTime;

public class Message {
    private String author;
    private String text;
    private LocalDateTime created;

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
        created = LocalDateTime.now();
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
        String msg = author + " ["+created+"]\n";
        msg += text;
        return msg;
    }
}
