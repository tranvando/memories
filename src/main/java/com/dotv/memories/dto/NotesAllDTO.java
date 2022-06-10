package com.dotv.memories.dto;

import java.sql.Timestamp;

public class NotesAllDTO {
    private Integer id;
    private String title;
    private String content;
    private java.sql.Timestamp createdDate;
    private java.sql.Timestamp showDate;
    private String fullName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Timestamp getShowDate() {
        return showDate;
    }

    public void setShowDate(Timestamp showDate) {
        this.showDate = showDate;
    }
}
