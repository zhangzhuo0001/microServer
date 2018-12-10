package com.rayfay.bizcloud.microservices.poc.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rayfay.bizcloud.microservices.poc.entity.T_car_box;


public interface ICarBoxRepository extends CrudRepository<T_car_box, Long>, PagingAndSortingRepository<T_car_box, Long>, JpaSpecificationExecutor<T_car_box>  {

}
