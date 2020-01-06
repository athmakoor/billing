package com.ref.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ref.bean.jpa.TrackerEntity;


public interface TrackerRepository extends PagingAndSortingRepository<TrackerEntity, Integer> {
}
