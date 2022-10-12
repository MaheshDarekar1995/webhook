package com.webhook.user.service.controller;

import com.svix.exceptions.ApiException;
import com.svix.exceptions.WebhookVerificationException;
import com.webhook.user.service.models.Events;
import com.webhook.user.service.models.WebhookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/api/webhook")
public class UserController {

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/print")
    public ResponseEntity<?> print(@RequestBody WebhookRequest request) throws ApiException, WebhookVerificationException {
        System.out.println("Webhook Request ========>" + request);
        request.setRow(java.util.UUID.randomUUID().toString());
        request.setDate(new Date().toString());
        try {
            ResponseEntity<Events> eventResponse=restTemplate.postForEntity("https://hooks.zapier.com/hooks/catch/13555221/b0m2dxc/", request, Events.class);
        }
        catch (Exception e){
            return new ResponseEntity<WebhookRequest >(request, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<WebhookRequest >(request, HttpStatus.OK);
    }

    @GetMapping("/apihealth") //http:localhost:8080/api/webhook/print
    public ResponseEntity<String> healthCheck() {
        //https://edaf-103-189-99-24.in.ngrok.io/api/webhook/apihealth
        System.out.println("Health Check  ========>");
        return new ResponseEntity<String >("Service is up", HttpStatus.OK);
    }
}
