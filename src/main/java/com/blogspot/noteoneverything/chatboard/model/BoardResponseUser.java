package com.blogspot.noteoneverything.chatboard.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

@Entity
@Table(name = "board_users")
public class BoardResponseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private BoardResponse boardResponse;
    private boolean is_seen;
    private boolean is_deleted;
    private Date updated;
    private Date created;

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

    public void setBoardResponse(BoardResponse boardResponse) {
        this.boardResponse = boardResponse;
    }

    public BoardResponse getBoardRBoard() {
        return this.boardResponse;
    }

    public void setIsSeen(boolean is_seen) {
        this.is_seen = is_seen;
    }

    public boolean getIsSeen() {
        return this.is_seen;
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
}