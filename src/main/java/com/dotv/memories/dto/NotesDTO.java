package com.dotv.memories.dto;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public class NotesDTO {
    private Integer id;
    private Integer idAcc;
    private String title;
    private String content;
    private String image;
    private String viewImage;
    private java.sql.Timestamp createdDate;
    private java.sql.Timestamp updatedDate;
    private Boolean status;
    private MultipartFile fileImage[];

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

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public MultipartFile[] getFileImage() {
        return fileImage;
    }

    public void setFileImage(MultipartFile[] fileImage) {
        this.fileImage = fileImage;
    }

    public String getViewImage() {
        return viewImage;
    }

    public void setViewImage(String viewImage) {
        this.viewImage = viewImage;
    }
}
