package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public String registerButtonClick(UserDto userDto) {
        // TODO: валидация userDto
        try {
            userService.save(userDto);
        } catch (RuntimeException e) {
            // пока так?
            return "";
        }
        return "login";
    }
}
