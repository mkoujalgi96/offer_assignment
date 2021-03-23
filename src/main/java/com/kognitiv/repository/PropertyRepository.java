package com.kognitiv.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kognitiv.entity.PropertyEntity;

@Repository
public interface PropertyRepository extends PagingAndSortingRepository<PropertyEntity, Long> {

	List<PropertyEntity> findByPropertyCodeIn(List<String> propertyCode);
	
}
