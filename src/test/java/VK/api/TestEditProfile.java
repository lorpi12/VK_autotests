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
import ru.lanit.at.utils.HttpClient;

import java.util.HashMap;


public class TestEditProfile {

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
        deletePhoto();
        setDefaultProfileInfo();
    }


    @Test
    public void test() {
        getProfileInfoAndFill();
        uploadPhoto();
    }

    @Step("Получить инфо о профиле")
    private Response getProfileInfo() {
        ApiRequest apiRequest = new ApiRequest("/account.getProfileInfo", "GET", token);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }

    @Step("Задать инфо о профиле")
    private Response setProfileInfo() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("relation", "1");
        hashMap.put("home_town", "Москва");
        return request("/account.saveProfileInfo", hashMap);
    }

    public void getProfileInfoAndFill() {
        Response profileInfo = getProfileInfo();
        Assert.assertEquals(profileInfo.getStatusCode(), 200);
        Assert.assertEquals((int) profileInfo.getBody().jsonPath().get("response.id"), 753224346);
        Assert.assertEquals(profileInfo.getBody().jsonPath().get("response.home_town"), "");
        Assert.assertEquals(profileInfo.getBody().jsonPath().get("response.first_name"), "Дмитрий");
        Assert.assertEquals(profileInfo.getBody().jsonPath().get("response.last_name"), "Пупкин");
        Assert.assertEquals((int) profileInfo.getBody().jsonPath().get("response.relation"), 0);
        Response setProfileInfo = setProfileInfo();
        Assert.assertEquals(setProfileInfo.getStatusCode(), 200);
        profileInfo = getProfileInfo();
        Assert.assertEquals(profileInfo.getStatusCode(), 200);
        Assert.assertEquals(profileInfo.getBody().jsonPath().get("response.home_town"), "Москва");
        Assert.assertEquals((int) profileInfo.getBody().jsonPath().get("response.relation"), 1);
    }

    @Step("Загрузить фото")
    public void uploadPhoto() {
        Response uploadServer = getUploadServer();
        Assert.assertEquals(uploadServer.getStatusCode(), 200);
        String url = uploadServer.getBody().jsonPath().get("response.upload_url");
        HttpClient.savePhotoFile(url, "src/test/resources/photoFile/kotik.jpg", 0);
        Response savePhoto = saveOwnerPhoto();
        Assert.assertEquals(savePhoto.getStatusCode(), 200);
    }

    @Step("Получить сервер загрузки фото")
    private Response getUploadServer() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("owner_id", "753224346");
        return request("/photos.getOwnerPhotoUploadServer", hashMap);
    }

    @Step("Сохранить владельца фото")
    private Response saveOwnerPhoto() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("server", ContextHolder.getValue("server"));
        hashMap.put("hash", ContextHolder.getValue("hash"));
        hashMap.put("photo", ContextHolder.getValue("photo"));
        return request("/photos.saveOwnerPhoto", hashMap);
    }

    @Step("Удалить фото")
    private Response deletePhoto() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("owner_id", "753224346");
        hashMap.put("photo_id", String.valueOf(getIdProfilePhoto()));
        return request("/photos.delete", hashMap);
    }

    @Step("Получить id профиля")
    private int getIdProfilePhoto() {
        ApiRequest apiRequest = new ApiRequest("/photos.getProfile", "GET", token);
        apiRequest.sendRequest();
        return apiRequest.getResponse().getBody().jsonPath().get("response.items[0].id");
    }

    @Step("Установить инфо о профиле по умолчанию")
    private void setDefaultProfileInfo() {
        ApiRequest apiRequest = new ApiRequest("/account.saveProfileInfo", "GET", token);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("relation", "0");
        hashMap.put("home_town", "");
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
    }

    private Response request(String path, HashMap<String, String> hashMap) {
        ApiRequest apiRequest = new ApiRequest(path, "GET", token);
        apiRequest.setQuery(hashMap);
        apiRequest.sendRequest();
        return apiRequest.getResponse();
    }


}
