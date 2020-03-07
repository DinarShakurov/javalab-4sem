package ru.shakurov.servlet_spring.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import ru.shakurov.servlet_spring.models.dto.SignUpDto;
import ru.shakurov.servlet_spring.services.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private SignUpService signUpService;
    private Configuration cfg;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) context.getAttribute("springContext");
        signUpService = applicationContext.getBean(SignUpService.class);
        cfg = applicationContext.getBean(Configuration.class);
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Template template = cfg.getTemplate("sign_up.ftl");
        Writer out = resp.getWriter();
        template.process(null, out);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        SignUpDto dto = SignUpDto.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
        signUpService.signUp(dto);

        resp.sendRedirect("/signUp");
    }
}
