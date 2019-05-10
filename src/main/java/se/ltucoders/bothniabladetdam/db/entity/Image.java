package se.ltucoders.bothniabladetdam.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Images")
public class Image implements File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private int imageId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "filePath")
    private String filePath;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "author")
    private Users author;

    @Column(name = "description")
    private String description;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "fileSize")
    private String fileSize;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "location")
    private String location;

    @Column(name = "licenseType")
    private String licenseType;

    @Column(name = "noOfAllowedUses")
    private int noOfAllowedUses;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "puid")
    private String puid;

    @Column(name = "filetype")
    private String filetype;

    @Column(name = "filetypeVersion")
    private String filetypeVersion;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH})
    @JoinTable(
            name = "ImagesTags",
            joinColumns = @JoinColumn(name = "imageId"),
            inverseJoinColumns = @JoinColumn( name = "tagId"))
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "image",
            cascade =   {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ImageCopy> imageCopies;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "image",
            cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<ImageUse> imageUses;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "image",
            cascade =   {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFiletypeVersion() {
        return filetypeVersion;
    }

    public void setFiletypeVersion(String filetypeVersion) {
        this.filetypeVersion = filetypeVersion;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<ImageCopy> getImageCopies() {
        return imageCopies;
    }

    public void setImageCopies(Set<ImageCopy> imageCopies) {
        this.imageCopies = imageCopies;
    }

    public Set<ImageUse> getImageUses() {
        return imageUses;
    }

    public void setImageUses(Set<ImageUse> imageUses) {
        this.imageUses = imageUses;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @JsonInclude
    public int getImageUseCount() {
        return this.imageUses.size();
    }

    public void addImageUse(ImageUse theImageUse) {
        if (imageUses == null) {
            imageUses = new HashSet<>();
        }
        imageUses.add(theImageUse);
    }
    public void addImageCopy(ImageCopy theImageCopy) {
        if (imageCopies != null) {
            imageCopies = new HashSet<>();
        }
        imageCopies.add(theImageCopy);
    }
    public void addTag(Tag theTag){
        if (tags == null){
            tags = new HashSet<>();
        }
        tags.add(theTag);

    }    public void addTag(Set<Tag> theTags){
        if (tags == null){
            tags = new HashSet<>();
        }
        tags.addAll(theTags);
    }

}