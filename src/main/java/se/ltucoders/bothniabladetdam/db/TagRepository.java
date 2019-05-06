package se.ltucoders.bothniabladetdam.db;

import org.hibernate.Session;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ltucoders.bothniabladetdam.db.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class TagRepository {

    private EntityManager entityManager;

    @Autowired
    public TagRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Tag theTag) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(theTag);
    }

    public Set<Tag> getTagByString(String[] tags) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "select distinct t from Tag t " +
                "where t.tagName in (:tags)";
        Query query = session.createQuery(hql);
        ((org.hibernate.query.Query) query).setParameterList("tags", tags);

        return new HashSet<>(query.getResultList());
    }
}
