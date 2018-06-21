package com.epam.horseraces.servlet;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.factory.ActionFactory;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.command.impl.manager.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code Controller} class handles the input information
 * ant thereafter sends the answer in the web page form
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class Controller extends HttpServlet {

    private static final String LIST = "list";
    private static final String PATH_PAGE_INDEX = "path.page.index";
    private static final String NULL_PAGE = "nullPage";
    private static final String MESSAGE_NULLPAGE = "message.nullpage";
    private static final String POST_METHOD = "POST";

    /**
     * @param req  The request parameter
     * @param resp The response parameter
     * @throws ServletException If the ServletException  was occurred while
     *                          processing the answer
     * @throws IOException      If the IOException  was occurred while
     *                          processing the answer
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * @param req  The request parameter
     * @param resp The response parameter
     * @throws ServletException If the ServletException  was occurred while
     *                          processing the answer
     * @throws IOException      If the IOException  was occurred while
     *                          processing the answer
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * @param request  The request parameter
     * @param response The request parameter
     * @throws ServletException If the ServletException  was occurred while
     *                          processing the answer
     * @throws IOException      If the IOException  was occurred while
     *                          processing the answer
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        page = command.execute(request);
        if (page != null) {
            //redirectToPage(request, response, page);
            String method = request.getMethod();
            if("POST".equalsIgnoreCase(method)) {
                response.sendRedirect(request.getContextPath() + page);
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
                System.out.print(request.getAttribute(LIST));
            }
        } else {
            page = ConfigurationManager.getProperty(PATH_PAGE_INDEX);
            request.getSession().setAttribute(NULL_PAGE, MessageManager.getProperty(MESSAGE_NULLPAGE));
            response.sendRedirect(request.getContextPath() + page);
        }
    }

    /**
     * @param request  The request parameter
     * @param response The response parameter
     * @param page     The web page that would be sent
     *                 to user
     * @throws IOException If the IOException  was occurred while
     *                     processing the answer
     */
    private void redirectToPage(HttpServletRequest request, HttpServletResponse response, String page) throws IOException {
        String method = request.getMethod();
        if (POST_METHOD.equalsIgnoreCase(method)) {
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
