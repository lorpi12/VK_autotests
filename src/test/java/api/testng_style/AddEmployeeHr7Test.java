package api.testng_style;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanit.at.dictionaries.PathOnLogin;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AddEmployeeHr7Test {
    private String token;

    private String generateJson(PathOnLogin login) {
        return "{\"username\":\"" + login.getLogin() + "\",\n\"password\":\"" + login.getPassword() + "\"}";

    }

    @BeforeClass
    public void prepare() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config/configuration.properties"));
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(System.getProperty("site.url"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        RestAssured.filters(new ResponseLoggingFilter());
    }

    @BeforeMethod
    public void authorization(){
        this.token = given()
                .body(generateJson(PathOnLogin.ADMIN))
                .when()
                .post("/api/login/")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getString("token");
    }


    @Test
    public void test() {
        given()
                .header(new Header("Authorization", "Token " + token))
                .when()
                .get("/api/accounts/")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonShema/GetAccounts.json")));
    }
}
