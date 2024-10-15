package com.homiliai.core.controller;

import com.homiliai.core.entity.Parish;
import com.homiliai.core.entity.User;
import com.homiliai.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
  private final UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("parish", new Parish());
    return "register";
  }

  @PostMapping("/register")
  public String registerUser(@ModelAttribute User user, @ModelAttribute Parish parish) {
    if (parish.getName() == null || parish.getName().isEmpty()) {
      parish = null; // Opcjonalne, jeśli użytkownik nie poda szczegółów parafii
    }
    userService.registerUser(user, parish);
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }

}
