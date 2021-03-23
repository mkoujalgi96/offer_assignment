package com.kognitiv.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kognitiv.entity.OfferEntity;

@Repository
public interface OfferRepository extends PagingAndSortingRepository<OfferEntity, Long> {

}
