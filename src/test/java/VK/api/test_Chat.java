package VK.api;


import io.restassured.response.Response;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.lanit.at.api.ApiRequest;
import ru.lanit.at.crypto.Crypto;
import ru.lanit.at.db.config.DataBase;
import ru.lanit.at.db.datasource.LoginTable;
import ru.lanit.at.utils.ContextHolder;

import java.util.HashMap;
import java.util.Random;


public class test_Chat {

    private String token;

    @BeforeClass
    public void beforeClass() {
        ApplicationContext config = new AnnotationConfigApplicationContext(DataBase.class);
        JdbcTemplate jdbcTemplate = config.getBean("jdbcTemplate", JdbcTemplate.class);
        LoginTable loginTable = new LoginTable(jdbcTemplate);
        token = Crypto.decrypt(loginTable.selectLogin().getToken());
    }

    @AfterMethod
    public void afterTest() {
        deleteChat();
    }


    @Test
    public void test() {
        stepsWithCreateChat();
    }


    private Response createChat() {
        ApiRequest apiRequest = new ApiRequest("/messages.createChat", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "Важно");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response editChatTitle() {
        ApiRequest apiRequest = new ApiRequest("/messages.editChat", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "Ну очень важная беседа");
        hashMap.put("chat_id", ContextHolder.getValue("chatId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response addChatUser() {
        ApiRequest apiRequest = new ApiRequest("/messages.addChatUser", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("chat_id", ContextHolder.getValue("chatId"));
        hashMap.put("user_id", "194237192");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response sendMessage(String message) {
        ApiRequest apiRequest = new ApiRequest("/messages.send", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("chat_id", ContextHolder.getValue("chatId"));
        Random random = new Random();
        hashMap.put("random_id", String.valueOf(random.nextInt(10000)));
        hashMap.put("message", message);
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response editMessage(String message) {
        ApiRequest apiRequest = new ApiRequest("/messages.edit", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        int peer_id = Integer.parseInt(ContextHolder.getValue("chatId")) + 2000000000;
        hashMap.put("peer_id", String.valueOf(peer_id));
        hashMap.put("message", message);
        hashMap.put("message_id", ContextHolder.getValue("messageId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response pinMessage() {
        ApiRequest apiRequest = new ApiRequest("/messages.pin", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        int peer_id = Integer.parseInt(ContextHolder.getValue("chatId")) + 2000000000;
        hashMap.put("peer_id", String.valueOf(peer_id));
        hashMap.put("message_id", ContextHolder.getValue("messageId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response deleteChat() {
        ApiRequest apiRequest = new ApiRequest("/messages.deleteConversation", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        int peer_id = Integer.parseInt(ContextHolder.getValue("chatId")) + 2000000000;
        hashMap.put("peer_id", String.valueOf(peer_id));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }


    public void stepsWithCreateChat() {
        Response createChat = createChat();
        Assert.assertEquals(createChat.getStatusCode(), 200);
        ContextHolder.put("chatId", String.valueOf(createChat.getBody().jsonPath().getInt("response")));

        Response ediChatTitle = editChatTitle();
        Assert.assertEquals(ediChatTitle.getStatusCode(), 200);
        Assert.assertEquals(ediChatTitle.getBody().jsonPath().getInt("response"), 1);

        Response addChatUser = addChatUser();
        Assert.assertEquals(addChatUser.getStatusCode(), 200);
        Assert.assertEquals(addChatUser.getBody().jsonPath().getInt("response"), 1);

        Response sendMessage = sendMessage("Это очень важная беседа, выходить!");
        Assert.assertEquals(sendMessage.getStatusCode(), 200);
        ContextHolder.put("messageId", String.valueOf(sendMessage.getBody().jsonPath().getInt("response")));

        Response editMessage = editMessage("Это очень важная беседа, НЕ выходить!");
        Assert.assertEquals(editMessage.getStatusCode(), 200);
        Assert.assertEquals(editMessage.getBody().jsonPath().getInt("response"), 1);

        Response pinMessage = pinMessage();
        Assert.assertEquals(pinMessage.getStatusCode(), 200);
        Assert.assertEquals(pinMessage.getBody().jsonPath().getString("response.text"), "Это очень важная беседа, НЕ выходить!");

        Response sendMessageSecond = sendMessage("А нет, лучше в группу");
        Assert.assertEquals(sendMessageSecond.getStatusCode(), 200);
    }


}
