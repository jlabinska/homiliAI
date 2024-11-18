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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
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
  public String registerUser(@ModelAttribute User user, @ModelAttribute Parish parish,
      Model model) {
    try {
      // Register the user, handling optional Parish
      userService.registerUser(user,
          (parish.getName() == null || parish.getName().isEmpty()) ? null : parish);
      return "redirect:/auth/login"; // Redirect to login on successful registration

    } catch (IllegalArgumentException ex) {
      // Add error message to the model in case of an exception
      model.addAttribute("errorMessage", ex.getMessage());
      model.addAttribute("user", user); // Preserve entered data
      model.addAttribute("parish", parish); // Preserve entered parish data
      return "register"; // Return to the registration form with the error message
    }
  }

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("user", new User());
    return "login"; // simply returns the "login.html" view
  }


}
