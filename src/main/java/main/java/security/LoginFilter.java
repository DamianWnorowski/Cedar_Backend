package main.java.security;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.java.models.User;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Inside loginFilter!");
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        if(user == null){
            System.out.println("USER IS NULL IN SESSION FUCK");
        }
        System.out.println(user.getEmail() + " is found to be logged in");
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals("/login");
        if (loggedIn || loginRequest){
            filterChain.doFilter(request, response);
        } else {
            System.out.println("Not logged in, redirecting..."); 
        }

    }

    @Override
    public void destroy() {

    }
}
