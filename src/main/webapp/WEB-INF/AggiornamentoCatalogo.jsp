<%--
  Created by IntelliJ IDEA.
  User: giusya
  Date: 04/06/21
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="model.*" %>
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
    <title>Catalog update</title>
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
            <input type = "hidden" name="elenco" value="4"/>
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

                <%if (film == null) {%>
                <h1 class="mt-4" style="color: #ffc107">Add Movie</h1>
                <%} else { %>
                <h1 class="mt-4" style="color: #ffc107">Change Movie</h1>
                <%}%>

                <div class="row">

                    <% if (film == null) { %>

                    <div class="container" style="margin-bottom: 50px;">
                        <div class="row justify-content-center">
                            <div class="col-lg-7 mt-5" style="background-color: #212529">
                                <div class="card shadow-lg border-0 rounded-lg">
                                    <div class="card-header"><h3 class="text-center  my-4" style="color: #ffc107">Add
                                        Movie</h3></div>
                                </div>
                                <div class="card-body">
                                    <form action="AddMovie" method="POST">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="title" id="inputTitle1"
                                                           placeholder="Titolo Movie" required/>
                                                    <label for="inputTitle1" style="color: #212529">Title*</label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="poster" id="inputUrlPoste"
                                                           type="text" placeholder="Url Poster" required/>
                                                    <label for="inputUrlPoste" style="color: #212529">Url Poster*</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="story" id="inputStory"
                                                           type="text" placeholder="Story" overflow="scroll"/>
                                                    <label for="inputStory" style="color: #212529"
                                                           class="overflow-auto">Story</label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="product" id="product"
                                                           type="text" placeholder="Url Poster" />
                                                    <label for="product" style="color: #212529">Production Company</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="originalTitle"
                                                           id="inputOriginalTitile"
                                                           type="text" placeholder="Original Title"/>
                                                    <label for="inputOriginalTitile" style="color: #212529"
                                                           class="overflow-auto">Original Title</label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="releaseDate" id="releaseDate"
                                                           type="date" placeholder="Release date"/>
                                                    <label for="releaseDate" style="color: #212529">Release date</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="duration" id="inputDuration"
                                                           type="numer" min="0" placeholder="Duration"/>
                                                    <label for="inputDuration" style="color: #212529">Duration</label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="imdbRating" id="inputImdbRating"
                                                           type="number" min="0" max="10" placeholder="ImbdRating"/>
                                                    <label for="inputImdbRating"
                                                           style="color: #212529">ImbdRating</label>
                                                </div>
                                            </div>
                                        </div>
                                        <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple genres</em></p>
                                        <div class="row mb-3">

                                            <div class=" mb-3">
                                                <div class="form-floating">
                                                    <input class="form-control" name="gen" id="gen1"
                                                           type="text" placeholder="Story"/>
                                                    <label for="gen1"
                                                           style="color: #212529">Genres</label>
                                                </div>
                                            </div>

                                        </div>
                                        <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple actors</em></p>

                                        <div class="row mb-3">


                                            <div class=" mb-3">
                                                <div class="form-floating">
                                                    <input class="form-control" name="act" id="act1"
                                                           type="text" placeholder="Story"/>
                                                    <label for="act1"
                                                           style="color: #212529">Actors</label>
                                                </div>
                                            </div>


                                        </div>

                                        <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple Director</em></p>
                                        <div class="row mb-3">

                                            <div class=" mb-3">
                                                <div class="form-floating">
                                                    <input class="form-control" name="director" id="dir"
                                                           type="text" placeholder="Story"/>
                                                    <label for="gen1"
                                                           style="color: #212529">Director</label>
                                                </div>
                                            </div>

                                        </div>

                                        <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple Languages</em></p>
                                        <div class="row mb-3">

                                            <div class=" mb-3">
                                                <div class="form-floating">
                                                    <input class="form-control" name="language" id="lan"
                                                           type="text" placeholder="Story"/>
                                                    <label for="lan"
                                                           style="color: #212529">Language</label>
                                                </div>
                                            </div>

                                        </div>

                                        <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple Country</em></p>
                                        <div class="row mb-3">

                                            <div class=" mb-3">
                                                <div class="form-floating">
                                                    <input class="form-control" name="country" id="country"
                                                           type="text" placeholder="Story"/>
                                                    <label for="country"
                                                           style="color: #212529">Country</label>
                                                </div>
                                            </div>

                                        </div>

                                            <div class="input-group mb-3">
                                                <label class="btn-warning input-group-text" for="inputGroupSelect00">Movies</label>
                                                <select class="form-select" id="inputGroupSelect00" name="catalog">
                                                    <option selected>Choose...</option>
                                                    <option value="movies_in_theaters">Movies in Theaters</option>
                                                    <option value="movies_coming_soon">Movies Coming Soon</option>
                                                    <option value="top_rated_movies">Movie Top Rated</option>
                                                    <option value="other">Other</option>
                                                </select>
                                            </div>

                                        <div class="d-flex align-items-center justify-content-center mt-4 mb-0">
                                            <button type="submit" class="btn btn-warning">Create Movie</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="card-footer text-center py-3">

                                </div>
                            </div>
                        </div>
                    </div>


                    <%} else {%>


                    <div class="container" style="margin-bottom: 50px; ">
                        <div class="row justify-content-center">
                            <div class="col-lg-7 mt-5" style="background-color: #212529">
                                <form action="ChangeMovie" method="POST">
                                <div class="card shadow-lg border-0 rounded-lg">
                                    <div class="card-header"><h3 class="text-center  my-4"
                                                                 style="color: #ffc107"><%=film.getTitle()%>
                                    </h3></div>
                                </div>
                                <div class="card-body">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="title" id="inputTitle"
                                                           placeholder="Titolo Movie" value="<%=film.getTitle()%>"  required/>
                                                    <label for="inputTitle" style="color: #212529" >Title
                                                        *</label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="imdbRating" id="imdbRating"
                                                           type="text" placeholder="Url Poster" value="<%=film.getImdbRating()%>" required/>
                                                    <label for="imdbRating" style="color: #212529;">ImbdRating</label>
                                                </div>
                                            </div>
                                        </div>
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating">
                                                <input class="form-control" name="story" id="inputStorym"
                                                       type="text" placeholder="Story" overflow="scroll" value="<%=film.getStoryline()%>"/>
                                                <label for="inputStorym" style="color: #212529"
                                                       class="overflow-auto">Story</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating">
                                                <input class="form-control" name="product" id="productm"
                                                       type="text" placeholder="Product Company" value="<%=film.getProductionCompany()%>"/>
                                                <label for="productm" style="color: #212529">Production Company</label>
                                            </div>
                                        </div>
                                    </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="originalTitle"
                                                           id="inputOriginalTitilem"
                                                           type="text" placeholder="Original Title" value="<%=film.getOriginalTitle()%>"/>
                                                    <label for="inputOriginalTitilem" style="color: #212529"
                                                           class="overflow-auto">Original Title
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="dateRelased"
                                                           id="inputDateRelasedm"
                                                           type="date" placeholder="Enter Date of Relased" value="<%=film.getReleaseDate()%>"/>
                                                    <label for="inputDateRelasedm"
                                                           style="color: #212529">Date of Relased
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="duration" id="inputDurationm"
                                                           type="text" placeholder="duration" value="<%=film.getDuration().toString().substring(2,film.getDuration().toString().length()-2)%>"/>
                                                    <label for="inputDurationm"
                                                           style="color: #212529">Duration
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="poster" id="poster"
                                                           type="text" placeholder="Rating" value="<%=film.getPosterurl()%>"/>
                                                    <label for="poster"
                                                           style="color: #212529">Poster
                                                    </label>
                                                </div>
                                            </div>
                                        </div>

                                        <p style="color: #adb5bd">insert , between actors and genres</p>
                                        <div class="row mb-3">

                                            <div class=" mb-3">
                                                <div class="form-floating">
                                                    <input class="form-control" name="gen" id="gen1i"
                                                           type="text" placeholder="Story" value="<%=film.getGenres().toString().substring(1,film.getGenres().toString().length()-1)%>"/>
                                                    <label for="gen1i"
                                                           style="color: #212529">Genres</label>
                                                </div>
                                            </div>

                                        </div>

                                        <div class="row mb-3">


                                            <div class=" mb-3">
                                                <div class="form-floating">
                                                    <input class="form-control" name="act" id="act1i"
                                                           type="text" placeholder="Story" value="<%=film.getActors().toString().substring(1,film.getActors().toString().length()-1)%>"/>
                                                    <label for="act1i"
                                                           style="color: #212529">Actors</label>
                                                </div>
                                            </div>


                                        </div>

                                    <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple Director</em></p>
                                    <div class="row mb-3">

                                        <div class=" mb-3">
                                            <div class="form-floating">
                                                <input class="form-control" name="director" id="dirm"
                                                       type="text" placeholder="Director" value="<%=film.getDirector().toString().substring(1,film.getDirector().toString().length()-1)%>"/>
                                                <label for="dirm"
                                                       style="color: #212529">Director</label>
                                            </div>
                                        </div>

                                    </div>

                                    <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple Languages</em></p>
                                    <div class="row mb-3">

                                        <div class=" mb-3">
                                            <div class="form-floating">
                                                <input class="form-control" name="language" id="lanm"
                                                       type="text" placeholder="Story" value="<%=film.getLanguage().toString().substring(1,film.getLanguage().toString().length()-1)%>"/>
                                                <label for="lanm"
                                                       style="color: #212529">Language</label>
                                            </div>
                                        </div>

                                    </div>

                                    <p style="color: #adb5bd"><em>Insert a comma ( , ) between multiple Country</em></p>
                                    <div class="row mb-3">

                                        <div class=" mb-3">
                                            <div class="form-floating">
                                                <input class="form-control" name="country" id="countrym"
                                                       type="text" placeholder="Story" value="<%=film.getCountry().toString().substring(1,film.getCountry().toString().length()-1)%>"/>
                                                <label for="countrym"
                                                       style="color: #212529">Country</label>
                                            </div>
                                        </div>

                                    </div>

                                        <div class="row mb-3">
                                            <div class="input-group mb-3">
                                                <label class=" btn-warning input-group-text" for="inputGroupSelect01">Movies</label>
                                                <select class="form-select" id="inputGroupSelect01" name="catalog">
                                                    <option>Choose...</option>
                                                    <option value="movies_in_theaters">Movies in Theaters</option>
                                                    <option value="movies_coming_soon">Movies Coming Soon</option>
                                                    <option value="top_rated_movies">Movie Top Rated</option>
                                                    <option value="other">Other</option>
                                                </select>
                                            </div>


                                        </div>
                                        <div class="d-flex align-items-center justify-content-center mt-4 mb-0">
                                            <button type="submit" class="btn btn-warning">Change Movie</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="card-footer text-center py-3">

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



