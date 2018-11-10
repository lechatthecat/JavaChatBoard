package com.blogspot.noteoneverything.chatboard.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.blogspot.noteoneverything.chatboard.util.Constants;

import com.blogspot.noteoneverything.chatboard.model.User;

@Entity
@Table(name = "user_images")
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String detail;
    @ManyToOne
    private User user;
    private String path;
    private boolean is_main;
    private Date updated;
    private Date created;
    private boolean is_deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public String getPath() {
        if (this.path == null || this.path.equals("")) {
            return Constants.BLANK_USERIMAGE_PATH;
        }
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setIsMain(boolean is_main) {
        this.is_main = is_main;
    }

    public boolean getIsMain() {
        return this.is_main;
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

    public void setIsDeleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean getIsDeleted() {
        return this.is_deleted;
    }
}