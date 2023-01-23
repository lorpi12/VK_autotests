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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "Важно");
        return request("/messages.createChat", hashMap);
    }

    private Response editChatTitle() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "Ну очень важная беседа");
        hashMap.put("chat_id", ContextHolder.getValue("chatId"));
        return request("/messages.editChat", hashMap);
    }

    private Response addChatUser() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("chat_id", ContextHolder.getValue("chatId"));
        hashMap.put("user_id", "194237192");
        return request("/messages.addChatUser", hashMap);
    }

    private Response sendMessage(String message) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("chat_id", ContextHolder.getValue("chatId"));
        Random random = new Random();
        hashMap.put("random_id", String.valueOf(random.nextInt(10000)));
        hashMap.put("message", message);
        return request("/messages.send", hashMap);
    }

    private Response editMessage(String message) {
        HashMap<String, String> hashMap = new HashMap<>();
        int peer_id = Integer.parseInt(ContextHolder.getValue("chatId")) + 2000000000;
        hashMap.put("peer_id", String.valueOf(peer_id));
        hashMap.put("message", message);
        hashMap.put("message_id", ContextHolder.getValue("messageId"));
        return request("/messages.edit", hashMap);
    }

    private Response pinMessage() {
        HashMap<String, String> hashMap = new HashMap<>();
        int peer_id = Integer.parseInt(ContextHolder.getValue("chatId")) + 2000000000;
        hashMap.put("peer_id", String.valueOf(peer_id));
        hashMap.put("message_id", ContextHolder.getValue("messageId"));
        return request("/messages.pin", hashMap);
    }

    private Response deleteChat() {
        HashMap<String, String> hashMap = new HashMap<>();
        int peer_id = Integer.parseInt(ContextHolder.getValue("chatId")) + 2000000000;
        hashMap.put("peer_id", String.valueOf(peer_id));
        return request("/messages.deleteConversation", hashMap);
    }

    private Response request(String path, HashMap<String, String> hashMap) {
        ApiRequest apiRequest = new ApiRequest(path, "GET", token);
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
