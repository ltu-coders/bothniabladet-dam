package se.ltucoders.bothniabladetdam.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ImageCopyRepository {

    private EntityManager entityManager;

    @Autowired
    public ImageCopyRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
