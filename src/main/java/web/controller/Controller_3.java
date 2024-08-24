package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controller_3 {
    @GetMapping("/page_3")
    public String page_3 (){
        return "page_3";

    }
}
