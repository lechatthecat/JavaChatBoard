package com.blogspot.noteoneverything.chatboard.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.context.SecurityContextHolder;
import java.security.Principal;
import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.dao.UserImageRepository;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.UserImage;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
public class ChatBoardController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserImageRepository userImageRepository;
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        model.addAttribute("user", user);
        // created boards by this user.
        List<Board> usersBoards = boardService.findBoardsByUser(user, PageRequest.of(0, 5));
        // boards in which this user mentioned.
        List<Long> board_ids = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L));
        List<Board> responedBoards = boardService.findBoardsByUserOfBoardResponses(user.getId(), 5);
        // remove duplicates from the board list.
        Set<Board> hs = new HashSet<>();
        hs.addAll(usersBoards);
        hs.addAll(responedBoards);
        // finally combine two lists of them.
        List<Board> boards = new ArrayList();
        boards.clear();
        boards.addAll(hs);
        model.addAttribute("boards", responedBoards);
        return "boards/index";
    }

    @GetMapping(value = "/board")
    public String boards(@RequestParam("b_id") String b_id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        model.addAttribute("user", user);
        Board board = boardService.findBoardById(Long.parseLong(b_id));
        model.addAttribute("board", board);
        List<BoardResponse> boardReponses = board.getBoardResponses();
        model.addAttribute("boardReponses", boardReponses);
        return "boards/board";
    }

    @GetMapping(value = "/create_board")
    public String createBoard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("board", new Board());
        return "boards/create";
    }

    @PostMapping(value = "/delete")
    public String deleteCat(@RequestParam("name") String name, @RequestParam("id") Long id) {
        // catservice.deleteCat(name, id);
        // System.out.println("Cat named = " + name + "was removed from our database.
        // Hopefully he or she was adopted.");
        return "redirect:/";
    }
}