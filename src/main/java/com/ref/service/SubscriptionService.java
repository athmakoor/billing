package com.ref.service;

import java.io.IOException;
import java.util.Map;

import com.ref.bean.SubscriptionDTO;
import com.ref.bean.UnsubscribeDTO;
import com.ref.bean.jpa.RequestTrackerEntity;

public interface SubscriptionService {
    String subscribe(SubscriptionDTO data) throws IOException;

    String unSubscribe(UnsubscribeDTO data) throws IOException;

    void saveSubscription(RequestTrackerEntity requestTrackerEntity, Map<String, String> requestParams);

    void saveUnSubscription(RequestTrackerEntity requestTrackerEntity, Map<String, String> requestParams);
}
