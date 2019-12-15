package com.ref.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ref.bean.SubscriptionDTO;
import com.ref.bean.UnsubscribeDTO;
import com.ref.bean.jpa.ProviderEntity;
import com.ref.bean.jpa.RequestTrackerEntity;
import com.ref.repository.ProviderRepository;
import com.ref.service.SubscriptionService;
import com.ref.utils.SubscriptionUtil;

@Component
public class MobilitySubscriptionService implements SubscriptionService {
    @Resource
    private ProviderRepository providerRepository;

    @Override
    public String subscribe(SubscriptionDTO data) throws IOException {
        Optional<ProviderEntity> providerEntityOptional = providerRepository.findByProviderKey(data.getKey());

        if (providerEntityOptional.isPresent()) {
            ProviderEntity providerEntity = providerEntityOptional.get();

            String subscriptionUrl = providerEntity.getSubscriptionUrl();
            subscriptionUrl = SubscriptionUtil.updateSubscribeUrl(subscriptionUrl, data);

            return SubscriptionUtil.getRequest(subscriptionUrl);

        }
        return null;
    }

    @Override
    public String unSubscribe(UnsubscribeDTO data) throws IOException {
        Optional<ProviderEntity> providerEntityOptional = providerRepository.findByProviderKey(data.getKey());

        if (providerEntityOptional.isPresent()) {
            ProviderEntity providerEntity = providerEntityOptional.get();

            String subscriptionUrl = providerEntity.getSubscriptionUrl();
            subscriptionUrl = SubscriptionUtil.updateUnsubscribeUrl(subscriptionUrl, data);

            return SubscriptionUtil.getRequest(subscriptionUrl);

        }
        return null;
    }

    @Override
    public void saveSubscription(RequestTrackerEntity requestTrackerEntity, Map<String, String> requestParams) {

    }

    @Override
    public void saveUnSubscription(RequestTrackerEntity requestTrackerEntity, Map<String, String> requestParams) {

    }
}
