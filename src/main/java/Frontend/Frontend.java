package Frontend;

import Generator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Frontend extends HttpServlet{
    private String login = "user";
    private String password = "qwerty";
    private AtomicLong userIdGenerator = new AtomicLong();

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH.mm.ss");
        return dateFormat.format(date);
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String currentPage = "index.html";
        String path = request.getPathInfo();
        Map<String, Object> pageVariables = new HashMap<>();

        if(path.equals("/index")) {
            currentPage = "index.html";
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                response.sendRedirect("/auth");
            } else {
                response.sendRedirect("/user");
            }
        }
        if(path.equals("/auth")) {
            currentPage = "auth.html";
        }
        if(path.equals("/user")) {
            currentPage = "user.html";
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                response.sendRedirect("/auth");
            } else {
                pageVariables.put("userId", userId);
                pageVariables.put("serverTime", getTime());
                pageVariables.put("refreshPeriod", "1000");
            }
        }
        response.getWriter().println(PageGenerator.getPage(currentPage, pageVariables));

    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUsername = request.getParameter("username");
        String requestPassword = request.getParameter("password");

        Map <String, Object> pageVariables = new HashMap<>();
        if (requestUsername.equals(login) && requestPassword.equals(password)) {
            Long userId = userIdGenerator.getAndIncrement();
            request.getSession().setAttribute("userId", userId);
            response.sendRedirect("/user");
            pageVariables.put("serverTime", getTime());
            pageVariables.put("refreshPeriod", "1000");
        } else {
            pageVariables.put("errorMessage", "Login or password was incorrect!");
            response.getWriter().println(PageGenerator.getPage("auth.html", pageVariables));
        }
    }
}