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
import ru.lanit.at.utils.HttpClient;

import java.util.HashMap;


public class test_Photo {

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
        Response deleteAlbum = deleteAlbum("publicAlbumId");
        Assert.assertEquals(deleteAlbum.getStatusCode(), 200);
        Assert.assertEquals(deleteAlbum.getBody().jsonPath().getInt("response"), 1);
    }


    @Test
    public void test() {
        stepsWithCreateAlbum();
    }


    private Response createPrivateAlbum() {
        ApiRequest apiRequest = new ApiRequest("/photos.createAlbum", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "АльбомПриват");
        hashMap.put("privacy_view", "only_me");
        hashMap.put("privacy_comment", "only_me");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response getUploadServer() {
        ApiRequest apiRequest = new ApiRequest("/photos.getUploadServer", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("album_id", ContextHolder.getValue("privateAlbumId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response savePhoto() {
        ApiRequest apiRequest = new ApiRequest("/photos.save", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("album_id", ContextHolder.getValue("privateAlbumId"));
        hashMap.put("server", ContextHolder.getValue("server"));
        hashMap.put("hash", ContextHolder.getValue("hash"));
        String photo = ContextHolder.getValue("photos_list").toString().replace("\\", "");
        hashMap.put("photos_list", photo);
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response makeCoverPhoto() {
        ApiRequest apiRequest = new ApiRequest("/photos.makeCover", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("album_id", ContextHolder.getValue("privateAlbumId"));
        hashMap.put("photo_id", ContextHolder.getValue("photoId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response commentPhoto(String message) {
        ApiRequest apiRequest = new ApiRequest("/photos.createComment", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", message);
        hashMap.put("photo_id", ContextHolder.getValue("photoId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response setTagPhoto() {
        ApiRequest apiRequest = new ApiRequest("/photos.putTag", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", "753224346");
        hashMap.put("photo_id", ContextHolder.getValue("photoId"));
        hashMap.put("x", "10");
        hashMap.put("x2", "12");
        hashMap.put("y", "10");
        hashMap.put("y2", "12");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response createPublicAlbum() {
        ApiRequest apiRequest = new ApiRequest("/photos.createAlbum", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "АльбомПаблик");
        hashMap.put("privacy_view", "all");
        hashMap.put("privacy_comment", "all");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response movePhoto() {
        ApiRequest apiRequest = new ApiRequest("/photos.move", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("target_album_id", ContextHolder.getValue("publicAlbumId"));
        hashMap.put("photo_id", ContextHolder.getValue("photoId"));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    private Response deleteAlbum(String albumId) {
        ApiRequest apiRequest = new ApiRequest("/photos.deleteAlbum", "GET",token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("album_id", ContextHolder.getValue(albumId));
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }


    public void stepsWithCreateAlbum() {
        Response createPrivateAlbum = createPrivateAlbum();
        Assert.assertEquals(createPrivateAlbum.getStatusCode(), 200);
        ContextHolder.put("privateAlbumId", String.valueOf(createPrivateAlbum.getBody().jsonPath().getInt("response.id")));

        Response uploadServer = getUploadServer();
        Assert.assertEquals(uploadServer.getStatusCode(), 200);
        String url = uploadServer.getBody().jsonPath().get("response.upload_url");
        HttpClient.savePhotoFile(url, "src/test/resources/photoFile/kotik.jpg", 1);
        Response savePhoto = savePhoto();
        Assert.assertEquals(savePhoto.getStatusCode(), 200);
        Assert.assertEquals(savePhoto.getBody().jsonPath().getInt("response[0].album_id"), Integer.parseInt(ContextHolder.getValue("privateAlbumId")));
        ContextHolder.put("photoId", String.valueOf(savePhoto.getBody().jsonPath().getInt("response[0].id")));

        Response makeCoverPhoto = makeCoverPhoto();
        Assert.assertEquals(makeCoverPhoto.getStatusCode(), 200);
        Assert.assertEquals(makeCoverPhoto.getBody().jsonPath().getInt("response"), 1);

        Response commentPhoto = commentPhoto("Коммент к фоту");
        Assert.assertEquals(commentPhoto.getStatusCode(), 200);

        Response setTag = setTagPhoto();
        Assert.assertEquals(setTag.getStatusCode(), 200);
        ContextHolder.put("tagId", String.valueOf(setTag.getBody().jsonPath().getInt("response")));

        Response publicAlbum = createPublicAlbum();
        Assert.assertEquals(publicAlbum.getStatusCode(), 200);
        ContextHolder.put("publicAlbumId", String.valueOf(publicAlbum.getBody().jsonPath().getInt("response.id")));

        Response movePhoto = movePhoto();
        Assert.assertEquals(movePhoto.getStatusCode(), 200);
        Assert.assertEquals(movePhoto.getBody().jsonPath().getInt("response"), 1);

        Response deleteAlbum = deleteAlbum("privateAlbumId");
        Assert.assertEquals(deleteAlbum.getStatusCode(), 200);
        Assert.assertEquals(deleteAlbum.getBody().jsonPath().getInt("response"), 1);

    }


}
