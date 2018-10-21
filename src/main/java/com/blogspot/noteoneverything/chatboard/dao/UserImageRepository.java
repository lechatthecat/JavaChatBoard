package com.blogspot.noteoneverything.chatboard.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogspot.noteoneverything.chatboard.model.UserImage;
import com.blogspot.noteoneverything.chatboard.model.User;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage,Long>{
    @Query("select ui from UserImage ui where ui.user= :user")
    UserImage findUserImageByUser(@Param("user") User user);
}