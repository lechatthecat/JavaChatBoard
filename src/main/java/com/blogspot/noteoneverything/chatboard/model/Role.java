package com.blogspot.noteoneverything.chatboard.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;

import com.blogspot.noteoneverything.chatboard.model.User;

@Entity
@Table(name = "roles")
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean is_admin;
    private int power_level;
    private String name;  
    @OneToMany(cascade = CascadeType.ALL)
    private List<User> users;

    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    public boolean getIsAdmin(){
        return this.is_admin;
    }
    public void setIsAdmin(boolean is_admin){
        this.is_admin = is_admin;
    }
    public int getPowerLevel(){
        return this.power_level;
    }
    public void setPowerLevel(int power_level){
        this.power_level = power_level;
    }
    public String getName(){
        return this.name;
    }
    public String getRoleName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}