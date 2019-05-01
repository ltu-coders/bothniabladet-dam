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
}
