package com.webhook.user.service.models;

import lombok.Data;

@Data
public class WebhookRequest {
    private String row;

    private String date;
}
