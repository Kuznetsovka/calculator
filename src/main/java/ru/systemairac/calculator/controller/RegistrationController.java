package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.service.allinterface.UserService;

import javax.servlet.http.HttpServletRequest;

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

    //Решить проблему с переадресацией после LogIn

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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, Model model) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        // some other stuff
        return "login";
    }

    @RequestMapping("/login")
    @PreAuthorize("permitAll()")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
