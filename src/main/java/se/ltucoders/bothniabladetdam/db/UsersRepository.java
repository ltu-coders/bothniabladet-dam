package se.ltucoders.bothniabladetdam.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UsersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UsersRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Users getUserByUsername(String theUsername){
        Session session = entityManager.unwrap(Session.class);
        try {
            Query query = session.createQuery("from Users where userName =:userName");
            query.setParameter("userName", theUsername);
            return (Users) ((org.hibernate.query.Query) query).uniqueResult();
        } catch (HibernateException hex) {
            hex.printStackTrace();
        }
        return null;
    }

    @Transactional
    public Users getUserById(int theId) {
        Session session = entityManager.unwrap(Session.class);
        try {
            return session.get(Users.class, theId);
        } catch (HibernateException hex) {
            hex.printStackTrace();
        }
        return new Users();
    }
}
