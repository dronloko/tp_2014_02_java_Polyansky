package server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.DateFormat;


/**
 * Created by dronloko 15.02.14.
 */

public class Frontend extends HttpServlet{
    AccountService accountService = new AccountService();
    private final static DateFormat FORMATTER = new SimpleDateFormat("HH.mm.ss");

    public static String getTime() {
        return FORMATTER.format(new Date());
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String currentPage = "index.html";
        String path = request.getPathInfo();
        Map<String, Object> pageVariables = new HashMap<>();
        if(path.equals("/index")) {
            currentPage = "index.html";
//            HttpSession session = request.getSession();
//            Long userId = (Long) session.getAttribute("userId");
//            if (userId == null) {
//                response.sendRedirect("/auth");
//            } else {
//                response.sendRedirect("/user");
//            }
        }
        if(path.equals("/reg") || path.equals("/reg.html")) {
            currentPage = "reg.html";
        }
        if(path.equals("/auth") || path.equals("/auth.html")) {
            currentPage = "auth.html";
        }
        if(path.equals("/user") || path.equals("/user.html")) {
            currentPage = "user.html";
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                pageVariables.put("errorMessage", "You haven't userId at the moment! Please, log in or sign up!");
            } else {
                pageVariables.put("userId", userId);
            }
            pageVariables.put("serverTime", getTime());
            pageVariables.put("refreshPeriod", "1000");
        }
        if(path.equals("/allusers") || path.equals("/allusers.html")) {
            currentPage = "allusers.html";
            pageVariables.put("users", accountService.getAllUsers());
        }
        response.getWriter().println(PageGenerator.getPage(currentPage, pageVariables));

    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUsername = request.getParameter("username");
        String requestPassword = request.getParameter("password");

        Map <String, Object> pageVariables = new HashMap<>();
        String path = request.getPathInfo();

        if(path.equals("/auth.html") || path.equals("/auth")){
            if (accountService.authorize(requestUsername, requestPassword)) {
                response.sendRedirect("/user");
                pageVariables.put("serverTime", getTime());
                pageVariables.put("refreshPeriod", "1000");
            } else {
                pageVariables.put("errorMessage", "Login or password was incorrect!");
                response.getWriter().println(PageGenerator.getPage("auth.html", pageVariables));
            }
        }
        if(path.equals("/reg.html") || path.equals("/reg")){
            if(requestUsername.isEmpty() || requestPassword.isEmpty()){
                pageVariables.put("registrationMessage", "Login or password was empty! Error!");
            } else {
                if(accountService.register(requestUsername, requestPassword)){
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", accountService.getUserId(requestUsername));
                    pageVariables.put("registrationMessage", "User " + requestUsername + " was registered!");
                } else {
                    pageVariables.put("registrationMessage", "User " + requestUsername + " is already registered! Try another login!");
                }
            }
            response.getWriter().println(PageGenerator.getPage("reg.html", pageVariables));
        }
    }
}