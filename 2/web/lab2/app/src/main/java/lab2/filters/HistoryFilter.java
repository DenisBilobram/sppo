package lab2.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lab2.beans.RequestsHistoryBean;

public class HistoryFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(true);

        RequestsHistoryBean historyBean = (RequestsHistoryBean)session.getAttribute("history");

        if (historyBean == null) {
            session.setAttribute("history", new RequestsHistoryBean());
            historyBean = (RequestsHistoryBean)session.getAttribute("history");
        }

        chain.doFilter(httpRequest, httpResponse);

    }

}