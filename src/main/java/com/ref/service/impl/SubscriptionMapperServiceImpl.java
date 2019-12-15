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
import com.ref.bean.jpa.SubscriptionEntity;
import com.ref.repository.ProviderRepository;
import com.ref.repository.SubscriptionRepository;
import com.ref.service.SubscriptionService;
import com.ref.utils.TimeUtil;

@Component
public class SubscriptionMapperServiceImpl implements SubscriptionService {
    @Resource
    private MobilitySubscriptionService mobilitySubscriptionService;
    @Resource
    private ProviderRepository providerRepository;
    @Resource
    private SubscriptionRepository subscriptionRepository;

    @Override
    public String subscribe(SubscriptionDTO data) throws IOException {
        Optional<ProviderEntity> providerEntityOptional = providerRepository.findByProviderKey(data.getKey());

        if (providerEntityOptional.isPresent()) {
            return mobilitySubscriptionService.subscribe(data);
        }
        return null;
    }

    @Override
    public String unSubscribe(UnsubscribeDTO data) throws IOException {
        Optional<ProviderEntity> providerEntityOptional = providerRepository.findByProviderKey(data.getKey());

        if (providerEntityOptional.isPresent()) {
            return mobilitySubscriptionService.unSubscribe(data);
        }
        return null;
    }

    @Override
    public void saveSubscription(RequestTrackerEntity requestTrackerEntity, Map<String, String> requestParams) {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();

        subscriptionEntity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        subscriptionEntity.setInternalTxnNo(requestTrackerEntity.getId());
        subscriptionEntity.setMsisdn(requestParams.get("msisdn"));
        subscriptionEntity.setReSub(false);
        subscriptionEntity.setActive(true);
        subscriptionEntity.setStatus(requestParams.get("status"));

        subscriptionRepository.save(subscriptionEntity);
    }

    @Override
    public void saveUnSubscription(RequestTrackerEntity requestTrackerEntity, Map<String, String> requestParams) {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();

        subscriptionEntity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        subscriptionEntity.setInternalTxnNo(requestTrackerEntity.getId());
        subscriptionEntity.setMsisdn(requestParams.get("msisdn"));
        subscriptionEntity.setReSub(false);
        subscriptionEntity.setActive(false);
        subscriptionEntity.setStatus(requestParams.get("status"));

        subscriptionRepository.save(subscriptionEntity);
    }
}
