package com.homiliai.core.controller;

import com.homiliai.core.dto.CreateSermonDTO;
import com.homiliai.core.entity.User;
import com.homiliai.core.service.SermonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SermonController {

  private final SermonService sermonService;

  @Autowired
  public SermonController(SermonService sermonService) {
    this.sermonService = sermonService;
  }

  @GetMapping("/create-sermon")
  public String showSermonForm(Model model) {
    model.addAttribute("createSermonDTO", new CreateSermonDTO());
    return "create-sermon";
  }

  @PostMapping("/create-sermon")
  public String submitSermonForm(CreateSermonDTO createSermonDTO,
      @AuthenticationPrincipal User user) {
    sermonService.createSermon(createSermonDTO, user);
    return "redirect:/sermons";
  }
}
