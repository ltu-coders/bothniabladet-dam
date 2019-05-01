package se.ltucoders.bothniabladetdam.db;

import java.time.LocalDateTime;

public class SearchCriteria {
    private String[] tags;
    private int imageId;
    private String licenseType;
    private int author;
    private String resolution; //Not currently used
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String editor; //Not currently used
    private String lastModified; //Not currently used
    private String make;
    private String model;

    public SearchCriteria() {
    }

    public SearchCriteria(String[] tags,
                          int imageId,
                          String licenseType,
                          int author,
                          String resolution,
                          LocalDateTime fromDate,
                          LocalDateTime toDate,
                          String editor,
                          String lastModified,
                          String make,
                          String model) {
        this.tags = tags;
        this.imageId = imageId;
        this.licenseType = licenseType;
        this.author = author;
        this.resolution = resolution;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.editor = editor;
        this.lastModified = lastModified;
        this.make = make;
        this.model = model;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

//    public String getResolution() {
//        return resolution;
//    }
//
//    public void setResolution(String resolution) {
//        this.resolution = resolution;
//    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

//    public String getEditor() {
//        return editor;
//    }
//
//    public void setEditor(String editor) {
//        this.editor = editor;
//    }
//
//    public String getLastModified() {
//        return lastModified;
//    }
//
//    public void setLastModified(String lastModified) {
//        this.lastModified = lastModified;
//    }

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
}
