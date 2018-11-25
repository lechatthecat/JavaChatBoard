package com.blogspot.noteoneverything.chatboard.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Transient;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardResponse> boardResponses;
    private String name;
    private String detail;
    private boolean is_private;
    private boolean is_deleted;
    private Date updated;
    private Date created;
    @Transient
    private boolean agreesTerm = false;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<BoardResponse> getBoardResponses() {
        return this.boardResponses;
    }

    public void setBoardResponses(List<BoardResponse> boardResponses) {
        this.boardResponses = boardResponses;
    }

    public void setIsPrivate(boolean is_private) {
        this.is_private = is_private;
    }

    public boolean getIsPrivate() {
        return this.is_private;
    }

    public void setIsDeleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean getIsDeleted() {
        return this.is_deleted;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return this.created;
    }

    public boolean getAgreesTerm() {
        return this.agreesTerm;
    }

    public void setAgreesTerm(boolean agreesTerm) {
        this.agreesTerm = agreesTerm;
    }
}