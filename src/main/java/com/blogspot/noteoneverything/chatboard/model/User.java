package com.blogspot.noteoneverything.chatboard.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import com.blogspot.noteoneverything.chatboard.model.Role;
import com.blogspot.noteoneverything.chatboard.model.UserImage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Email
    private String email;
    @ManyToOne
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<UserImage> userImages;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String password;
    @Transient
    private UserImage user_main_image;
    private Date updated;
    private Date created;
    @Transient
    private String passwordConfirm;
    private boolean is_deleted;
    @Transient
    private boolean agreesTerm = false;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<BoardResponse> boardResponses;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Board> boards;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return this.birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
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

    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setUserMainImage(UserImage user_main_image) {
        this.user_main_image = user_main_image;
    };

    public UserImage getUserMainImage() {
        if (this.user_main_image == null) {
            this.user_main_image = new UserImage();
        } else {
            for (UserImage image : userImages) {
                if (image.getIsMain()) {
                    this.user_main_image = image;
                    break;
                }
            }
        }
        return this.user_main_image;
    };

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<UserImage> getUserImages() {
        return this.userImages;
    }

    public void setUserImages(List<UserImage> userImages) {
        this.userImages = userImages;
    }

    public void setIsDeleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean getIsDeleted() {
        return this.is_deleted;
    }

    public boolean getAgreesTerm() {
        return this.agreesTerm;
    }

    public void setAgreesTerm(boolean agreesTerm) {
        this.agreesTerm = agreesTerm;
    }

    public List<Board> getBoards() {
        return this.boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public List<BoardResponse> getBoardResposes() {
        return this.boardResponses;
    }

    public void setBoardResponses(List<BoardResponse> boardResponses) {
        this.boardResponses = boardResponses;
    }
}