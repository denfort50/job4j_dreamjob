package ru.job4j.dream.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import static ru.job4j.dream.util.UserAttributeAdder.addAttributeUser;

@ThreadSafe
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "index";
    }
}
