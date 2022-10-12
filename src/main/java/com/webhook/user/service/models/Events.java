package com.webhook.user.service.models;

import lombok.Data;

@Data
public class Events {

    private String id;

    private String attempt;

    private String request_id;

    private String status;
}

