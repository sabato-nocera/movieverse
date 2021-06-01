package control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Permette di visualizzare le informazioni relative ad un film.
 * Inoltre, gestisce la possibilità da parte degli utenti di aggiungere il film alla lista di film guardati,
 * alla lista dei film che si vuole guardare, di aggiungere recensioni, di modificare recensioni e di eliminare il film
 * visualizzato.
 */
@WebServlet("/Film")
public class FilmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = response.encodeURL("WEB-INF/Login.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
