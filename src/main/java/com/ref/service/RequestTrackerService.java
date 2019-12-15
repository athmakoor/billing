package com.ref.service;

import java.util.Map;

import com.ref.bean.SubscriptionDTO;
import com.ref.bean.UnsubscribeDTO;

public interface RequestTrackerService {
    String saveAndGetSubscriptionRedirectUrl(SubscriptionDTO data);

    void updateNotification(Map<String, String> requestParams);

    String getResponseRedirectionUrl(Map<String, String> requestParams);

    String saveAndGetUnSubscribeRedirectUrl(UnsubscribeDTO unsubscribeDTO);
}
