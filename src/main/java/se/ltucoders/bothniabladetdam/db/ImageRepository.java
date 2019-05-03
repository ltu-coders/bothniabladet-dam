package se.ltucoders.bothniabladetdam.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ImageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Image> findAll(){

        List<Image> images = null;
        Session session = entityManager.unwrap(Session.class);
        try  {
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
        Session session = entityManager.unwrap(Session.class);
        try {
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
        Image imageToReturn = new Image();
        Session session = entityManager.unwrap(Session.class);
        try {
            imageToReturn = session.get(Image.class, theId);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return imageToReturn;
    }

    @Transactional
    public Image save(Image theImage) {
        Session session = entityManager.unwrap(Session.class);
        try {
            session.save(theImage);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return theImage;
    }
    @Transactional
    public List<Image> search(SearchCriteria searchCriteria) {
        Session session = entityManager.unwrap(Session.class);
        try {
            String sql = "select distinct it.imageId from ImagesTags it " +
                    "inner join Tags t " +
                    "where t.tagId = it.tagId AND (t.tagName IN :tags)";

            //Fetches the id's of the images mapped to specific tags
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameterList("tags", searchCriteria.getTags());

            List<Integer> imagesId = query.getResultList();

            CriteriaBuilder cBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Image> criteriaQuery = cBuilder.createQuery(Image.class);
            Root<Image> imageRoot = criteriaQuery.from(Image.class);


            List<Predicate> tagPredicate = new ArrayList<>();   //Holds the list of id's to be added to the OR condition
            List<Predicate> predicateList = new ArrayList<>();  //in the where clause

            //If there are > 0 tags add them as predicates
            if (searchCriteria.getTags().length > 0) {
                for (int loop = 0; loop < imagesId.size(); loop++) {
                    tagPredicate.add(cBuilder.equal(imageRoot.get("imageId"), imagesId.get(loop)));
                }
            }
            //if author is a search criteria, add that as a predicate
            if (searchCriteria.getAuthor()>0){
                predicateList.add(cBuilder.equal(imageRoot.get("author"), searchCriteria.getAuthor()));
            }
            if (searchCriteria.getFromDate() != null) {
                predicateList.add(cBuilder.greaterThanOrEqualTo(imageRoot.get("dateTime"), searchCriteria.getFromDate()));
            }
            if (searchCriteria.getToDate() != null) {
                predicateList.add(cBuilder.lessThanOrEqualTo(imageRoot.get("dateTime"), searchCriteria.getToDate()));
            }

            //Add the list of imageId predicates as an OR condition
            Predicate keywords = cBuilder.or(tagPredicate.toArray(new Predicate[0]));

            //Adds the other criterias as AND conditions
            Predicate finalPredicate = cBuilder.and(predicateList.toArray(new Predicate[0]));
            criteriaQuery.where(finalPredicate, keywords).distinct(true);
            org.hibernate.query.Query<Image> imageQuery = session.createQuery(criteriaQuery);

            return imageQuery.getResultList();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
