package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("userDao")
@Transactional
public class UserDAOImpl extends AbstractJpaDAO<User> implements UserDao {

    @Override
    public User findByUserName(String userName) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("user_name"), userName));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public User findByEmail(String email) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("email"), email));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
