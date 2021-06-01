package control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Permette di visualizzare cataloghi di film a seconda della tipologia, nello specifico permette di visualizzare
 * la lista di tutti i film "in sala", la lista dei film migliori, la lista dei film che prossimamente usciranno,
 * la lista di tutti i film presenti nel database, la lista dei film che si ha intenzione di guardare, la lista dei film
 * guardati e, infine, la lista dei film ricercati con la barra di ricerca.
 * Inoltre, è responsabile dell'ordinametno e del filtraggio dei film in base ad un particolare criterio.
 */
@WebServlet("/Catalogo")
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = response.encodeURL("WEB-INF/Login.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
