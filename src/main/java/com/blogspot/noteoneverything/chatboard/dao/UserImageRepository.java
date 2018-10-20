package com.blogspot.noteoneverything.chatboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogspot.noteoneverything.chatboard.model.UserImage;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage,Long>{
}