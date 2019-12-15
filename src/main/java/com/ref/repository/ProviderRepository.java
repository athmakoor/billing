package com.ref.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ref.bean.jpa.ProviderEntity;

public interface ProviderRepository  extends PagingAndSortingRepository<ProviderEntity, Integer> {
    Optional<ProviderEntity> findByProviderKey(String providerKey);
}
