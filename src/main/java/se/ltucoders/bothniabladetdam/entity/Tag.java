package se.ltucoders.bothniabladetdam.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tags")
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

}
