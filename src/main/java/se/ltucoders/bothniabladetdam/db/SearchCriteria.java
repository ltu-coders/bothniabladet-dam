package se.ltucoders.bothniabladetdam.db;

import java.time.LocalDateTime;

/*
Class providing functionality for searching the database
*/
public class SearchCriteria {
    private String[] tags;
    private String licenseType;
    private String author;
    private String resolution;
    private int maxWidth;
    private int minWidth;
    private int maxHeight;
    private int minHeight;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String editor; 
    private String lastModified; 
    private String make;
    private String model;

    public SearchCriteria() {
    }

    public SearchCriteria(String[] tags,
                          String licenseType,
                          String author,
                          String resolution,
                          int maxWidth,
                          int minWidth,
                          int maxHeight,
                          int minHeight,
                          LocalDateTime fromDate,
                          LocalDateTime toDate,
                          String editor,
                          String lastModified,
                          String make,
                          String model) {
        this.tags = tags;
        this.licenseType = licenseType;
        this.author = author;
        this.resolution = resolution;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.editor = editor;
        this.lastModified = lastModified;
        this.make = make;
        this.model = model;
    }

    public SearchCriteria(String[] tags, String author, LocalDateTime fromDate, LocalDateTime toDate) {
        this.tags = tags;
        this.author = author;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }



    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

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

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getLastModified() {
        return lastModified;
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
