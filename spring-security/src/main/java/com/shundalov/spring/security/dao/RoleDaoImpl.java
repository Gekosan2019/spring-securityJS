package com.shundalov.spring.security.dao;

import com.shundalov.spring.security.model.Role;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements  RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String username) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.username = :username", Role.class)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.id = :id", Role.class)
                .setParameter("id", id).getSingleResult();
    }
}
