package com.blogspot.noteoneverything.chatboard.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;

@Entity
@Table(name = "board_responses")
public class BoardResponse{
    
    private MessageType type;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String response;
    @ManyToOne
    private User user;
    @ManyToOne
    private Board board;
    private boolean is_deleted;
    private Date updated;
    private Date created;

    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    public MessageType getType(){
        return type;
    }
    public void setType(MessageType type){
        this.type = type;
    }
    public String getResponse(){
        return this.response;
    }
    public void setResponse(String response){
        this.response = response;
    }
    public User getUser(){
        return this.user;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setBoard(Board board){
        this.board = board;
    }
    public Board getBoard(){
        return this.board;
    }
    public void setIsDeleted(boolean is_deleted){
        this.is_deleted = is_deleted;
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