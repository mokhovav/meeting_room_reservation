package com.mokhovav.meeting_room_reservation.security;

import com.mokhovav.meeting_room_reservation.entities.user.User;
import com.mokhovav.meeting_room_reservation.entities.user.UserService;
import com.mokhovav.meeting_room_reservation.entities.authority.AuthorityService;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class LoginController {
    final UserService userService;
    final AuthorityService authorityService;

    public LoginController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("{state}")
    public String login(
            @AuthenticationPrincipal User user,
            @PathVariable String state,
            Model model
    ) {
        if (state.equals("login")) {
            model.addAttribute("state", state);
            return "login";
        }
        if (state.equals("changePassword")) {
            model.addAttribute("state", "changePassword");
            return "login";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    @PostMapping("/changePassword")
    public String login(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "") String password ,
            @RequestParam(defaultValue = "") String confirm,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        try{
            userService.passwordsCheck(password, confirm);
            user.setPassword(passwordEncoder.encode(password));
            user.setChangePassword(false);
            userService.update(user);
            logoutPage(request, response);
        }catch (CustomException e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("state", "changePassword");
            return "login";
        }
        return "redirect:/";
    }

    private boolean logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return true;
        }
        else return false;
    }
}
