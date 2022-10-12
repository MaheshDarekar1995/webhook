package com.webhook.user.service.serviceImpl;

import com.webhook.user.service.producer.UserWebHookRequest;
import org.springframework.stereotype.Service;

@Service
public class UserWebHookRequestServiceImpl {

    public UserWebHookRequest createDLGWebHookRequest(UserWebHookRequest userWebHookRequest) {
        UserWebHookRequest userWebHookRequest1=new UserWebHookRequest();
        userWebHookRequest1.setUserName(userWebHookRequest.getUserName());
        return userWebHookRequest1;
    }

}
