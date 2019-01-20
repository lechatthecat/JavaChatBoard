package com.blogspot.noteoneverything.chatboard.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.simp.user.SimpUserRegistry;

import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.dao.UserImageRepository;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.validator.BoardValidator;
import com.blogspot.noteoneverything.chatboard.util.Constants;
import com.blogspot.noteoneverything.chatboard.util.Utility;

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
    @Autowired
    private HttpSession session;  
    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @GetMapping(value = "/")
    public String index(Model model, @PageableDefault(value=10, page=0) Pageable pageable) {
        User user = loadUserInfoOfSession(model);
        this.loadRelatedBoards(model, user);
        Page<Board> pages = boardService.getPublicBoardPages(pageable);
        this.loadPageRelatedInfo(model, pages);
        model.addAttribute("lastCheckedTime", session.getAttribute("lastCheckedTime"));
        HashMap<String, HashMap<String,String>> latestResponseTimePerBoard = boardService.getLatestResponseTimePerBoard(user.getId());
        model.addAttribute("latestResponseTimePerBoard", latestResponseTimePerBoard);
        return "boards/index";
    }

    @GetMapping(value = "/index")
    public String index2(Model model, @PageableDefault(value=10, page=0) Pageable pageable) {
        return this.index(model, pageable);
    }

    @GetMapping(value = "/board")
    public String boards(@RequestParam("b_id") String b_id, Model model) {
        this.setLastCheckedTime(b_id);
        User user = loadUserInfoOfSession(model);
        this.loadRelatedBoards(model, user);
        Board board = boardService.findBoardById(Long.parseLong(b_id));
        model.addAttribute("board", board);
        List<BoardResponse> boardReponses = board.getBoardResponses();
        model.addAttribute("boardReponses", boardReponses);
        model.addAttribute("lastCheckedTime", session.getAttribute("lastCheckedTime"));
        // TO DO: get user objects, not only user name strings.
        List<String> usersInThisBoard = this.getUsersOfTopic(b_id);
        if(usersInThisBoard != null && !usersInThisBoard.contains(user.getName())){
            usersInThisBoard.add(user.getName());
        }
        model.addAttribute("usersInThisBoard", usersInThisBoard);
        return "boards/board";
    }

    @GetMapping(value = "/create_board")
    public String createBoard(Model model) {
        User user = loadUserInfoOfSession(model);
        this.loadRelatedBoards(model, user);
        model.addAttribute("board", new Board());
        return "boards/create";
    }

    @PostMapping(value = "/create_board")
    public String creatBoard(
            @Valid @ModelAttribute("board") Board board,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult,
            Model model) {
        //Login user in session
        User user = loadUserInfoOfSession(model);
        this.loadRelatedBoards(model, user);
        board.setUser(user);
        model.addAttribute("user", user);
        //Validator
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            redirectAttributes.addFlashAttribute("message", "Sorry board could not be created. Please check the input values.");
            redirectAttributes.addFlashAttribute("alertClass", "text-danger");
            return "boards/create";
        }
        //Board creation
        long b_id = boardService.createBoard(board);
        if(b_id > 0){  
            redirectAttributes.addFlashAttribute("message", "Success. Board created.");
            redirectAttributes.addFlashAttribute("alertClass", "info");
            this.setLastCheckedTime(String.valueOf(b_id));
        }else{
            redirectAttributes.addFlashAttribute("message", "Sorry board could not be created. Please contact the administarator.");
            redirectAttributes.addFlashAttribute("alertClass", "danger");
        }
        return "redirect:/";
    }

    private User loadUserInfoOfSession(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        model.addAttribute("user", user);
        return user;
    }

    private void loadPageRelatedInfo(Model model, Page<Board> pages){
        model.addAttribute("number", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalElements", pages.getTotalElements());
        model.addAttribute("size", pages.getSize());
        model.addAttribute("pageSpan", Constants.PAGE_SPAN);
        model.addAttribute("publicBoards", pages.getContent());
    }

    private void loadRelatedBoards(Model model, User user) {
        List<Board> respondedBoards = boardService.findBoardsByUserOfBoardResponses(user.getId(), 5);
        model.addAttribute("respondedBoards", respondedBoards);
        List<Board> createdBoards = boardService.findBoardsByUser(user, PageRequest.of(0,5));
        model.addAttribute("createdBoards", createdBoards);
        Long numOfCreatedBoard = boardService.getCountOfBoardsByUser(user);
        model.addAttribute("numOfCreatedBoard", numOfCreatedBoard);
        Long numOfRespondedBoard = boardService.getCountBoardsByUserOfBoardResponses(user.getId());
        model.addAttribute("numOfRespondedBoard", numOfRespondedBoard);
        Long unSeenResponses = boardService.getHowManyUnseenResponses(user);
        model.addAttribute("unSeenResponses", unSeenResponses);
    }

    private void setLastCheckedTime(String b_id){
        HashMap<String,String> lastCheckedTime = new HashMap<String,String>();
        Date now = new Date();
        String nowStr = Utility.formatDate(now);
        lastCheckedTime.put(b_id, nowStr);
        session.setAttribute("lastCheckedTime", lastCheckedTime);
    }

    private List<String> getUsersOfTopic(){
        if(simpUserRegistry != null){
            if(simpUserRegistry.getUsers() != null){
                List<String> usersOfTopic = simpUserRegistry.getUsers().parallelStream()
                .map(u -> {
                    return u.getName();
                }).collect(Collectors.toList());
                return usersOfTopic;
            }
        }
        return null;
    }

    private List<String> getUsersOfTopic(String boardId){
        if(simpUserRegistry != null){
            if(simpUserRegistry.getUsers() != null){
                List<String> usersOfTopic = simpUserRegistry.getUsers().parallelStream()
                .filter(u -> u.getSessions().iterator().next().getSubscriptions().iterator().next().getDestination().equals("/board/public/"+boardId))
                .map(u -> {
                    return u.getName();
                }).collect(Collectors.toList());
                return usersOfTopic;
            }
        }
        return null;
    }
}