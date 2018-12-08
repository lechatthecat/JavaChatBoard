package com.blogspot.noteoneverything.chatboard.validator;

import java.util.List;
import java.util.Date;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.blogspot.noteoneverything.chatboard.validator.MyValidateUtil;

@Component
public class BoardValidator implements Validator {
    @Autowired
    private BoardService boardService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Board.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Board board = (Board) o;

        MyValidateUtil.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        MyValidateUtil.rejectIfEmptyOrWhitespace(errors, "detail", "NotEmpty");
        if (board.getName().length() < 3 || board.getName().length() > 32) {
            errors.rejectValue("name", "Size.boardForm.name");
        }
        if (board.getDetail().length() < 3 || board.getDetail().length() > 2000) {
            errors.rejectValue("detail", "Size.boardForm.detail");
        }
        if (!board.getAgreesTerm()) {
            errors.rejectValue("agreesTerm", "NotEmpty.agreesTerm");
        }
    }
}