package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public String homePage() {
        return "welcomePage";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "errorPage";
    }
}
