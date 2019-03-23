package me.hdpe.modulesblog.app;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AppController {

    @GetMapping("/ping")
    @ResponseBody
    public String ping() {
        return "Hello";
    }
}
