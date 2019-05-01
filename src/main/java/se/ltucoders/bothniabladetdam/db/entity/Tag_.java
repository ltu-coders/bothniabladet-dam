package se.ltucoders.bothniabladetdam.db.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Set;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public class Tag_ {

    public static volatile SingularAttribute<Tag, Integer> tagId;
    public static volatile SingularAttribute<Tag, String> tagName;
    public static volatile SetAttribute<Tag, Set> images;

    public static final String TAG_ID = "tagId";
    public static final String TAG_NAME = "tagName";
    public static final String IMAGES = "images";


}
