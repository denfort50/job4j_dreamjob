package ru.job4j.dream.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class AuthFilter implements Filter {

    private final Set<String> mappings = new HashSet<>();

    public AuthFilter() {
        mappings.add("loginPage");
        mappings.add("login");
        mappings.add("formAddUser");
        mappings.add("fail");
        mappings.add("success");
        mappings.add("registration");
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (mappingIsPresent(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    private boolean mappingIsPresent(String uri) {
        boolean result = false;
        for (String mapping : mappings) {
            if (uri.endsWith(mapping)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
