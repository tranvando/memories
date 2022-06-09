package com.dotv.memories.dto;

import java.sql.Timestamp;

public class NotesDTO {
    private Integer id;
    private Integer idAcc;
    private String title;
    private String content;
    private java.sql.Timestamp createdDate;
    private Boolean statusHide;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(Integer idAcc) {
        this.idAcc = idAcc;
    }

    public String getTitle() {
        return this.title;
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

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatusHide() {
        return statusHide;
    }

    public void setStatusHide(Boolean statusHide) {
        this.statusHide = statusHide;
    }
}
