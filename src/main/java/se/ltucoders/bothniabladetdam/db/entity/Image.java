package se.ltucoders.bothniabladetdam.db.entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Images")
@Indexed
public class Image implements File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private int imageId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "filePath")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "author")
    @IndexedEmbedded
    private Users author;

    @Field
    @Column(name = "description")
    private String description;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "fileSize")
    private String fileSize;

    @Field
    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Field
    @Column(name = "make")
    private String make;

    @Field
    @Column(name = "model")
    private String model;

    @Field
    @Column(name = "location")
    private String location;

    @Field
    @Column(name = "licenseType")
    private String licenseType;

    @Field
    @Column(name = "noOfAllowedUses")
    private int noOfAllowedUses;

    @Field
    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "ImagesTags",
            joinColumns = @JoinColumn(name = "imageId"),
            inverseJoinColumns = @JoinColumn( name = "tagId"))
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "image",
            cascade =   {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<ImageCopy> imageCopies;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "image",
            cascade = CascadeType.ALL)
    private List<ImageUse> imageUses;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "image",
            cascade =   {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<OrderDetails> orderDetails;

    public Image() {
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public int getNoOfAllowedUses() {
        return noOfAllowedUses;
    }

    public void setNoOfAllowedUses(int noOfAllowedUses) {
        this.noOfAllowedUses = noOfAllowedUses;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<ImageCopy> getImageCopies() {
        return imageCopies;
    }

    public void setImageCopies(List<ImageCopy> imageCopies) {
        this.imageCopies = imageCopies;
    }

    public List<ImageUse> getImageUses() {
        return imageUses;
    }

    public void setImageUses(List<ImageUse> imageUses) {
        this.imageUses = imageUses;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addImageUse(ImageUse theImageUse) {
        if (imageUses == null) {
            imageUses = new ArrayList<>();
        }
        imageUses.add(theImageUse);
    }
}