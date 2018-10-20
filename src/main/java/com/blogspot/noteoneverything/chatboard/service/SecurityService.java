package com.blogspot.noteoneverything.chatboard.service;

public interface SecurityService{
    public String findLoggedInUsername();
    public void login(String name, String password);
}