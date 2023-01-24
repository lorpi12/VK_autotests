package ru.lanit.at.api;

import io.qameta.allure.Allure;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import ru.lanit.at.api.listeners.RestAssuredCustomLogger;
import ru.lanit.at.api.properties.RestConfigurations;
import ru.lanit.at.utils.FileUtil;
import ru.lanit.at.utils.JsonUtil;
import ru.lanit.at.utils.RegexUtil;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.lanit.at.utils.ContextHolder.replaceVarsIfPresent;

/**
 * Created by Sorokin Boris on 21.01.2022.
 */
public class ApiRequest {
    private final static RestConfigurations CONFIGURATIONS = ConfigFactory.create(RestConfigurations.class,
            System.getProperties(),
            System.getenv());

    private String baseUrl;
    private String path;
    private Method method;
    private String body;
    private Response response;

    private RequestSpecBuilder builder;

    public ApiRequest(String path, String method, String token) {
        this.builder = new RequestSpecBuilder();
        this.baseUrl = CONFIGURATIONS.getBaseUrl();
        this.path = replaceVarsIfPresent(path);
        this.method = Method.valueOf(method);

        URI uri = URI.create(baseUrl);
        builder.setBasePath(path);

        this.builder.setBaseUri(uri);
        addLoggingListener();
        setQueryDefault(token);
    }

    private void setQueryDefault(String token) {
        builder.addQueryParam("v", "5.131");
        builder.addQueryParam("access_token", token);
    }

    public Response getResponse() {
        return response;
    }

    /**
     * Сеттит заголовки
     */
    public void setHeaders(Map<String, String> headers) {
        headers.forEach((k, v) -> {
            builder.addHeader(k, v);
        });
    }

    /**
     * Сеттит query-параметры
     */
    public void setQuery(Map<String, String> query) {
        query.forEach((k, v) -> {
            builder.addQueryParam(k, v);
        });
    }

    /**
     * Отправляет сформированный запрос
     */
    public void sendRequest() {
        RequestSpecification requestSpecification = builder.build();

        Response response = given()
                .spec(requestSpecification)
                .request(method);

        attachRequestResponseToAllure(response, body);
        this.response = response;
    }

    /**
     * Сессит тело запроса из файла
     */
    private void setBodyFromFile() {
        if (body != null && RegexUtil.getMatch(body, ".*\\.json")) {
            body = replaceVarsIfPresent(FileUtil.readBodyFromJsonDir(body));
            builder.setBody(body);
        }
    }

    /**
     * Аттачит тело запроса и тело ответа в шаг отправки запроса
     */
    private void attachRequestResponseToAllure(Response response, String requestBody) {
        if (requestBody != null) {
            Allure.addAttachment(
                    "Request",
                    "application/json",
                    new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8)),
                    ".txt");
        }
        String responseBody = JsonUtil.jsonToUtf(response.body().asPrettyString());
        Allure.addAttachment("Response", "application/json", responseBody, ".txt");
    }

    /**
     * Добавляет логгер, печатающий в консоль данные запросов и ответов
     */
    private void addLoggingListener() {
        builder.addFilter(new RestAssuredCustomLogger());
    }
}
