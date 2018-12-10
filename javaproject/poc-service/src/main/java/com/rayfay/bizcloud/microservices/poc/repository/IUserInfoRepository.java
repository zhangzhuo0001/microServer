package com.rayfay.bizcloud.microservices.poc.repository;

import com.rayfay.bizcloud.microservices.poc.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUserInfoRepository extends CrudRepository<UserInfo, Long>, PagingAndSortingRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
}
 