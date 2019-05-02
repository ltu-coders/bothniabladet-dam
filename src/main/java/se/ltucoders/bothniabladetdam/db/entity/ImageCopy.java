package se.ltucoders.bothniabladetdam.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ImageCopy")
public class ImageCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageCopyId")
    private int imageCopyId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "imageId")
    @JsonBackReference
    private Image image;

    @Column(insertable = false, updatable = false)
    @Transient
    private int originalImageId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "newImageId")
    @JsonBackReference
    private Image newImage;

    @Transient
    private String filePath;

    @Transient
    private int modifiedById;

    @Transient
    private String fileName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "modifiedBy")
    @JsonBackReference
    private Users modifiedBy;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Column(name = "description")
    private String description;

    public ImageCopy() {
    }

    public ImageCopy(Image image, Users modifiedBy, LocalDateTime dateTime, String description) {
        this.image = image;
        this.modifiedBy = modifiedBy;
        this.dateTime = dateTime;
        this.description = description;
    }

    public int getImageCopyId() {
        return imageCopyId;
    }

    public void setImageCopyId(int imageCopyId) {
        this.imageCopyId = imageCopyId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getNewImage() {
        return newImage;
    }

    public void setNewImage(Image newImage) {
        this.newImage = newImage;
    }

    public int getOriginalImageId() {
        return originalImageId;
    }

    public void setOriginalImageId(int originalImageId) {
        this.originalImageId = originalImageId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(int modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Users getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Users modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @PostLoad
    protected void initFields(){
        this.originalImageId = image.getImageId();
        this.fileName = newImage.getFileName();
        this.filePath = newImage.getFilePath();
        this.modifiedById = newImage.getAuthor().getUserId();
    }
}
