package com.blogspot.noteoneverything.chatboard.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.security.Principal;
import javax.validation.Valid;

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
import org.springframework.transaction.annotation.Transactional;

import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.dao.UserImageRepository;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.UserImage;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.validator.BoardValidator;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        model.addAttribute("user", user);
        List<Board> responedBoards = boardService.getUserReposponedBoards(user);
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

    @PostMapping(value = "/create_board")
    public String creatBoard(
            @Valid @ModelAttribute("board") Board board,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        board.setUser(user);
        model.addAttribute("user", user);

        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            redirectAttributes.addFlashAttribute("message", "Sorry board could not be created. Please check the input values.");
            redirectAttributes.addFlashAttribute("alertClass", "text-danger");
            return "boards/create";
        }
        
        if(boardService.createBoard(board)){  
            redirectAttributes.addFlashAttribute("message", "Success. Board created.");
            redirectAttributes.addFlashAttribute("alertClass", "text-success");
        }else{
            redirectAttributes.addFlashAttribute("message", "Sorry board could not be created. Please contact the administarator.");
            redirectAttributes.addFlashAttribute("alertClass", "text-danger");
        }
        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    public String deleteCat(@RequestParam("name") String name, @RequestParam("id") Long id) {
        // catservice.deleteCat(name, id);
        // System.out.println("Cat named = " + name + "was removed from our database.
        // Hopefully he or she was adopted.");
        return "redirect:/";
    }
}