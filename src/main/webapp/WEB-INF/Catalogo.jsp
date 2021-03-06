<%--
  Created by IntelliJ IDEA.
  User: Sabato
  Date: 01/06/2021
  Time: 15:48
  To change this template use File | Settings | File Templates.

--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<% UtenteBean user = (UtenteBean) session.getAttribute("utente"); %>
<% ArrayList<FilmBean> movie = (ArrayList<FilmBean>) request.getAttribute("movie"); %>
<% String elenco = (String) request.getAttribute("elenco"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Catalog</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-film"
         viewBox="0 0 16 16">
        <path d="M0 1a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1V1zm4 0v6h8V1H4zm8 8H4v6h8V9zM1 1v2h2V1H1zm2 3H1v2h2V4zM1 7v2h2V7H1zm2 3H1v2h2v-2zm-2 3v2h2v-2H1zM15 1h-2v2h2V1zm-2 3v2h2V4h-2zm2 3h-2v2h2V7zm-2 3v2h2v-2h-2zm2 3h-2v2h2v-2z"/>
    </svg>
    <link rel="icon" href="css/Image/icon.png"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
            crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3"><img src="css/Image/Logo.png" style="width:200px;height:80px"></a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i
            class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0" action="Catalogo">
        <div class="input-group">
            <input class="form-control" type="text" placeholder="Search by movie title " aria-label="Search for..."
                   aria-describedby="btnNavbarSearch" name="titleSearched"/>
            <input type="hidden" name="elenco" value="4"/>
            <button class="btn btn-outline-warning" id="btnNavbarSearch" type="submit"><i class="fas fa-search"></i>
            </button>
        </div>
    </form>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown"
               aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="Profilo">Profile</a></li>
                <li>
                    <hr class="dropdown-divider"/>
                </li>
                <li><a class="dropdown-item" href="Index?action=logout">
                    Logout
                </a></li>
            </ul>
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <div class="sb-sidenav-menu-heading">Movie</div>

                    <form action="Catalogo" method="POST">
                        <div class="nav-link">
                            <div class="sb-nav-link-icon"><i class="fa fa-film"></i></div>
                            <button type="submit" class="btn nav-link" name="elenco" value="1">Movies in Theaters
                            </button>
                        </div>
                    </form>

                    <form action="Catalogo" method="POST">
                        <div class="nav-link">
                            <div class="sb-nav-link-icon"><i class="fa fa-film"></i></div>
                            <button type="submit" class="btn nav-link" name="elenco" value="2">Coming Soon</button>
                        </div>
                    </form>

                    <form action="Catalogo" method="POST">
                        <div class="nav-link">
                            <div class="sb-nav-link-icon"><i class="fa fa-film"></i></div>
                            <button type="submit" class="btn nav-link" name="elenco" value="3">Best Movies</button>
                        </div>
                    </form>

                    <form action="Catalogo" method="POST">
                        <div class="nav-link">
                            <div class="sb-nav-link-icon"><i class="fa fa-film"></i></div>
                            <button type="submit" class="btn nav-link" name="elenco" value="4">All Movies</button>
                        </div>
                    </form>

                    <% if (!user.getAdmin()) {%>
                    <div class="sb-sidenav-menu-heading">List</div>
                    <form action="Catalogo" method="POST">
                        <div class="nav-link">
                            <div class="sb-nav-link-icon"><i
                                    class="fas fa-eye"></i></div>
                            <button type="submit" class="btn nav-link" name="elenco" value="5">Watched List
                            </button>
                        </div>
                        <div class="nav-link">
                            <div class="sb-nav-link-icon"><i
                                    class="fas fa-eye"></i></div>
                            <button type="submit" class="btn nav-link" name="elenco" value="6">To Watch List
                            </button>
                        </div>
                    </form>
                    <% } else {%>
                    <div class="sb-sidenav-menu-heading">Options</div>
                    <a class="nav-link" href="AggiornamentoCatalogo">
                        <div class="sb-nav-link-icon"><i class="fas fa-plus-circle"></i></div>
                        Add Movie
                    </a>
                    <% } %>
                </div>
            </div>
            <div class="sb-sidenav-footer">
                <div class="small">Logged in as:</div>
                <%=user.getUsername()%>
            </div>
        </nav>
    </div>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4" style="color: #ffc107">Movie</h1>

                <div class="btn-toolbar mb-3" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="First group" style="margin-top: 10px;">
                        <div class="input-group-prepend btn-warning pt-2 pb-1">
                            <span class="input-group-text btn-warning">Order by :</span>
                        </div>
                        <button type="button" class="btn btn-light"><a class="text-decoration-none text-black-50"
                                                                       href="Catalogo?order=title&elenco=<%=elenco%>">Title</a>
                        </button>
                        <button type="button" class="btn btn-light"><a class="text-decoration-none text-black-50"
                                                                       href="Catalogo?order=releaseDate&elenco=<%=elenco%>">Release
                            Date</a></button>
                        <button type="button" class="btn btn-light"><a class="text-decoration-none text-black-50"
                                                                       href="Catalogo?order=averageRating&elenco=<%=elenco%>">User
                            Rating</a></button>
                        <button type="button" class="btn btn-light"><a class="text-decoration-none text-black-50"
                                                                       href="Catalogo?order=imdbRating&elenco=<%=elenco%>">Movieverse
                            Rating</a></button>
                    </div>

                    <form class="d-none d-md-inline-block form-inline me-0 me-md-3 my-2" style="padding-top: 10px;"
                          action="Catalogo">
                        <div class="input-group">
                            <input class="form-control" type="text" placeholder="Search by genre"
                                   aria-label="Search for..."
                                   aria-describedby="btnNavbarSearch" name="genreSearched"/>
                            <input type="hidden" name="elenco" value="<%=elenco%>">
                            <button class="btn btn-warning" type="submit"><i class="fas fa-search"></i>
                            </button>
                        </div>
                    </form>
                    <form class="d-none d-md-inline-block form-inline me-0 me-md-3 my-2" style="padding-top: 10px;"
                          action="Catalogo">
                        <div class="input-group">
                            <input class="form-control" type="text" placeholder="Search by actor"
                                   aria-label="Search for..."
                                   aria-describedby="btnNavbarSearch" name="actorSearched"/>
                            <input type="hidden" name="elenco" value="<%=elenco%>">
                            <button class="btn btn-warning" type="submit"><i class="fas fa-search"></i>
                            </button>
                        </div>
                    </form>


                </div>
                <div class="row">

                    <% int i;
                        for (i = 0; i < movie.size(); i++) {%>
                    <div class="card mb-3" style="max-width: 540px; margin-right: 10px">
                        <div class="row g-0">
                            <div class="col-md-4">
                                <% if (movie.get(i).getPosterurl() == null || movie.get(i).getPosterurl().equalsIgnoreCase("null") || movie.get(i).getPosterurl().equals("")) {%>
                                <img src="css/Image/locandina.png" style="width:180px; height:290px;margin-top: 10px">
                                <%} else {%>
                                <img src="<%=movie.get(i).getPosterurl()%>"
                                     style="width:180px; height:290px;margin-top: 10px"
                                     onError="this.src = 'css/Image/locandina.png'">
                                <%}%>
                            </div>
                            <div class="col-md-8">
                                <div class="card-body">
                                    <h6 class="card-title"><%=movie.get(i).getTitle()%>
                                    </h6>
                                    <p class="card-text">Genres: <%
                                        int j;
                                        for (j = 0; j < movie.get(i).getGenres().size(); j++) {
                                            if (j != 0) {
                                    %>
                                        <%=", "%>
                                        <%}%>
                                        <%=movie.get(i).getGenres().get(j).toString() %><%}%></p>
                                    <p class="card-text">Actors: <%
                                        int h;
                                        for (h = 0; h < movie.get(i).getActors().size(); h++) {
                                            if (h != 0) {
                                    %>
                                        <%=", "%>
                                        <%}%>
                                        <%=movie.get(i).getActors().get(h).toString()%><%}%></p>
                                    <%if (movie.get(i).getReleaseDate() != null) {%>
                                    <p class="card-text">Release Date:
                                        <!-- Serve per stampare la data nel formato che ci interessa -->
                                        <%=LocalDateTime.ofInstant(movie.get(i).getReleaseDate().toInstant(), ZoneId.of("Z")).format(DateTimeFormatter.ofPattern("d MMM uuuu"))%>
                                    </p>
                                    <%}%>
                                    <p class="card-text">User Rating: <%=movie.get(i).getAverageRating()%>
                                    </p>
                                    <p class="card-text">Movieverse Rating: <%=movie.get(i).getImdbRating()%>
                                    </p>
                                    <p class="card-text"><small class="text-muted">
                                    <form action="Film" method="POST">
                                        <button type="submit" class="btn btn-link" name="TitoloFilm"
                                                value="<%=movie.get(i).getTitle()%>">Dettagli
                                        </button>
                                    </form>

                                    </small></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%}%>

                </div>
                <main>
                    <footer class="py-4  mt-auto" style="background-color: #212529">
                        <div class="container-fluid px-4">
                            <div class="d-flex align-items-center justify-content-between small">
                                <div class="text-muted">Copyright &copy; Movieverse 2021</div>
                                <div>
                                    <a href="#">Privacy Policy</a>
                                    &middot;
                                    <a href="#">Terms &amp; Conditions</a>
                                </div>
                            </div>
                        </div>
                    </footer>
                </main>
            </div>
        </main>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
    <script src="assets/demo/chart-area-demo.js"></script>
    <script src="assets/demo/chart-bar-demo.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
    <script src="js/datatables-simple-demo.js"></script>
</div>
</body>
</html>

