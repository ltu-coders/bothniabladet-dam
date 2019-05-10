package se.ltucoders.bothniabladetdam.db;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.ImageUse;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class ImageUseRepository {

    private EntityManager entityManager;

    @Autowired
    public ImageUseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(ImageUse imageUse){
        Session session = entityManager.unwrap(Session.class);
        session.save(imageUse);
    }
}
