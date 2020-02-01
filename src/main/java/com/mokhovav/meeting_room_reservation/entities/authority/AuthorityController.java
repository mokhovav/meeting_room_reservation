package com.mokhovav.meeting_room_reservation.entities.authority;

import com.mokhovav.meeting_room_reservation.entities.user.User;
import com.mokhovav.meeting_room_reservation.entities.user.UserService;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/controlPanel")
@PreAuthorize("hasAuthority('admin')")
public class AuthorityController {
    private final AuthorityService authorityService;
    private final UserService userService;

    public AuthorityController(AuthorityService authorityService, UserService userService) {
        this.authorityService = authorityService;
        this.userService = userService;
    }

    @PostMapping("addAuthority")
    public String add(AuthorityData authorityData, Model model){
        Authority authority = new Authority(authorityData.getAuthority());
        AuthorityData answer = new AuthorityData(authorityData);
        User userData = new User();
        try {
            authorityService.save(authority);
            throw new CustomException("Authority successfully added");
        }
        catch (CustomException e) {
            answer.setError(e.getMessage());
        }
        model.addAttribute("authorityData", answer);
        model.addAttribute("userData", userData);
        model.addAttribute("users", userService.getAll());
        return "controlPanel";
    }
}
