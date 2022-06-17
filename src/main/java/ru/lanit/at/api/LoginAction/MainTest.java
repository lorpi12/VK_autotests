package ru.lanit.at.api.LoginAction;


import io.restassured.http.ContentType;
import ru.lanit.at.dictionaries.PathOnLogin;

import static io.restassured.RestAssured.given;

public abstract class MainTest {


    private String generateJson(PathOnLogin login) {
        return "{\"username\":\"" + login.getLogin() + "\",\n\"password\":\"" + login.getPassword() + "\"}";

    }

    protected String getAuthToken(PathOnLogin login) {
        return given()
                .baseUri(System.getProperty("site.url"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(generateJson(login))
                .when()
                .post("/api/otp_token/")
                .then()
                .extract().body().jsonPath().getString("otp_token");
    }


}
