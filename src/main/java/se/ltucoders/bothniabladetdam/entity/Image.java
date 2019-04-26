package se.ltucoders.bothniabladetdam.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private int imageId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "filePath")
    private String filePath;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "author")
    private Users author;

    @Column(name = "description")
    private String description;

    @Column(name = "resolution")
    private String resolution;

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
}