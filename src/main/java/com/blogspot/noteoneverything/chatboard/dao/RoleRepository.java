package com.blogspot.noteoneverything.chatboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogspot.noteoneverything.chatboard.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
}