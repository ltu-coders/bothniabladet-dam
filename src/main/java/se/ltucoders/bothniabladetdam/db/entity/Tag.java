package se.ltucoders.bothniabladetdam.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ImagesTags",
            joinColumns = @JoinColumn(name = "tagId"),
            inverseJoinColumns = @JoinColumn( name = "imageId"))
    @JsonBackReference
    private Set<Image> images;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagName.equals(tag.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }
}
