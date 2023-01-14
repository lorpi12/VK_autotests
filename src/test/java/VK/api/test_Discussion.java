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


public class test_Discussion {

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
        Response deleteGroup = deleteGroup();
        Assert.assertEquals(deleteGroup.getStatusCode(), 200);
        Assert.assertEquals(deleteGroup.getBody().jsonPath().getInt("response"), 1);
    }

    @Test
    public void test() {
        stepsWithCreateGroup();
    }

    private Response createGroup() {
        ApiRequest apiRequest = new ApiRequest("/groups.create", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "Группа");
        hashMap.put("type", "group");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response createDiscussion() {
        ApiRequest apiRequest = new ApiRequest("/board.addTopic", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("title", "Название обсуждения");
        hashMap.put("text", "Текст обсуждения");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response fixDiscussion() {
        ApiRequest apiRequest = new ApiRequest("/board.fixTopic", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response createComment(String message) {
        ApiRequest apiRequest = new ApiRequest("/board.createComment", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        hashMap.put("message", message);
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response editComment(String message, String commentId) {
        ApiRequest apiRequest = new ApiRequest("/board.editComment", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        hashMap.put("comment_id", ContextHolder.getValue(commentId));
        hashMap.put("message", message);
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response deleteComment(String commentId) {
        ApiRequest apiRequest = new ApiRequest("/board.deleteComment", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        hashMap.put("comment_id", ContextHolder.getValue(commentId));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response deleteGroup() {
        ApiRequest apiRequest = new ApiRequest("/groups.leave", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    public void stepsWithCreateGroup() {
        Response createGroup = createGroup();
        Assert.assertEquals(createGroup.getStatusCode(), 200);
        ContextHolder.put("groupId", String.valueOf(createGroup.getBody().jsonPath().getInt("response.id")));

        Response createDiscussion = createDiscussion();
        Assert.assertEquals(createDiscussion.getStatusCode(), 200);
        ContextHolder.put("discussionId", String.valueOf(createDiscussion.getBody().jsonPath().getInt("response")));

        Response fixDiscussion = fixDiscussion();
        Assert.assertEquals(fixDiscussion.getStatusCode(), 200);
        Assert.assertEquals(fixDiscussion.getBody().jsonPath().getInt("response"), 1);

        Response createComment = createComment("Первый коммент");
        Assert.assertEquals(createComment.getStatusCode(), 200);
        ContextHolder.put("firstCommentId", String.valueOf(createComment.getBody().jsonPath().getInt("response")));

        createComment = createComment("Второй коммент");
        Assert.assertEquals(createComment.getStatusCode(), 200);
        ContextHolder.put("secondCommentId", String.valueOf(createComment.getBody().jsonPath().getInt("response")));

        createComment = createComment("Третий коммент");
        Assert.assertEquals(createComment.getStatusCode(), 200);
        ContextHolder.put("thirdCommentId", String.valueOf(createComment.getBody().jsonPath().getInt("response")));

        Response editComment = editComment("Изменил коммент", "secondCommentId");
        Assert.assertEquals(editComment.getStatusCode(), 200);
        Assert.assertEquals(editComment.getBody().jsonPath().getInt("response"), 1);

        Response deleteComment = deleteComment("firstCommentId");
        Assert.assertEquals(deleteComment.getStatusCode(), 200);
        Assert.assertEquals(deleteComment.getBody().jsonPath().getInt("response"), 1);

    }

}
