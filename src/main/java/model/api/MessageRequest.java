package model.api;

import model.Message;

public class MessageRequest {
    String token;
    String text;

    public MessageRequest(String token, Message message) {
        this.token = token;
        this.text = message.toString();
    }
}
