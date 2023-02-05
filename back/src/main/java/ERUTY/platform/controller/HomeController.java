package ERUTY.platform.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("home controller");

        return "home";
    }
    @GetMapping("/gallery")
    public String gallery() {
        log.info("gallery controller");

        return "gallery";
    }
    @GetMapping("/designpage")
    public String designpage() {
        log.info("designpage controller");

        return "designpage";
    }
    
}
