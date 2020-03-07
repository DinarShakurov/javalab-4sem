package ru.shakurov.servlet_spring.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import ru.shakurov.servlet_spring.services.ConfirmService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/confirm/*")
public class ConfirmServlet extends HttpServlet {
    private ConfirmService confirmService;
    private Configuration cfg;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] split = req.getRequestURL().toString().split("/");
        Template template = cfg.getTemplate("confirmation.ftl");
        Map<String, Object> root = new HashMap<>();

        if (confirmService.confirm(split[split.length - 1])) {
            root.put("status", "Success");
        } else {
            root.put("status", "Fail");
        }
        try {
            template.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) context.getAttribute("springContext");
        confirmService = applicationContext.getBean(ConfirmService.class);
        cfg = applicationContext.getBean(Configuration.class);
    }
}
