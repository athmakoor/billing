package com.ref.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ref.bean.jpa.ProductEntity;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findByProviderKeyAndPackageId(String providerKey, String packageId);
}
