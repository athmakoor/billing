package com.ref.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ref.bean.jpa.RequestTrackerEntity;

public interface RequestTrackerRepository extends PagingAndSortingRepository<RequestTrackerEntity, Integer> {
}
