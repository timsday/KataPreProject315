package com.example.rest_template;

import com.example.rest_template.communication.Communication;
import com.example.rest_template.config.Config;
import com.example.rest_template.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class App 
{
    public static void main( String[] args ) throws JsonProcessingException {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(Config.class);
        Communication communication
                = context.getBean("communication", Communication.class);
        ObjectMapper jsonMapper = new ObjectMapper();

        List<User> users = communication.getAllUsers();
        HttpHeaders headers = communication.getHeaders();

        String jsonUser3 = jsonMapper.writeValueAsString(
                new User(3L, "James", "Brown", (byte) 45));
        String jsonEditedUser3 = jsonMapper.writeValueAsString(
                new User(3L, "Thomas", "Shelby", (byte) 45));

        StringBuilder kataKey = new StringBuilder();

        kataKey.append(communication.saveUser(jsonUser3, headers));
        kataKey.append(communication.updateUser(jsonEditedUser3, headers));
        kataKey.append(communication.deleteUser(3L, headers));

        System.out.println("Kata task #3.1.5 key: " + kataKey);
    }
}
