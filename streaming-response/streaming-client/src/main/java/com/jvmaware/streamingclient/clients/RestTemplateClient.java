package com.jvmaware.streamingclient.clients;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.jvmaware.streamingclient.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RestTemplateClient implements Client {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Gson gson = new Gson();

    @Override
    public void start() {
        RestTemplate restTemplate = new RestTemplate();
        List<Employee> employees = new ArrayList<>();

        restTemplate.execute("http://localhost:8080/stream/employee", HttpMethod.GET, null, response -> {
            InputStream body = response.getBody();
            JsonReader jsonReader = new JsonReader(new InputStreamReader(body));
            jsonReader.setLenient(true);
            while (jsonReader.hasNext() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                Employee json = gson.fromJson(jsonReader, Employee.class);
                logger.info("found: [{}] employee", json);
            }
            return employees;
        });
    }
}
