package se.ltucoders.bothniabladetdam.db;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.ImageCopy;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class ImageCopyRepository {

    private EntityManager entityManager;

    @Autowired
    public ImageCopyRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(ImageCopy imageCopy) {
        Session session = entityManager.unwrap(Session.class);
        session.save(imageCopy);
    }
}
