package com.rayfay.bizcloud.microservices.poc.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rayfay.bizcloud.microservices.poc.entity.CarBoxSearchCondition;
import com.rayfay.bizcloud.microservices.poc.entity.T_car_box;
import com.rayfay.bizcloud.microservices.poc.entity.UserInfo;
import com.rayfay.bizcloud.microservices.poc.repository.ICarBoxRepository;
import com.rayfay.bizcloud.microservices.poc.util.BizCloudSorter;

@Service("carBoxService")
public class CarBoxService {
	@Autowired
	private ICarBoxRepository icarBoxRepository;
	
	/**
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @param searchCondition
	 * @param sorts
	 * @return
	 */
    public Page<T_car_box> findDataPageable(int pageSize, int pageNumber, CarBoxSearchCondition searchCondition, List<BizCloudSorter> sorts) {

        Specification spe = new Specification<T_car_box>() {
            @Override
            public Predicate toPredicate(Root<T_car_box> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (searchCondition != null) {
                    if (StringUtils.isNotEmpty(searchCondition.getCar())) {
                        predicates.add(
                                criteriaBuilder.like(root.get("car"),
                                        "%" + searchCondition.getCar() + "%"));
                    }
                    if (StringUtils.isNotEmpty(searchCondition.getBox())) {
                        predicates.add(
                                criteriaBuilder.like(root.get("box"),
                                        "%" + searchCondition.getBox() + "%"));
                    }
                   
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };

        if (sorts != null && sorts.size() > 0) {
            List<Sort.Order> orderList = new ArrayList<>();
            sorts.forEach(sort -> {
                orderList.add(new Sort.Order(sort.getDirection(), sort.getSortField()));
            });
            Sort sort = new Sort(orderList);
            return (Page<T_car_box>) icarBoxRepository.findAll(spe, new PageRequest(pageNumber, pageSize, sort));

        } else {
            return (Page<T_car_box>) icarBoxRepository.findAll(spe, new PageRequest(pageNumber, pageSize));
        }
    }
    
    /**
     * 根据ID查询信息
     *
     * @param userId 人员ID
     * @return {@link T_car_box}
     */
    public T_car_box findCarBoxById(Long id) {

//        UserInfo record = userInfoRepository.findOne(new Specification<UserInfo>() {
//            @Override
//            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery,
//                                         CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<>();
//                predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
//                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
//            }
//        });
    	T_car_box record =  icarBoxRepository.findOne(id);
    	return record;
    }

    /**
     * 删除信息
     *
     * @param id ID
     */
    public void deleteCarBox(long id) {
    	icarBoxRepository.delete(id);
    }
    
    
    public void insertCarBox(T_car_box carBox){
    	icarBoxRepository.save(carBox);
    }
    
}
