package ua.rd.webbanking.servlets;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.rd.webbanking.controller.Dispatcher;
import org.apache.log4j.Logger;
import ua.rd.webbanking.controller.exceptions.AuthorizationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        parseRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        parseRequest(request, response);
    }

    protected void parseRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathToWebPage = dispatcher.logicIdentificator(request);

            RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(pathToWebPage);
            requestDispatcher.forward(request, response);
        } catch (AuthorizationException ae) {
            logger.error(ae);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (NumberFormatException ne) {
            logger.error(ne);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

}
