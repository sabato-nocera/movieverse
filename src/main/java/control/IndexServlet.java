package control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Richiamata quando viene avviata la web application.
 * Mostra all'utente la pagina di login.
 */
@WebServlet("/Index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(IndexServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("utente") != null) {
            logger.log(Level.WARNING, "Utente loggato");
            String action = request.getParameter("action");
            logger.log(Level.WARNING, "Action: "+action);
            if(action!=null){
                request.getSession().invalidate();
                String url = response.encodeURL("WEB-INF/Login.jsp");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            } else {
                String url = response.encodeURL("Catalogo");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
        }

        String url = response.encodeURL("WEB-INF/Login.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
