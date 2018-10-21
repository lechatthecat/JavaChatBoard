package com.blogspot.noteoneverything.chatboard.model;

import java.util.Date;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.Email;

import com.blogspot.noteoneverything.chatboard.model.Role;
import com.blogspot.noteoneverything.chatboard.model.UserImage;

@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Email
    private String email;
    @ManyToOne
    private Role role;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<UserImage> userImages;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String password;
    private Date updated;
    private Date created;
    @Transient
    private String passwordConfirm;
    private boolean is_deleted;
    @OneToOne(cascade = CascadeType.ALL)
    private UserImage userImage;
    @Transient
    private boolean agreesTerm = false;


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
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public Date getBirth(){
        return this.birth;
    }
    public void setBirth(Date birth){
        this.birth = birth;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
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
    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    public Role getRole() {
        return this.role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public Set<UserImage> getUserImages() {
        return this.userImages;
    }
    public void setUserImages(Set<UserImage> userImages) {
        this.userImages = userImages;
    }
    public void setIsDeleted(boolean is_deleted){
        this.is_deleted = is_deleted;
    } 
    public boolean getIsDeleted(){
        return this.is_deleted;
    }
    public boolean getAgreesTerm(){
        return this.agreesTerm;
    }
    public void setAgreesTerm(boolean agreesTerm){
        this.agreesTerm = agreesTerm;
    }
    public void setUserImage(UserImage userImage){
        this.userImage = userImage;
    } 
    public UserImage getUserImage(){
        return this.userImage;
    }
}