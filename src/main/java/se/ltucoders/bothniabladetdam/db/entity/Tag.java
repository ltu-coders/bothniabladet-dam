package se.ltucoders.bothniabladetdam.db.entity;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tags")
@Indexed
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagId")
    private int tagId;

    @Column(name = "tagName")
    private String tagName;

    @ManyToMany
    @JoinTable(
            name = "ImagesTags",
            joinColumns = @JoinColumn(name = "tagId"),
            inverseJoinColumns = @JoinColumn( name = "imageId"))
    private List<Image> images;

    public Tag() {
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
