package se.ltucoders.bothniabladetdam.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.Users;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class UsersRepository {

    private EntityManager entityManager;

    @Autowired
    public UsersRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Users getUserByUsername(String theUsername){

        try (Session session = entityManager.unwrap(Session.class)){
            Query query = session.createQuery("from Users where userName =:userName");
            query.setParameter("userName", theUsername);
            return (Users) ((org.hibernate.query.Query) query).uniqueResult();
        }
    }

    @Transactional
    public Users getUserById(int theId) {

        try (Session session = entityManager.unwrap(Session.class)){
            return session.get(Users.class, theId);
        } catch (HibernateException hex) {
            hex.printStackTrace();
        }
        return new Users();
    }
}
