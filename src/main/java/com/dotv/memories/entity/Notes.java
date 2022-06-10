package com.dotv.memories.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "notes")
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_acc")
    private Integer idAcc;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private java.sql.Timestamp createdDate;

    @Column(name = "show_date")
    private java.sql.Timestamp showDate;

    @Column(name = "status_hide")
    private Boolean statusHide;

    @Column(name = "status")
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

    public Timestamp getShowDate() {
        return showDate;
    }

    public void setShowDate(Timestamp showDate) {
        this.showDate = showDate;
    }
}
