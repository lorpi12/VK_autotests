package web;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import io.restassured.http.ContentType;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public abstract class MainTest {


    private String generateStringFromResource(PathOnLogin login) throws IOException {
        return new String(Files.readAllBytes(Paths.get(login.getPath())));

    }

    protected String getAuthToken(PathOnLogin login) throws IOException {
        return given()
                .baseUri("http://178.154.246.238:58082/api")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(generateStringFromResource(login))
                .when()
                .post("/otp_token/")
                .then()
                .extract().body().jsonPath().getString("otp_token");
    }


    protected String readJsonFile(PathOnLogin login, String field) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(login.getPath()));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get(field).getAsString();
    }


}
