package model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.api.MessageRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebChatClient implements  ChatClient{
    private String loggedUser;
    private String token;
    private final String BASE_URL = "http://fimuhkpro22021.aspifyhost.cz";

    private List<String> loggedUsers;
    private List<Message> messages;

    private List<ActionListener> listenersLoggedUsersChanged = new ArrayList<>();
    private List<ActionListener> listenersMessagesChanged = new ArrayList<>();
    private Gson gson;


    public WebChatClient() {
        //gson = new Gson();
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
        }).create();
        loggedUsers = new ArrayList<>();
        messages = new ArrayList<>();

        Runnable refreshLoggedUsersRun = () -> {
            Thread.currentThread().setName("refreshLoggedUsersThread");
            while(true){
                if(isAuthenticated()){
                    refreshLoggedUsers();
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread thread = new Thread(refreshLoggedUsersRun);
        thread.start();
    }


    @Override
    public Boolean isAuthenticated() {
        return token != null;
    }

    @Override
    public void login(String userName) {
        try{
            String url = BASE_URL + "/api/chat/login";
            HttpPost post = new HttpPost(url);
            StringEntity body = new StringEntity("\""+userName+"\"", "utf-8");
            body.setContentType("application/json");
            post.setEntity(body);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);


            if(response.getStatusLine().getStatusCode() == 200){
                token = EntityUtils.toString(response.getEntity());
                token = token.replaceAll("\"","");
                loggedUser = userName;
                addMessage(new Message(Message.USER_LOGGED_IN, loggedUser));
                refreshLoggedUsers();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logout() {
        try{
            String url = BASE_URL + "/api/chat/logout";
            HttpPost post = new HttpPost(url);
            StringEntity body = new StringEntity("\""+token+"\"", "utf-8");
            body.setContentType("application/json");
            post.setEntity(body);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);

            if(response.getStatusLine().getStatusCode() == 204){
                addMessage(new Message(Message.USER_LOGGED_OUT, loggedUser));
                token = null;
                loggedUser = null;
                refreshLoggedUsers();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
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
        listenersMessagesChanged.add(toAdd);
    }

    private void raiseEventLoggedUsersChanged() {
        for (ActionListener al: listenersLoggedUsersChanged) {
            al.actionPerformed(new ActionEvent(this, 1, "usersChanged"));
        }
    }

    private void raiseEventMessagesChanged() {
        for(ActionListener al: listenersMessagesChanged) {
            al.actionPerformed(new ActionEvent(this, 1, "messagesChanged"));
        }
    }
    private void addMessage(Message message) {
        try{
            MessageRequest messageRequest = new MessageRequest(token, message);
            String url = BASE_URL + "/api/chat/sendMessage";
            HttpPost post = new HttpPost(url);
            StringEntity body = new StringEntity(gson.toJson(messageRequest), "utf-8");
            body.setContentType("application/json");
            post.setEntity(body);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);

            if(response.getStatusLine().getStatusCode() == 204){
                refreshMessages();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void refreshLoggedUsers() {
        try{
           String url = BASE_URL + "/api/chat/getLoggedUsers";
           HttpGet get = new HttpGet(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);

            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResult = EntityUtils.toString(entity);

                loggedUsers = gson.fromJson(jsonResult, new TypeToken<ArrayList<String>>(){}.getType());

                raiseEventLoggedUsersChanged();
            }
        }catch(Exception e) {
          e.printStackTrace();
        }
    }

    private void refreshMessages(){
        try{
            String url = BASE_URL + "/api/chat/getMessages";
            HttpGet get = new HttpGet(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);

            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResult = EntityUtils.toString(entity);

                messages = gson.fromJson(jsonResult, new TypeToken<ArrayList<Message>>(){}.getType());

                raiseEventMessagesChanged();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}
