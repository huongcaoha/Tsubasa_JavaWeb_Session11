package com.ra.session11.controller;

import com.ra.session11.model.dto.UserLoginDTO;
import com.ra.session11.model.dto.UserRegisterDTO;
import com.ra.session11.model.entity.User;
import com.ra.session11.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userLogin" , new UserLoginDTO());
        return "auth/login";
    }

    @PostMapping("/login")
    public String handleLogin(HttpServletResponse response, @Valid @ModelAttribute UserLoginDTO userLogin, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userLogin" , userLogin);
            return "auth/login";
        }
        User user = userService.login(userLogin);
        if (user != null) {
            model.addAttribute("user" , user);
            return "redirect:/";
        }else {
            model.addAttribute("userLogin" , userLogin);
            model.addAttribute("message" , "Username or password is incorrect");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRegisterDTO userRegisterDTO, BindingResult bindingResult
            , Model model, RedirectAttributes redirectAttributes) {
       if (userRegisterDTO.getAvatar() == null || userRegisterDTO.getAvatar().isEmpty()) {
           bindingResult.rejectValue("avatar", "error.avatar.empty", "avatar is empty");
       }
        if (bindingResult.hasErrors()) {
           model.addAttribute("userRegisterDTO", userRegisterDTO);
           return "auth/register";
       }
        User user = userService.register(userRegisterDTO);
        if (user != null) {
            redirectAttributes.addFlashAttribute("message" , "Đăng ký thành công");
            return "redirect:/auth/login";
        }else {
            model.addAttribute("message","Đăng ký thất bại !");
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        userService.logout();
        return "redirect:/auth/login";
    }


}
