package se.ltucoders.bothniabladetdam.db;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.Image;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ImageRepository {

    private EntityManager entityManager;

    @Autowired
    public ImageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Image> findAll(){

        List<Image> images = null;
        try (Session session = entityManager.unwrap(Session.class)) {

            Query query = session.createQuery("from Image");
            images = query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return images;
    }

    @Transactional
    public Image findById(int theId) {
        Image imageToReturn = null;
        try (Session session = entityManager.unwrap(Session.class)){
            imageToReturn = session.get(Image.class, theId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return imageToReturn;
    }

    @Transactional
    public Image save(Image theImage) {

        try (Session session = entityManager.unwrap(Session.class)) {
            session.save(theImage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return theImage;
    }
}
