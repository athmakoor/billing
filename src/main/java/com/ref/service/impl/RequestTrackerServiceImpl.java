package com.ref.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ref.bean.ResponseRedirectDTO;
import com.ref.bean.SubscriptionDTO;
import com.ref.bean.UnsubscribeDTO;
import com.ref.bean.jpa.ProductEntity;
import com.ref.bean.jpa.ProviderEntity;
import com.ref.bean.jpa.RequestTrackerEntity;
import com.ref.exception.ItemNotFoundException;
import com.ref.repository.ProductRepository;
import com.ref.repository.ProviderRepository;
import com.ref.repository.RequestTrackerRepository;
import com.ref.service.RequestTrackerService;
import com.ref.utils.SubscriptionUtil;
import com.ref.utils.TimeUtil;

@Component
public class RequestTrackerServiceImpl implements RequestTrackerService {
    @Resource
    private ProviderRepository providerRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private RequestTrackerRepository requestTrackerRepository;
    @Resource
    private SubscriptionMapperServiceImpl subscriptionMapperService;

    @Override
    public String saveAndGetSubscriptionRedirectUrl(SubscriptionDTO data) {
        RequestTrackerEntity requestTrackerEntity = new RequestTrackerEntity();
        RequestTrackerEntity requestTrackerEntitySaved;
        requestTrackerEntity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        requestTrackerEntity.setPackageId(data.getPackageid());
        requestTrackerEntity.setProviderKey(data.getKey());
        requestTrackerEntity.setType("subscribe");
        requestTrackerEntity.setProductTrackId(data.getTransno());

        requestTrackerEntitySaved = requestTrackerRepository.save(requestTrackerEntity);

        Optional<ProviderEntity> providerEntityOptional = providerRepository.findByProviderKey(data.getKey());

        if (providerEntityOptional.isPresent()) {
            ProviderEntity providerEntity = providerEntityOptional.get();

            Optional<ProductEntity> productEntityOptional = productRepository.findByProviderKeyAndPackageId(requestTrackerEntity.getProviderKey(), requestTrackerEntity.getPackageId());

            if (productEntityOptional.isPresent()) {
                return SubscriptionUtil.updateSubscribeUrl(providerEntity.getSubscriptionUrl(), requestTrackerEntitySaved, productEntityOptional.get());
            }
        }

        return null;
    }

    @Override
    public String saveAndGetUnSubscribeRedirectUrl(UnsubscribeDTO data) {
        RequestTrackerEntity requestTrackerEntity = new RequestTrackerEntity();
        RequestTrackerEntity requestTrackerEntitySaved;
        requestTrackerEntity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        requestTrackerEntity.setPackageId(data.getPackageid());
        requestTrackerEntity.setProviderKey(data.getKey());
        requestTrackerEntity.setType("unsubscribe");
        requestTrackerEntity.setProductTrackId(data.getTransno());

        requestTrackerEntitySaved = requestTrackerRepository.save(requestTrackerEntity);

        Optional<ProviderEntity> providerEntityOptional = providerRepository.findByProviderKey(data.getKey());

        if (providerEntityOptional.isPresent()) {
            ProviderEntity providerEntity = providerEntityOptional.get();

            Optional<ProductEntity> productEntityOptional = productRepository.findByProviderKeyAndPackageId(requestTrackerEntity.getProviderKey(), requestTrackerEntity.getPackageId());

            if (productEntityOptional.isPresent()) {
                return SubscriptionUtil.updateSubscribeUrl(providerEntity.getUnsubscribeUrl(), requestTrackerEntitySaved, productEntityOptional.get());
            }
        }

        return null;
    }

    @Override
    public void updateNotification(Map<String, String> requestParams) {
        if (requestParams.containsKey("transno")) {
            String trackerIdString = requestParams.get("transno");
            Integer trackerId = Integer.valueOf(trackerIdString);
            Optional<RequestTrackerEntity> requestTrackerEntityOptional = requestTrackerRepository.findById(trackerId);

            if (!requestTrackerEntityOptional.isPresent()) {
                throw new ItemNotFoundException("Invalid Transaction ID sent");
            }

            RequestTrackerEntity requestTrackerEntity = requestTrackerEntityOptional.get();
            String notificationType = requestTrackerEntity.getType();

            if ("subscribe".equals(notificationType)) {
                subscriptionMapperService.saveSubscription(requestTrackerEntity, requestParams);
            } else if ("unsubscribe".equals(notificationType)) {
                subscriptionMapperService.saveUnSubscription(requestTrackerEntity, requestParams);
            }
        } else {
            throw new ItemNotFoundException("Transaction ID not sent");
        }

    }

    @Override
    public String getResponseRedirectionUrl(Map<String, String> requestParams) {
        ResponseRedirectDTO responseRedirectDTO = new ResponseRedirectDTO(requestParams);

        if (requestParams.containsKey("transno")) {
            String trackerIdString = responseRedirectDTO.getTransno();
            Integer trackerId = Integer.valueOf(trackerIdString);
            Optional<RequestTrackerEntity> requestTrackerEntityOptional = requestTrackerRepository.findById(trackerId);

            if (!requestTrackerEntityOptional.isPresent()) {
                throw new ItemNotFoundException("Invalid Transaction ID sent");
            }

            RequestTrackerEntity requestTrackerEntity = requestTrackerEntityOptional.get();
            String notificationType = requestTrackerEntity.getType();

            if ("subscribe".equals(notificationType) || "unsubscribe".equals(notificationType)) {
                Optional<ProductEntity> productEntityOptional = productRepository.findByProviderKeyAndPackageId(requestTrackerEntity.getProviderKey(), requestTrackerEntity.getPackageId());

                if (productEntityOptional.isPresent()) {
                    String notificationUrl = productEntityOptional.get().getNotificationUrl();

                    if (notificationUrl != null) {
                        try {
                            notificationUrl = SubscriptionUtil.updateRedirectUrl(notificationUrl, responseRedirectDTO, requestTrackerEntity);
                            SubscriptionUtil.getRequest(notificationUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    String redirectUrl = productEntityOptional.get().getRedirectUrl();

                    return SubscriptionUtil.updateRedirectUrl(redirectUrl, responseRedirectDTO, requestTrackerEntity);
                } else {
                    throw new ItemNotFoundException("Invalid Product ID sent");
                }
            }

            return "";
        } else {
            throw new ItemNotFoundException("Transaction ID not sent");
        }
    }
}
