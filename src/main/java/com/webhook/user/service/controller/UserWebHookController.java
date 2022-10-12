package com.webhook.user.service.controller;

import com.webhook.user.service.producer.UserWebHookRequest;
import com.webhook.user.service.producer.WebhookRequestProducer;
import com.webhook.user.service.serviceImpl.UserWebHookRequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/webHooks")
public class UserWebHookController {

    private final WebhookRequestProducer webhookRequestProducer;

    private final UserWebHookRequestServiceImpl userWebHookRequestService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserWebHookController.class);

    public UserWebHookController(WebhookRequestProducer webhookRequestProducer, UserWebHookRequestServiceImpl userWebHookRequestService) {
        this.webhookRequestProducer = webhookRequestProducer;
        this.userWebHookRequestService = userWebHookRequestService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/webHook/{username}", consumes = { "application/json",
            "application/xml", "text/plain", "text/html" }, produces = { "application/json", "application/xml" })
    //@ApiOperation(value = "WebHook for Incoming message and statuses from Admin", hidden = true)
    public String webHook(@PathVariable String username) {
        try {
            LOGGER.info("userRequest for username {}" , username);
            UserWebHookRequest userWebHookRequest = new UserWebHookRequest(username, new Date());
            userWebHookRequest = userWebHookRequestService.createDLGWebHookRequest(userWebHookRequest);
            webhookRequestProducer.sendUserWebHookRequest(userWebHookRequest);
            LOGGER.info("Sent to Admin Layer");
            return "Successfully request sent!!!";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "error";
    }

}
