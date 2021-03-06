<%--
  Created by IntelliJ IDEA.
  User: Giusy
  Date: 04/06/2021
  Time: 15:56
  To change this template use File | Settings | File Templates.

--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="org.bson.Document" %>
<% UtenteBean user = (UtenteBean) session.getAttribute("utente"); %>
<% FilmBean film = (FilmBean) request.getAttribute("Film");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Film</title>
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
                <h1 class="mt-4" style="color: #ffc107"><%=film.getTitle()%>
                </h1>


                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-4" style="max-width: 360px; min-width: 360px;">
                            <% if (film.getPosterurl() == null || film.getPosterurl().equalsIgnoreCase("null") || film.getPosterurl().equals("")) {%>
                            <img src="css/Image/locandina.png" style="width:340px; height:490px; margin: 10px;">
                            <%} else {%>
                            <img src="<%=film.getPosterurl()%>"
                                 style="width:340px; height:490px; margin: 10px;"
                                 onError="this.src = 'css/Image/locandina.png'">
                            <%}%>

                            <div class="text-center" style="margin-bottom: 10px;">
                                <%if (user.getAdmin()) { %>
                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                    <form action="AggiornamentoCatalogo" method="POST">
                                        <button type="submit" class="btn btn-warning" name="TitoloFilm"
                                                value="<%=film.getTitle()%>" style="margin-right: 5px;">Edit Movie
                                        </button>
                                    </form>
                                    <form action="RemoveMovie" method="POST">
                                        <button type="submit" class="btn btn-outline-danger" name="TitoloFilm"
                                                value="<%=film.getTitle()%>">Remove Movie
                                        </button>
                                    </form>
                                </div>
                                <% } else { %>
                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                    <%if (film.getCatalog() == null || !film.getCatalog().equalsIgnoreCase("movies_coming_soon")) {%>
                                    <% if (user.getViewedMovies()==null || !user.getViewedMovies().contains(film.getId())) {%>
                                    <form action="AddWatched" method="POST">
                                        <button type="submit" class="btn btn-warning" name="TitoloFilm"
                                                value="<%=film.getTitle()%>" style="margin-right: 5px;">Add to Watched List
                                        </button>
                                    </form>
                                    <%} else {%>
                                    <form action="RemoveWatchedMovie" method="POST">
                                        <button type="submit" class="btn btn-outline-warning" name="TitoloFilm"
                                                value="<%=film.getTitle()%>" style="margin-right: 5px;">Remove form
                                            Watched List
                                        </button>
                                    </form>
                                    <%
                                            }
                                        }
                                        if (user.getMoviesToSee()==null || !user.getMoviesToSee().contains(film.getId())) {
                                    %>
                                    <form action="AddToWatch" method="POST">
                                        <button type="submit" class="btn btn-warning" name="TitoloFilm"
                                                value="<%=film.getTitle()%>">Add to Watch List
                                        </button>
                                    </form>
                                    <%} else {%>
                                    <form action="RemoveToWatchMovie" method="POST">
                                        <button type="submit" class="btn btn-outline-warning" name="TitoloFilm"
                                                value="<%=film.getTitle()%>">Remove form Watch List
                                        </button>
                                    </form>
                                    <%}%>
                                </div>
                                <%}%>
                            </div>
                        </div>


                        <div class="col-md-8">
                            <div class="card-body">
                                <h3 class="card-title"><%=film.getTitle()%>
                                </h3>

                                <%if (film.getOriginalTitle() != null && !film.getOriginalTitle().equals("")) {%>
                                <h5 class="card-title">Original Title: <%=film.getOriginalTitle()%>
                                </h5> <%}%>

                                <h5 class="card-title">Movieverse Rating: <%=film.getImdbRating()%>
                                </h5>
                                <h6 class="card-title">User Rating: <%=film.getAverageRating()%>
                                </h6>

                                <%if(film.getGenres()!=null){%>
                                <p class="card-title">Genres: <%
                                    int j;
                                    if (film.getGenres() != null) {
                                        for (j = 0; j < film.getGenres().size(); j++) {
                                            if (j != 0) {
                                %>
                                    <%=", "%>
                                    <%}%>
                                    <%=film.getGenres().get(j).toString() %><%
                                            }
                                        }
                                    %></p>
                                <%}%>

                                <%if(film.getActors()!=null){%>
                                <p class="card-title">Actors: <%
                                    int h;
                                    if (film.getGenres() != null) {
                                            for (h = 0; h < film.getActors().size(); h++) {
                                                if (h != 0) {
                                %>
                                    <%=", "%>
                                    <%}%>
                                    <%=film.getActors().get(h).toString()%><%
                                            }
                                        }
                                    %></p>
                                <%}%>

                                <%if (film.getReleaseDate() != null) {%>
                                <p class="card-title">Release date:
                                    <!-- Serve per stampare la data nel formato che ci interessa -->
                                    <%=LocalDateTime.ofInstant(film.getReleaseDate().toInstant(), ZoneId.of("Z")).format(DateTimeFormatter.ofPattern("d MMM uuuu"))%>
                                </p>
                                <%}%>

                                <% if (film.getProductionCompany() != null) { %>
                                <p class="card-text">Production Company : <%=film.getProductionCompany()%>
                                </p><% } %>
                               <% if (film.getDirector() != null) { %>
                                <p class="card-text">Director : <%
                                    int z;
                                        for (z = 0; z < film.getDirector().size(); z++) {
                                            if (z != 0) {
                                %>
                                    <%=", "%>
                                    <%}%>
                                    <%=film.getDirector().get(z).toString()%><%
                                            }
                                    %></p>
                                <%}%>
                                <%if (film.getLanguage() != null) {%>
                                <p class="card-text">Language : <%
                                    int w;

                                        for (w = 0; w < film.getLanguage().size(); w++) {
                                            if (w != 0) {
                                %>
                                    <%=", "%>
                                    <%}%>
                                    <%=film.getLanguage().get(w).toString()%><%
                                            }
                                    %></p>
                                <%}%>
                                <%if (film.getCountry() != null) {%>
                                <p class="card-text">Country : <%
                                    int y;

                                        for (y = 0; y < film.getCountry().size(); y++) {
                                            if (y != 0) {
                                %>
                                    <%=", "%>
                                    <%}%>
                                    <%=film.getCountry().get(y).toString()%><%
                                            }

                                    %></p>
                                <%}%>

                                <%if(film.getDuration()!= null){%>
                                <p class="card-title">Duration : <%=film.getDuration().substring(2)%>
                                </p>
                                <%}%>

                                <%if(film.getStoryline()!=null){%>
                                <p class="card-title">Story: <%=film.getStoryline()%>
                                </p>
                                <%}%>


                            </div>
                        </div>
                    </div>
                </div>

                    <%List<Document> recensioni = film.getReviews();%>
                <div class="card mb-3 p-3">
                    <div class="row g-0">
                        <div class="col-md-4" style="width: 75%;">
                            <h3 style="color: #ffc107; margin: 10px;">Reviews</h3>
                            <%if (recensioni != null) {%>
                            <ul class=" list-group list-group-flush">
                                <%for (int i = 0; i < recensioni.size(); i++) {%>
                                <li class="list-group-item" style="background: #212529; color: #adb5bd">
                                    <h6 style="color: #ffc107">
                                        <%=recensioni.get(i).getString("userUsername")%>
                                        <span class="badge bg-warning">
                                            <span class="fa fa-star"></span>
                                            <span class="pl-3"><%=recensioni.get(i).getDouble("vote")%></span>
                                        </span>
                                    </h6>
                                    <p-3><%=recensioni.get(i).getString("comment")%>
                                    </p-3>

                                </li>
                                <%}%>
                            </ul>
                            <%} else {%>
                            <p class="m-3 p-3">No Reviews Present</p>
                            <%}%>
                        </div>


                        <%if (!user.getAdmin()) {%>
                        <% if (recensioni != null) {
                            boolean inserita = false;
                            for (int i = 0; i < recensioni.size(); i++) {
                                if (recensioni.get(i).getString("userUsername").equals(user.getUsername())) {
                                    inserita = true;
                        %>
                        <div class="col-md-8" style="width: 25%">
                            <div class="card-body">
                                <h6 style="color:#ffc107;">Write Review</h6>
                                <form action="ChangeReview" method="post">
                                    <div class="input-group">
                                        <button class="input-group-text btn-outline-warning" name="username"
                                                value="<%=user.getUsername()%>" disabled><%=user.getUsername()%>
                                        </button>
                                        <input type="number" min="1" max="10" class="form-control" placeholder="Vote"
                                               id="vt" name="vote" value="<%=recensioni.get(i).getDouble("vote")%>">
                                        <label for="vt" style="color: #212529">Vote</label>
                                    </div>
                                    <div class="mb-3" style="margin-top: 10px; margin-bottom: -10px;">
                                        <input class="form-control overflow-auto" name="userReview" id="rw" rows="3"
                                               type="text" placeholder="Write review ... "
                                               value="<%=recensioni.get(i).getString("comment")%>"/>
                                        <label for="rw" class="form-label" style="color: #212529" name="userReview">Review</label>
                                    </div>
                                    <div class="text-center" style="margin-bottom: 5px;">
                                        <button type="submit" class="btn btn-warning" name="addreview"
                                                value="<%=film.getTitle()%>">Change Review
                                        </button>
                                    </div>

                                </form>
                            </div>
                        </div>

                        <% }
                        }
                            if (!inserita) {%>
                        <div class="col-md-8" style="width: 25%">
                            <div class="card-body">
                                <h6 style="color:#ffc107;">Write Review</h6>
                                <form action="AddRecensione" method="post">
                                    <div class="input-group">
                                        <button class="input-group-text btn-outline-warning" id="basic"
                                                name="username" value="<%=user.getUsername()%>"
                                                disabled><%=user.getUsername()%>
                                        </button>
                                        <input type="number" min="1" max="10" class="form-control" placeholder="Vote"
                                               id="v" name="vote">
                                        <label for="v" style="color: #212529">Vote</label>
                                    </div>
                                    <div class="mb-3" style="margin-top: 10px; margin-bottom: -10px;">
                                        <textarea class="form-control" id="re" rows="3"
                                                  placeholder="Write review ... " name="userReview"></textarea>
                                        <label for="re" class="form-label" name="userReview"></label>
                                    </div>
                                    <div class="text-center" style="margin-bottom: 5px;">
                                        <button type="submit" class="btn btn-warning" name="addreview"
                                                value="<%=film.getTitle()%>">Add Review
                                        </button>
                                    </div>

                                </form>
                            </div>
                        </div>
                        <% }


                        } else {
                        %>
                        <div class="col-md-8" style="width: 25%">
                            <div class="card-body">
                                <h6 style="color:#ffc107;">Write Review</h6>
                                <form action="AddRecensione" method="post">
                                    <div class="input-group">
                                        <button class="input-group-text btn-outline-warning" id="basic-addon1"
                                                name="username" value="<%=user.getUsername()%>"
                                                disabled><%=user.getUsername()%>
                                        </button>
                                        <input type="number" min="1" max="10" class="form-control" placeholder="Vote"
                                               id="vote" name="vote">
                                        <label for="vote" style="color: #212529">Vote</label>
                                    </div>
                                    <div class="mb-3" style="margin-top: 10px; margin-bottom: -10px;">
                                        <textarea class="form-control" id="userReview" rows="3"
                                                  placeholder="Write review ... " name="userReview"></textarea>
                                        <label for="userReview" class="form-label" name="userReview"></label>
                                    </div>
                                    <div class="text-center" style="margin-bottom: 5px;">
                                        <button type="submit" class="btn btn-warning" name="addreview"
                                                value="<%=film.getTitle()%>">Add Review
                                        </button>
                                    </div>

                                </form>
                            </div>
                        </div>
                        <%
                                }
                            }
                        %>

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

</body>
</html>


