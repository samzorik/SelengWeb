package test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet("/hello_html")
public class HelloWorldHTML extends HttpServlet {
@Override
public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html"); //«адаем заголовок ответа
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" + // формируем HTML страницы
                "<html>\n" +
                "<head><title>A Test Servlet waka waka</title></head>\n" +
                "<body bgcolor=\"#fdf5e6\">\n" +
                "<h1>Tes waka wakat</h1>\n" +
                "<p>Simple servlet for testing.</p>\n" +
                "</body></html>");
    }
}