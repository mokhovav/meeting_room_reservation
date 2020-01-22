package com.mokhovav.meeting_room_reservation.entities.user;

import com.mokhovav.meeting_room_reservation.entities.authority.AuthorityData;
import com.mokhovav.meeting_room_reservation.entities.authority.AuthorityService;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/controlPanel")
@PreAuthorize("hasAuthority('admin')")
public class UserController {
    private final UserService userService;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String userList(Model model){

        AuthorityData authorityData = new AuthorityData();
        User userData = new User();

        model.addAttribute("authorityData", authorityData);
        model.addAttribute("userData", userData);
        model.addAttribute("users", userService.getAll());
        return "controlPanel";
    }

    @GetMapping("{user_id}")
    public String userEditForm(@PathVariable Long user_id, Model model) {
        //AuthorityData authorityData = new AuthorityData();
        model.addAttribute("userData", userService.getById(user_id));
        model.addAttribute("authorities", authorityService.getAll());
        return "userEdit";
    }

    @PostMapping("addUser")
    public String addUser(User user, Model model){
        User answer = new User();
        AuthorityData authorityData = new AuthorityData();

        try {
            userService.userDataCheck(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
        } catch (CustomException e) {
            answer.setError(e.getMessage());
        }

        answer.setUsername(user.getUsername());
        model.addAttribute("userData", answer);
        model.addAttribute("authorityData", authorityData);
        model.addAttribute("users", userService.getAll());
        return "controlPanel";
    }

    @PostMapping ("editUser")
    public String userUpdate(UserData userData, @RequestParam Map<String, String> list, Model model) throws CustomException {
        String temp = null;
        try {
            userService.userUpdate(userData, list);
        } catch (CustomException e) {
            temp = e.getMessage();
        }
        UserData answer = userService.getById(userData.getId());
        answer.setError(temp);
        model.addAttribute("userData", answer);//userService.getById(answer.getId()));
        model.addAttribute("authorities", authorityService.getAll());
        return "userEdit";
    }

}
