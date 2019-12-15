package com.ref.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ref.bean.jpa.SubscriptionEntity;

public interface SubscriptionRepository extends PagingAndSortingRepository<SubscriptionEntity, Integer> {
}
