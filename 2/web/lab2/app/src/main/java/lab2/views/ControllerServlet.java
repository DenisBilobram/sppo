package lab2.views;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab2.beans.RecordBean;
import lab2.beans.RequestsHistoryBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


@WebServlet("/main")
public class ControllerServlet extends HttpServlet {

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("xcoord") != null && request.getParameter("ycoord") != null && request.getParameter("rval") != null) {

            response.sendRedirect("results?xcoord=" + request.getParameter("xcoord") + "&ycoord=" + request.getParameter("ycoord") + "&rval=" + request.getParameter("rval"));
            return;
        }

        InputStream is = getServletContext().getResourceAsStream("html/index.html");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String html = br.lines().collect(Collectors.joining("\n"));

        Document index = Jsoup.parse(html);
        index.charset(StandardCharsets.UTF_8);
        
        if (request.getSession().getAttribute("validerror") != null) {
            Element errorsEl = index.body().getElementById("global-errors");
            if (errorsEl != null) {
                errorsEl.removeClass("hiddenerror");   
            }   
            request.getSession().removeAttribute("validerror");
        }

        List<RecordBean> history = ((RequestsHistoryBean)request.getSession().getAttribute("history")).getRecords();
        if (history.size() != 0) {
            RecordBean lastRecord = history.get(history.size()-1);
            Double rval = lastRecord.getR();
            index.getElementsByClass("r-buttons-td").get(0).children().forEach(el -> {
                if (el.attributes().get("value").equals(rval.toString().substring(0, 1))) {
                    el.addClass("selected");
                }
            });
        }
            

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(index.outerHtml());
    }

}
