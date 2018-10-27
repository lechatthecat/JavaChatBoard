package com.blogspot.noteoneverything.chatboard.model;

import java.util.Date;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.blogspot.noteoneverything.chatboard.model.User;

@Entity
@Table(name = "boards")
public class Board{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private User user;
    @OneToMany
    private Collection<BoardResponse> boardResponses;
    private String name;
    private String detail;
    private boolean is_deleted;
    private Date updated;
    private Date created;

    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    public User getUser(){
        return this.user;
    }
    public void setUser(User user){
        this.user = user;
    }
    public String getName(){
        return this.name;
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
    public void setIsDeleted(boolean is_deleted){
        this.is_deleted= is_deleted;
    } 
    public boolean getIsDeleted(){
        return this.is_deleted;
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
}