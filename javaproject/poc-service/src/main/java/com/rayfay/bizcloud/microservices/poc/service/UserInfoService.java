package com.rayfay.bizcloud.microservices.poc.service;

import com.rayfay.bizcloud.microservices.poc.entity.UserInfo;
import com.rayfay.bizcloud.microservices.poc.entity.UserSearchCondition;
import com.rayfay.bizcloud.microservices.poc.repository.IUserInfoRepository;
import com.rayfay.bizcloud.microservices.poc.util.BizCloudSorter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserInfoService {
    private IUserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(IUserInfoRepository userRepository) {
        this.userInfoRepository = userRepository;
    }

    /**
     * 新增人员身份信息
     *
     * @param user {@link UserInfo} 人员身份对象
     * @return {@link UserInfo} 保存的人员身份对象
     */
    public UserInfo insertUser(UserInfo user) {
        return userInfoRepository.save(user);
    }

    /**
     * 根据人员ID查询人员信息
     *
     * @param userId 人员ID
     * @return {@link UserInfo}人员身份对象
     */
    public UserInfo findUserById(Long userId) {

        UserInfo record = userInfoRepository.findOne(new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        });
//    	UserInfo record =  userInfoRepository.findOne(userId);
    	return record;
    }

    /**
     * 删除人员身份信息
     *
     * @param userId 人员ID
     */
    public void deleteUser(long userId) {
        userInfoRepository.delete(userId);
    }

    /**
     * 根据查询条件查询人员身份信息(分页)
     *
     * @param pageSize        每页记录数
     * @param pageNumber      页码
     * @param searchCondition {@link UserSearchCondition}查询条件
     *
     *                        <pre>
     *                                       {<br>
     *                                           name: "姓名",<br>
     *                                           sex: "性别",<br>
     *                                           startBirthday: "开始生日",<br>
     *                                           endBirthday: "结束生日",<br>
     *                                       }<br>
     *                                   </pre>
     * @param sorts           List<{@link BizCloudSorter}>排序
     * @return Page<{   @   link       UserInfo   }> 查询结果记录
     */
    public Page<UserInfo> findUserPageable(int pageSize, int pageNumber, UserSearchCondition searchCondition, List<BizCloudSorter> sorts) {

        Specification spe = new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (searchCondition != null) {
                    if (StringUtils.isNotEmpty(searchCondition.getName())) {
                        predicates.add(
                                criteriaBuilder.like(root.get("name"),
                                        "%" + searchCondition.getName() + "%"));
                    }
                    if (0 != searchCondition.getSex()) {
                        predicates.add(criteriaBuilder.equal(root.get("sex"), searchCondition.getSex()));
                    }
                    if (0 != searchCondition.getStartBirthday()) {
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Timestamp(searchCondition.getStartBirthday())));
                    }
                    if (0 != searchCondition.getEndBirthday()) {
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Timestamp(searchCondition.getEndBirthday())));
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
            return (Page<UserInfo>) userInfoRepository.findAll(spe, new PageRequest(pageNumber, pageSize, sort));

        } else {
            return (Page<UserInfo>) userInfoRepository.findAll(spe, new PageRequest(pageNumber, pageSize));
        }
    }
}
