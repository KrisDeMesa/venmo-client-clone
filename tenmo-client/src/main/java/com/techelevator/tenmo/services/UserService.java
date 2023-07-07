package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService {

    private static final String API_BASE_URL = "http://localhost:8080/tenmo_user/";

    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;


    public User[] listUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException ex) {
            System.out.println("Error!");
        }
        return users;

    }

    public User getUserById(int userId) {
        try {
            User user = restTemplate.getForObject(API_BASE_URL + userId, User.class);
            return user;
        } catch (RestClientResponseException | ResourceAccessException ex) {
            throw new RuntimeException("Error getting user details: " + userId, ex);
        }
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
