package mate.controller;

import mate.exception.AuthenticationException;
import mate.lib.Injector;
import mate.service.AuthenticationService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate");
    private final AuthenticationService authenticationService = (AuthenticationService)
            injector.getInstance(AuthenticationService.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            authenticationService.login(login, password);
            resp.sendRedirect("/index");
        } catch (AuthenticationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
