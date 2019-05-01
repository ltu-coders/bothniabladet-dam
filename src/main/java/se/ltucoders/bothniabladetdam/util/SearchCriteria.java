package se.ltucoders.bothniabladetdam.util;

import java.time.LocalDateTime;

// Collects different parameters that come form a search
// and are to be used by the database classes.
public class SearchCriteria {
    String tags;
    int imageId;
    String licenceType;
    String author;
    int resolution;
    String fromDate;
    String toDate;
    String editor;
    String lastModified;
    String make;
    String model;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public LocalDateTime getFromDate() {
        return LocalDateTime.parse(this.fromDate);
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return LocalDateTime.parse(this.toDate);
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public LocalDateTime getLastModified() {
        return LocalDateTime.parse(this.lastModified);
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
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
}
