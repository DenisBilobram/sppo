package lab2.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CoordinatesFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getMethod().equals("DELETE")) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        if (request.getParameter("xcoord") == null || request.getParameter("ycoord") == null || request.getParameter("rval") == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        try {
            Double x = Double.parseDouble(request.getParameter("xcoord"));
            Double y = Double.parseDouble(request.getParameter("ycoord"));
            Double r = Double.parseDouble(request.getParameter("rval"));

            if ((r != 1 && r != 1.5 && r != 2 && r != 2.5 && r != 3) || x >= 10 || x <= -10 || y >= 10 || y <= - 10) {
                throw new NumberFormatException();
            }
            
        } catch (NumberFormatException e) {
            httpRequest.getSession().setAttribute("validerror", true);
            httpResponse.sendRedirect("main");
            return;
        }

        chain.doFilter(httpRequest, httpResponse);

    }

}
