package com.webhook.user.service.controller;

import com.svix.exceptions.ApiException;
import com.svix.exceptions.WebhookVerificationException;
import com.webhook.user.service.models.Events;
import com.webhook.user.service.models.WebhookRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/api/webhook")
public class UserController {

    private final WebClient webClient;

    public UserController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/print")
    public Mono<?> print(@RequestBody WebhookRequest request) throws ApiException, WebhookVerificationException {
        System.out.println("Webhook Request ========>" + request);
        request.setRow(java.util.UUID.randomUUID().toString());
        request.setDate(new Date().toString());
            WebClient webClient = WebClient.create("https://hooks.zapier.com");

            return webClient.post()
                    .uri("/hooks/catch/13555221/b0m2dxc/")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(request), Events.class)
                    .retrieve()
                    .bodyToMono(Events.class);
        }


    @GetMapping("/apihealth") //http:localhost:8080/api/webhook/print
    public ResponseEntity<String> healthCheck() {
        //https://edaf-103-189-99-24.in.ngrok.io/api/webhook/apihealth
        System.out.println("Health Check  ========>");
        return new ResponseEntity<String >("Service is up", HttpStatus.OK);
    }
}
