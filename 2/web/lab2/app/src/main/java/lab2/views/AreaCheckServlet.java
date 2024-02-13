package lab2.views;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lab2.beans.RecordBean;
import lab2.beans.RequestsHistoryBean;

@WebServlet("/results")
public class AreaCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        RequestsHistoryBean historyBean = (RequestsHistoryBean)session.getAttribute("history");

        Double x = Double.parseDouble(request.getParameter("xcoord"));
        Double y = Double.parseDouble(request.getParameter("ycoord"));
        Double r = Double.parseDouble(request.getParameter("rval"));

        Boolean result;
        if (((x >= 0 && y >= 0) && (y <= -2*x + r)) || ((x < 0 && y >= 0) && (x >= -r/2 && y <= r)) || ((x >= 0 && y < 0) && (Math.sqrt(y*y + x*x) <= r))) {
            result = true;
        } else {
            result = false;
        }

        historyBean.addRecord(new RecordBean(x, y, r, new Date(), result));
        request.getSession().getAttribute("history");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        Double x = Double.parseDouble(request.getParameter("xcoord"));
        Double y = Double.parseDouble(request.getParameter("ycoord"));
        Double r = Double.parseDouble(request.getParameter("rval"));

        Boolean result;
        if (((x >= 0 && y >= 0) && (y <= -2*x + r)) || ((x < 0 && y >= 0) && (x >= -r/2 && y <= r)) || ((x >= 0 && y < 0) && (Math.sqrt(y*y + x*x) <= r))) {
            result = true;
        } else {
            result = false;
        }
        
        ((RequestsHistoryBean)session.getAttribute("history")).getRecords().add(new RecordBean(x, y, r, new Date(), result));
        response.setStatus(200);
        return;

    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestsHistoryBean history = (RequestsHistoryBean)request.getSession().getAttribute("history");
        history.getRecords().clear();
        response.setStatus(200);
        return;
    }
}
