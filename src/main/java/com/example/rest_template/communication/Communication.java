package com.example.rest_template.communication;

import com.example.rest_template.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {
    private final String URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HttpHeaders getHeadersOnStart() {
        ResponseEntity<String> forEntity
                = restTemplate.getForEntity(URL, String.class);
        List<String> cookies = forEntity.getHeaders().get("Set-Cookie");
        HttpHeaders headersOnStart = new HttpHeaders();
        headersOnStart.set("Cookie", String.join(";", cookies));
        headersOnStart.setContentType(MediaType.APPLICATION_JSON);
        return headersOnStart;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity
                = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>(){});
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", String.join(";", cookies));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return responseEntity.getBody();
    }

    public String saveUser(String jsonUser, HttpHeaders headers) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,
                HttpMethod.POST, new HttpEntity<>(jsonUser, headers),
                String.class);
        return responseEntity.getBody();
    }

    public String updateUser(String jsonUser, HttpHeaders headers) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,
                HttpMethod.PUT, new HttpEntity<>(jsonUser, headers),
                String.class);
        return responseEntity.getBody();
    }

    public String deleteUser(long id, HttpHeaders headers) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL + "/" + id, HttpMethod.DELETE,
                new HttpEntity<>(null, headers), String.class);
        return responseEntity.getBody();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
