package com.webhook.user.service.producer;

import com.webhook.user.service.models.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookRequestProducer {

    private final RestTemplate restTemplate;

    @Value("${adminServiceUrl}")
    private String adminServiceUrl;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookRequestProducer.class);

    public WebhookRequestProducer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendUserWebHookRequest(UserWebHookRequest request) {
        adminServiceUrl=adminServiceUrl+"/getUserByUsername/"+request.getUserName();
        try {
            ResponseEntity<UserResponse> userResponse=restTemplate.exchange(adminServiceUrl, HttpMethod.GET, null,
                    UserResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
