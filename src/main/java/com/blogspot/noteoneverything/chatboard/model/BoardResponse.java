package com.blogspot.noteoneverything.chatboard.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.blogspot.noteoneverything.chatboard.model.Board;

@Entity
@Table(name = "board_responses")
public class BoardResponse {

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String response;
    @JsonIgnore
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private Board board;
    private boolean is_seen;
    private boolean is_deleted;
    @Transient
    private String sender;
    @Transient
    private long b_id;
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

    public void setIsSeen(boolean is_seen) {
        this.is_seen = is_seen;
    }

    public boolean getIsSeen() {
        return this.is_seen;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getBId() {
        return this.b_id;
    }

    public void setBID(long b_id) {
        this.b_id = b_id;
    }
}