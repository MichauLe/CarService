package com.javagda17.myproject.carservice.controller;

import com.javagda17.myproject.carservice.model.dto.ChecklistDto;
import com.javagda17.myproject.carservice.model.dto.ItemDto;
import com.javagda17.myproject.carservice.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ChecklistController {
    @Autowired
    private ChecklistService checklistService;

    @GetMapping("/checklist")
    public  String getWpisy(Model model){
        model.addAttribute("wpisyList",checklistService.getAllWpisy());
        return "checklist";
    }
    @PostMapping("/checklist")
    public String submitWpis(ChecklistDto checklist){
        checklistService.addWall(checklist);
        return "redirect:/checklist";
    }
    @GetMapping("/removeWpis")
    public String removeWpis(@RequestParam(name = "wpistId") Long wpisId) {
        checklistService.remove(wpisId);

        return "redirect:/checklist";
    }
    @RequestMapping(value = "/edit/{identifier}", method = RequestMethod.GET)
    public String getEditForm(Model model, @PathVariable(name = "identifier") Long id) {
        Optional<ChecklistDto> wallDtoOptional = checklistService.getById(id);
        if (wallDtoOptional.isPresent()) {
            model.addAttribute("tweetToEdit", wallDtoOptional.get());
            model.addAttribute("wpisyList",checklistService.getAllWpisy());

            return "item";
        }

        return "redirect:/checklist";
    }
    @RequestMapping(value = "/edit/{identifier}", method = POST)
    public String getEditForm(Model model, @PathVariable(name = "identifier") Long id,
                              ChecklistDto dto) {
        checklistService.update(dto);

        return "redirect:/checklist";
    }

    @RequestMapping(value = "/tweetTag/", method = POST)
    public String addTagToTweet(ItemDto dto) {
        checklistService.addTagToTweet(dto);

        return "redirect:/edit/" + dto.getWpisId();
    }
    @RequestMapping(value = "/listTweets/{tagName}")
    public String getTweetsWithTag(Model model, @PathVariable(name = "tagName") String tagName){
        model.addAttribute("tweetList", checklistService.findTweetsWithTag(tagName));

        return "list";
    }
}
