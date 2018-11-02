package com.gunnarro.dietmanager.endpoint.rest;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.RestTemplate;

public class SpringRestTestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/diet-manager";

    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllUsers() {
        System.out.println("Testing listAllUsers API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI + "/user/", List.class);

        if (usersMap != null) {
            for (LinkedHashMap<String, Object> map : usersMap) {
                System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age=" + map.get("age") + ", Salary=" + map.get("salary"));
                ;
            }
        } else {
            System.out.println("No user exist----------");
        }
    }

    /* GET */
    private static void getUser() {
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(REST_SERVICE_URI + "/user/1", User.class);
        System.out.println(user);
    }

    // /* POST */
    // private static void createUser() {
    // System.out.println("Testing create User API----------");
    // RestTemplate restTemplate = new RestTemplate();
    // User user = new User(0,"Sarah",51,134);
    // URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user,
    // User.class);
    // System.out.println("Location : "+uri.toASCIIString());
    // }

}
