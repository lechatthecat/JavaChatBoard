package com.blogspot.noteoneverything.chatboard.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import javax.persistence.PrimaryKeyJoinColumn;

import com.blogspot.noteoneverything.chatboard.util.Constants;
import com.blogspot.noteoneverything.chatboard.model.User;

@Entity
@Table(name = "user_images")
public class UserImage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String detail;
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;
    private String path = Constants.BLANK_USERIMAGE_PATH;
    private Date updated;
    private Date created;
    private boolean is_deleted;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public String getUsername(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDetail(){
        return this.detail;
    }
    public void setDetail(String detail){
        this.detail = detail;
    }
    public String getPath(){
        return this.path;
    }
    public void setPath(String path){
        this.path = path;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
    public void setUpdated(Date updated){
        this.updated = updated;
    }
    public Date getUpdated(){
        return this.updated;
    }
    public void setCreated(Date created){
        this.created = created;
    }
    public Date getCreated(){
        return this.created;
    }
    public void setIsDeleted(boolean is_deleted){
        this.is_deleted = is_deleted;
    } 
    public boolean getIsDeleted(){
        return this.is_deleted;
    }
}