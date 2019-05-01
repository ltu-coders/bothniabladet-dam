package se.ltucoders.bothniabladetdam.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ImageUse")
public class ImageUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageUseId;

    @Column(name = "datePublished")
    private LocalDate datePublished;

    @Column(name = "title")
    private String title;

    @Column(name = "articleWrittenBy")
    private String articleWrittenBy;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "imageId")
    @JsonBackReference
    private Image image;

    public ImageUse() {
    }

    public int getImageUseId() {
        return imageUseId;
    }

    public void setImageUseId(int imageUseId) {
        this.imageUseId = imageUseId;
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDate datePublished) {
        this.datePublished = datePublished;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleWrittenBy() {
        return articleWrittenBy;
    }

    public void setArticleWrittenBy(String articleWrittenBy) {
        this.articleWrittenBy = articleWrittenBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
