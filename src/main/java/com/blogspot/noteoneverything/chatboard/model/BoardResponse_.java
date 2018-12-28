package com.blogspot.noteoneverything.chatboard.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

@StaticMetamodel(BoardResponse.class)
public class BoardResponse_ {

    public enum MessageType {
        CHAT, 
        JOIN, 
        LEAVE
    }

    public static volatile SingularAttribute<BoardResponse, MessageType>  type;
    public static volatile SingularAttribute<BoardResponse, Long> id;
    public static volatile SingularAttribute<BoardResponse, String> response;
    public static volatile SingularAttribute<BoardResponse, User> user;
    public static volatile SingularAttribute<BoardResponse, Board> board;
    public static volatile SingularAttribute<BoardResponse, Boolean> is_deleted;
    public static volatile SingularAttribute<BoardResponse, Date> updated;
    public static volatile SingularAttribute<BoardResponse, Date> created;
}