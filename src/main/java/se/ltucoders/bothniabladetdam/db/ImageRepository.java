package se.ltucoders.bothniabladetdam.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.Image;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return images;
    }

    @Transactional
    public List<Image> findByKeyword(String [] tags) {
        List<Image> images = null;
        try (Session session = entityManager.unwrap(Session.class)){
            String hql = "select distinct i from Image i " +
                    "join i.tags t " +
                    "where t.tagName in (:tags)";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setParameterList("tags", tags);
            images = query.getResultList();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return images;
    }

    @Transactional
    public Image findById(int theId) {
        Image imageToReturn = null;
        try (Session session = entityManager.unwrap(Session.class)){
            imageToReturn = session.get(Image.class, theId);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return imageToReturn;
    }

    @Transactional
    public Image save(Image theImage) {

        try (Session session = entityManager.unwrap(Session.class)) {
            session.save(theImage);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return theImage;
    }
    public List<Image> advancedSearch(SearchCriteria searchCriteria) {

        try (Session session = entityManager.unwrap(Session.class)){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Image> criteriaQuery = criteriaBuilder.createQuery(Image.class);
            Root<Image> imageRoot = criteriaQuery.from(Image.class);

            criteriaQuery.select(imageRoot);
            org.hibernate.query.Query<Image> imageQuery = session.createQuery(criteriaQuery);
            List<Image> images = imageQuery.getResultList();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
