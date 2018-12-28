package com.blogspot.noteoneverything.chatboard.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Date;
import javax.persistence.Transient;
import com.blogspot.noteoneverything.chatboard.util.Utility;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.blogspot.noteoneverything.chatboard.model.Board;

@Entity
@Table(name = "board_responses")
public class BoardResponse {

    public enum MessageType {
        CHAT, 
        JOIN, 
        LEAVE
    }

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String response;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Board board;
    private boolean is_deleted;
    @Transient
    private String sender;
    @Transient
    private long b_id;
    @Transient
    private String user_image_path;
    private Date updated;
    private Date created;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setIsDeleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean getIsDeleted() {
        return this.is_deleted;
    }

    public void setUserImagePath(String user_image_path) {
        this.user_image_path = user_image_path;
    }

    public String getUserImagePath() {
        return this.user_image_path;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUpdated() {
        return Utility.formatDate(this.updated);
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreated() {
        return Utility.formatDate(this.created);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getBId() {
        if(this.board!=null){
            this.b_id = this.board.getId();
            return this.b_id;
        }else{
            return this.b_id;
        }
    }

    public void setBId(long b_id) {
        this.b_id = b_id;
    }
}