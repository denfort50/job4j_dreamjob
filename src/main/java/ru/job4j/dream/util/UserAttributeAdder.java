package ru.job4j.dream.util;

import org.springframework.ui.Model;
import ru.job4j.dream.model.User;
import javax.servlet.http.HttpSession;

public final class UserAttributeAdder {

    private UserAttributeAdder() {
        throw new java.lang.UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void addAttributeUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
    }
}
