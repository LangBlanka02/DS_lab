package com.utcn.demo.controller;

import com.google.common.base.CaseFormat;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.Formatter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.utcn.demo.controller.exception.AccountNotFoundException;
import com.utcn.demo.model.*;
import com.utcn.demo.service.*;
import com.utcn.demo.service.impl.question.QuestionSortType;

import jakarta.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.utcn.demo.controller.ControllerConstants.QUESTIONS_PATH;

@Controller
@RequestMapping(QUESTIONS_PATH)
@AllArgsConstructor
public class QuestionController {

    private static final String TEMPLATE_DIR = "question";
    private static final String NEW_TEMPLATE = TEMPLATE_DIR + "/new";
    private static final String LIST_TEMPLATE = TEMPLATE_DIR + "/list";
    private static final String EDIT_TEMPLATE = TEMPLATE_DIR + "/edit";
    private static final String VIEW_TEMPLATE = TEMPLATE_DIR + "/view";

    private final QuestionService questionService;
    private final AccountService accountService;
    private final List<QuestionSortService> questionSortServices;



    @ModelAttribute("module")
    public String module() {
        return "questions";
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "filters", required = false) String filters,
                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {

        if (!Objects.isNull(filters)) {
            List<String> filtersList = List.of(filters.split(","));
        }

        Optional<QuestionSortType> sortType = pageable
            .getSort()
            .get()
            .map(Sort.Order::getProperty)
            .map(type -> CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, type))
            .map(QuestionSortType::valueOf)
            .findFirst();

        sortType.flatMap(questionSortType -> questionSortServices
                .stream()
                .filter(service -> service.isSuitableFor(questionSortType))
                .findFirst()).ifPresent(service -> model.addAttribute("questions", service.sort(pageable)));

        return LIST_TEMPLATE;
    }

    @GetMapping("/new")
    public String askQuestion(Model model) {
        model.addAttribute("question", new Question());

        return NEW_TEMPLATE;
    }

    @PostMapping
    public String saveQuestion(@Valid @ModelAttribute Question question, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService
                .findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with this email not found: " + userEmail));

        question.setAuthor(author);
        questionService.save(question);

        return "redirect:questions";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, Principal principal) {
        Question question = questionService
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        if (question.getBody() == null) { // Add a check for null content
            throw new IllegalStateException("Question body is null for question ID: " + id);
        }
        Answer answer = new Answer();
        answer.setQuestion(question);
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);

        return VIEW_TEMPLATE;
    }

    @PatchMapping(value = "/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
//    public Map voteUp(@PathVariable Long id, Principal principal) {
//        String userEmail = principal.getName();
//        Account author = accountService.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException(userEmail));
//        Question question = questionService.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
//        question.removeNegativeVote(author);
//        question.addPositiveVote(author);
//        questionService.save(question);
//        Integer rating = question.getRating();
//        return Collections.singletonMap("rating", rating);
//    }
    public Map<String, Integer> voteUp(@PathVariable Long id, Principal principal) {
        String userEmail = principal.getName();
        Account voter = accountService.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException(userEmail));
        Question question = questionService.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        // Updating question score
        double scoreIncrement = 2.5;
        accountService.updateScore(question.getAuthor().getId(), scoreIncrement);

        // Adding positive vote logic here
        question.addPositiveVote(voter);
        questionService.save(question);

        return Collections.singletonMap("rating", question.getRating());
    }

    @PatchMapping(value = "/{id}/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Integer> voteDown(@PathVariable Long id, Principal principal) {
        String userEmail = principal.getName();
        Account voter = accountService.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException(userEmail));
        Question question = questionService.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        // Updating question score
        double scoreDecrement = -1.5;
        accountService.updateScore(question.getAuthor().getId(), scoreDecrement);



        // Adding negative vote logic here
        question.addNegativeVote(voter);
        questionService.save(question);

        return Collections.singletonMap("rating", question.getRating());
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        model.addAttribute("question", question);

        return EDIT_TEMPLATE;
    }

    @PutMapping
    public String editQuestion(@Valid @ModelAttribute Question question) {
        Long updatedQuestionId = questionService
                .save(question)
                .getId();

        return String.format("redirect:%s/%d", QUESTIONS_PATH, updatedQuestionId);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }

}
