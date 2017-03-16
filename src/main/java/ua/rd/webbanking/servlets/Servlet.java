package ua.rd.webbanking.servlets;

import ua.rd.webbanking.controller.Dispatcher;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private Dispatcher dispatcher = Dispatcher.getInstance();
    private static final Logger logger = Logger.getLogger(Servlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        parseRequest(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        parseRequest(request,response);
    }

    protected void parseRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String pathToWebPage = dispatcher.logicIdentificator(request);

        RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(pathToWebPage);
        requestDispatcher.forward(request,response);
    }

}
