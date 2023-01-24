package VK.api;


import io.qameta.allure.Step;
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


public class TestDiscussion {

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

    @Step("Создать группу")
    private Response createGroup() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "Группа");
        hashMap.put("type", "group");
        return request("/groups.create", hashMap);
    }

    @Step("Создать дискуссию")
    private Response createDiscussion() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("title", "Название обсуждения");
        hashMap.put("text", "Текст обсуждения");
        return request("/board.addTopic", hashMap);
    }

    @Step("Закрепить дискуссию")
    private Response fixDiscussion() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        return request("/board.fixTopic", hashMap);
    }

    @Step("Создать коммент")
    private Response createComment(String message) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        hashMap.put("message", message);
        return request("/board.createComment", hashMap);
    }

    @Step("Изменить коммент")
    private Response editComment(String message, String commentId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        hashMap.put("comment_id", ContextHolder.getValue(commentId));
        hashMap.put("message", message);
        return request("/board.editComment", hashMap);
    }

    @Step("Удалить коммент")
    private Response deleteComment(String commentId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        hashMap.put("topic_id", ContextHolder.getValue("discussionId"));
        hashMap.put("comment_id", ContextHolder.getValue(commentId));
        return request("/board.deleteComment", hashMap);
    }

    @Step("Удалить группу")
    private Response deleteGroup() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", ContextHolder.getValue("groupId"));
        return request("/groups.leave", hashMap);
    }

    private Response request(String path, HashMap<String, String> hashMap) {
        ApiRequest apiRequest = new ApiRequest(path, "GET", token);
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
