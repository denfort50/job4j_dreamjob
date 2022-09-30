package ru.job4j.dream.controller;

import org.springframework.ui.Model;
import ru.job4j.dream.model.User;
import javax.servlet.http.HttpSession;

public final class UserAttributeAdder {
    public static void addAttributeUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
    }
}
