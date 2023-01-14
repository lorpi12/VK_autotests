package ru.lanit.at.utils;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClient {

    public static void savePhotoFile(String url, String filePath, int flag) {
        try {
            createPost(url, filePath, flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createPost(String url, String filePath, int flag) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

// This attaches the file to the POST:
        File f = new File(filePath);
        builder.addBinaryBody(
                "file",
                new FileInputStream(f),
                ContentType.APPLICATION_OCTET_STREAM,
                f.getName()
        );
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        String result = CharStreams.toString(new InputStreamReader(
                response.getEntity().getContent(), Charsets.UTF_8));
        ContextHolder.put("server", regExp(result, "\"server\":(.+?),"));
        if (flag == 0) {
            ContextHolder.put("photo", regExp(result, "\"photo\":\"(.+?)\""));
        } else {
            ContextHolder.put("photos_list", regExp(result, "\"photos_list\":\"(.+?])\""));
        }
        ContextHolder.put("hash", regExp(result, "\"hash\":\"(.+?)\""));
    }

    public static String regExp(String input, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher.group(1);
    }

}
