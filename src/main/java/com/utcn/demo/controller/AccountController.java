package com.utcn.demo.controller;

import com.google.common.base.CaseFormat;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.utcn.demo.controller.exception.AccountNotFoundException;
import com.utcn.demo.model.Account;
import com.utcn.demo.service.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.utcn.demo.controller.ControllerConstants.ACCOUNTS_PATH;

@Controller
@RequestMapping(ACCOUNTS_PATH)
@AllArgsConstructor
public class AccountController {

    private static final String TEMPLATE_DIR = "account";
    private static final String PROFILE_TEMPLATE = TEMPLATE_DIR + "/profile";
    private static final String LIST_TEMPLATE = TEMPLATE_DIR + "/list";
    private static final String EDIT_TEMPLATE = TEMPLATE_DIR + "/edit";
    private static final String ACTIVITY_TEMPLATE = TEMPLATE_DIR + "/activity";

    private final AccountService accountService;
    private final ImageService imageService;

    @ModelAttribute("module")
    public String module() {
        return "accounts";
    }

    @GetMapping
    public String findAll(Model model, @PageableDefault(size = 20) Pageable pageable) {
        model.addAttribute("accounts", accountService.findAll(pageable));
        return TEMPLATE_DIR + "/list";
    }

    @GetMapping("/{id}")
    public String viewAccount(@PathVariable Long id, Model model) {
        Account account = accountService.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        model.addAttribute("account", account);
        model.addAttribute("profile", "view");

        return PROFILE_TEMPLATE;
    }

    @GetMapping("/activity/{id}")
    public String profileActivity(@PathVariable Long id, Model model) {
        Account account = accountService.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        model.addAttribute("profile", "activity");
        model.addAttribute("account", account);
        return ACTIVITY_TEMPLATE;
    }

    @GetMapping("/ban/{id}")
    public String banAccount(@PathVariable Long id) {
        accountService.banUser(id);
        return "redirect:/admin/accounts";  // Adjust the redirect URL as needed
    }

    @GetMapping("/unban/{id}")
    public String unbanAccount(@PathVariable Long id) {
        accountService.unbanUser(id);
        return "redirect:/admin/accounts";  // Adjust the redirect URL as needed
    }

    @PutMapping
    public String updateAccount(@ModelAttribute Account account, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            imageService.findById(account.getAvatar().getId())
                .ifPresent(image -> {
                    try {
                        image.setData(Base64.getEncoder().encodeToString(file.getBytes()));
                        imageService.save(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }

        accountService.save(account);
        return String.format("redirect:%s/%d", ACCOUNTS_PATH, account.getId());
    }

    @GetMapping("/edit/{id}")
//    @PreAuthorize("authentication.principal.id.equals(#id)")
    public String getEditAccountForm(@PathVariable Long id, Model model) {
        model.addAttribute("profile", "edit");

        Account account = accountService
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        model.addAttribute("account", account);

        return EDIT_TEMPLATE;
    }

    @PostMapping("/new")
    public String createAccount() {
        return "/account/list";
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
        return String.format("redirect:%s", ACCOUNTS_PATH);
    }

}
