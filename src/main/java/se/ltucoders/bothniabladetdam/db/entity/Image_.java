package se.ltucoders.bothniabladetdam.db.entity;


import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Image.class)
public abstract class Image_ {

    public static volatile SingularAttribute<Image, Integer> imageId;
    public static volatile SingularAttribute<Image, String> fileName;
    public static volatile SingularAttribute<Image, String> filePath;
    public static volatile SingularAttribute<Image, Users> author;
    public static volatile SingularAttribute<Image, String> description;
    public static volatile SingularAttribute<Image, String> resolution;
    public static volatile SingularAttribute<Image, String> width;
    public static volatile SingularAttribute<Image, String> height;
    public static volatile SingularAttribute<Image, String> fileSize;
    public static volatile SingularAttribute<Image, LocalDateTime> dateTime;
    public static volatile SingularAttribute<Image, String> make;
    public static volatile SingularAttribute<Image, String> model;
    public static volatile SingularAttribute<Image, String> location;
    public static volatile SingularAttribute<Image, String> licenseType;
    public static volatile SingularAttribute<Image, Integer> noOfAllowedUses;
    public static volatile SingularAttribute<Image, BigDecimal> price;
    public static volatile SetAttribute<Image, Set> tags;
    public static volatile SetAttribute<Image, List> imageCopies;
    public static volatile ListAttribute<Image, List> imageUses;
    public static volatile ListAttribute<Image, List> orderDetails;


    public static final String IMAGE_ID = "imageId";
    public static final String FILE_NAME = "fileName";
    public static final String FILE_PATH = "filePath";
    public static final String AUTHOR = "author";
    public static final String DESCRIPTION = "description";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String RESOLUTION = "resolution";
    public static final String FILE_SIZE = "fileSize";
    public static final String DATE_TIME = "dateTime";
    public static final String MAKE = "make";
    public static final String MODEL = "model";
    public static final String LOCATION = "location";
    public static final String LICENSE_TYPE = "licenseType";
    public static final String NO_OF_ALLOWED_USES = "noOfAllowedUses";
    public static final String PRICE = "price";
    public static final String TAGS = "tags";
    public static final String IMAGE_COPIES = "imageCopies";
    public static final String IMAGE_USES = "imageUses";
    public static final String ORDER_DETAILS = "orderDetails";
}
