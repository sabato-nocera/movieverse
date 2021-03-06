<%--
  Created by IntelliJ IDEA.
  User: Giusy
  Date: 04/06/2021
  Time: 15:50
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
<%
    Integer numeroMaschi = (Integer) request.getAttribute("numeroMaschi");
    Integer numeroDonne = (Integer) request.getAttribute("numeroDonne");
    Integer numeroAltri = (Integer) request.getAttribute("numeroAltri");
    Integer numeroGenereNonEspresso = (Integer) request.getAttribute("numeroGenereNonEspresso");

    Integer numeroMoviesComingSoon = (Integer) request.getAttribute("numeroMoviesComingSoon");
    Integer numeroMoviesInTheaters = (Integer) request.getAttribute("numeroMoviesInTheaters");
    Integer numeroTopRatedMovies = (Integer) request.getAttribute("numeroTopRatedMovies");
    Integer numeroFilmNonInCatalogo = (Integer) request.getAttribute("numeroFilmNonInCatalogo");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Profile</title>
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
                <h1 class="mt-4" style="color: #ffc107">Profile</h1>

                <div class="row">
                    <% if (!user.getAdmin()) {%>
                    <div class="container" style="margin-bottom: 50px;">
                        <div class="row justify-content-center">
                            <div class="col-lg-7 mt-5" style="background-color: #212529">
                                <div class="card shadow-lg border-0 rounded-lg">
                                    <div class="card-header"><h3 class="text-center  my-4"
                                                                 style="color: #ffc107"><%=user.getUsername()%>
                                    </h3></div>
                                </div>
                                <div class="card-body">
                                    <form action="ChangeProfile" method="POST">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <%if(user.getEmail()!=null){%>
                                                    <input class="form-control" name="email" id="inputEmail"
                                                           type="email"
                                                           placeholder="<name@example.com*>"
                                                           value="<%=user.getEmail()%>" required/>
                                                    <label for="inputEmail" style="color: #212529">Email*</label>
                                                    <%}else{%>
                                                    <input class="form-control" name="email" id="inputEmail"
                                                           type="email"
                                                           placeholder="<name@example.com*>" required/>
                                                    <label for="inputEmail" style="color: #212529">Email*</label>
                                                    <%}%>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <%if(user.getUsername()!=null){%>
                                                    <input class="form-control" name="username" id="inputUsername"
                                                           type="text" placeholder="Enter your username*"
                                                           value="<%=user.getUsername()%>" required/>
                                                    <label for="inputUsername" style="color: #212529">Username*</label>
                                                    <%}else{%>
                                                    <input class="form-control" name="username" id="inputUsername"
                                                           type="text" placeholder="Enter your username*"
                                                            required/>
                                                    <label for="inputUsername" style="color: #212529">Username*</label>
                                                    <%}%>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="password" id="inputPassword"
                                                           type="password" placeholder="Create a password*">"
                                                    <label for="inputPassword" style="color: #212529">Change
                                                        Password*</label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="passwordConfirm"
                                                           id="inputPasswordConfirm" type="password"
                                                           placeholder="Confirm password*">"
                                                    <label for="inputPasswordConfirm" style="color: #212529">Confirm
                                                        Password*</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <%if(user.getFirstName()!=null){%>
                                                    <input class="form-control" name="firstName" id="inputFirstName"
                                                           type="text" placeholder="Enter your first name"
                                                           value="<%=user.getFirstName()%>"/>
                                                    <label for="inputFirstName" style="color: #212529">First
                                                        Name</label>
                                                    <%}else{%>
                                                    <input class="form-control" name="firstName" id="inputFirstName"
                                                           type="text" placeholder="Enter your first name"/>
                                                    <label for="inputFirstName" style="color: #212529">First
                                                        Name</label>
                                                    <%}%>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <%if(user.getLastName()!=null){%>
                                                    <input class="form-control" name="lastName" id="inputLastName"
                                                           type="text" placeholder="Enter your last name"
                                                           value="<%=user.getLastName()%>"/>
                                                    <label for="inputLastName" style="color: #212529">Last Name</label>
                                                    <%}else{%>
                                                    <input class="form-control" name="lastName" id="inputLastName"
                                                           type="text" placeholder="Enter your last name"/>
                                                    <label for="inputLastName" style="color: #212529">Last Name</label>
                                                    <%}%>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <%if(user.getDateOfBirth()!=null){%>
                                                    <input class="form-control" name="dateOfBirth" id="inputDateOfBirth"
                                                           type="date" placeholder="Enter your date of birth"
                                                           value="<%=user.getDateOfBirth()%>"/>
                                                    <label for="inputDateOfBirth" style="color: #212529">Date Of
                                                        Birth <%=user.getDateOfBirth()%></label>
                                                    <%}else{%>
                                                    <input class="form-control" name="dateOfBirth" id="inputDateOfBirth"
                                                           type="date" placeholder="Enter your date of birth"
                                                           value="<%=user.getDateOfBirth()%>"/>
                                                    <label for="inputDateOfBirth" style="color: #212529">Date Of
                                                        Birth</label>
                                                    <%}%>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="text-white">Gender</span> <br/>
                                                <div class="form-check form-check-inline">
                                                    <%if (user.getGender()!=null && user.getGender().equalsIgnoreCase("male")) {%>
                                                    <input class="form-check-input" type="radio" name="gender"
                                                           value="male"
                                                           id="flexRadioDefault1" checked>
                                                    <%} else {%>
                                                    <input class="form-check-input" type="radio" name="gender"
                                                           value="male"
                                                           id="flexRadioDefault1">
                                                    <%}%>
                                                    <label class="form-check-label" for="flexRadioDefault1"
                                                           style="color: #ffc107">
                                                        Male
                                                    </label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <%if (user.getGender()!=null && user.getGender().equalsIgnoreCase("female")) {%>
                                                    <input class="form-check-input" type="radio" name="gender"
                                                           value="female" id="flexRadioDefault2" checked>
                                                    <%} else {%>
                                                    <input class="form-check-input" type="radio" name="gender"
                                                           value="female" id="flexRadioDefault2">
                                                    <%}%>
                                                    <label class="form-check-label" for="flexRadioDefault2"
                                                           style="color: #ffc107">
                                                        Female
                                                    </label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <%if (user.getGender()!=null && user.getGender().equalsIgnoreCase("other")) {%>
                                                    <input class="form-check-input" type="radio" name="gender"
                                                           value="other"
                                                           id="flexRadioDefault3" checked>
                                                    <%} else {%>
                                                    <input class="form-check-input" type="radio" name="gender"
                                                           value="other"
                                                           id="flexRadioDefault3">
                                                    <%}%>
                                                    <label class="form-check-label" for="flexRadioDefault3"
                                                           style="color: #ffc107">
                                                        Other
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="d-flex align-items-center justify-content-center mt-4 mb-0">
                                            <button type="submit" class="btn btn-warning">Update</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="card-footer text-center py-3">
                                </div>
                            </div>
                        </div>
                    </div>


                    <% } else { %>
                    <div class="container" style="margin-bottom: 50px;">
                        <div class="row justify-content-center">
                            <div class="col-lg-7 mt-5" style="background-color: #212529">
                                <div class="card shadow-lg border-0 rounded-lg">
                                    <div class="card-header"><h3 class="text-center  my-4"
                                                                 style="color: #ffc107"><%=user.getUsername()%>
                                    </h3></div>
                                </div>
                                <div class="card-body">
                                    <form action="ChangeProfile" method="POST">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <%if(user.getEmail()!=null){%>
                                                    <input class="form-control" name="email" id="inputEmailad"
                                                           type="email"
                                                           placeholder="<name@example.com*>"
                                                           value="<%=user.getEmail()%>" required/>
                                                    <label for="inputEmail" style="color: #212529">Email*</label>
                                                    <%}else{%>
                                                    <input class="form-control" name="email" id="inputEmailad"
                                                           type="email"
                                                           placeholder="<name@example.com*>" required/>
                                                    <label for="inputEmail" style="color: #212529">Email*</label>
                                                    <%}%>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <%if(user.getUsername()!=null){%>
                                                    <input class="form-control" name="username" id="inputUsernamead"
                                                           type="text" placeholder="Enter your username*"
                                                           value="<%=user.getUsername()%>" required/>
                                                    <label for="inputUsername" style="color: #212529">Usernsme*</label>
                                                    <%}else{%>
                                                    <input class="form-control" name="username" id="inputUsernamead"
                                                           type="text" placeholder="Enter your username*" required/>
                                                    <label for="inputUsername" style="color: #212529">Usernsme*</label>
                                                    <%}%>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-floating mb-3 mb-md-0">
                                                    <input class="form-control" name="password" id="inputPasswordad"
                                                           type="password" placeholder="Create a password*"/>
                                                    <label for="inputPassword" style="color: #212529">Change
                                                        Password*</label>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-floating">
                                                    <input class="form-control" name="passwordConfirm"
                                                           id="inputPasswordConfirmad" type="password"
                                                           placeholder="Confirm password*"/>
                                                    <label for="inputPasswordConfirm" style="color: #212529">Confirm
                                                        Password*</label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="d-flex align-items-center justify-content-center mt-4 mb-0">
                                            <button type="submit" class="btn btn-warning">Change Account</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="card-footer text-center py-3">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-6">
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-chart-pie me-1"></i>
                                    Users by gender
                                </div>
                                <div class="card-body"><canvas id="myPersonalPieChart" width="100%" height="50"></canvas></div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-chart-bar me-1"></i>
                                    Movies by catalog
                                </div>
                                <div class="card-body"><canvas id="myPersonalBarChart" width="100%" height="50"></canvas></div>
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
    <script>
        var ctx = document.getElementById('myPersonalPieChart').getContext('2d');
        var myPieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['Male', 'Female', 'Other', 'Not expressed'],
                datasets: [{
                    label: 'Number of users',
                    data: [<%= numeroMaschi %>, <%= numeroDonne %>, <%= numeroAltri %>, <%= numeroGenereNonEspresso %>],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 99, 132, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    color: 'rgb(255,255,255)',
                    borderWidth: 1
                }]
            },
            options: {
                legend: {
                    labels: {
                        fontColor: "white",
                        fontSize: 18
                    }
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });

        var ctx2 = document.getElementById('myPersonalBarChart').getContext('2d');
        var myBarChart = new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: ['Movies in theathers', 'Coming soon', 'Best movies', 'Movies in no catalog'],
                datasets: [{
                    label: 'Number of movies',
                    data: [<%= numeroMoviesInTheaters %>, <%= numeroMoviesComingSoon %>, <%= numeroTopRatedMovies %>, <%= numeroFilmNonInCatalogo %>],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 99, 132, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(255, 159, 64, 1)',
                    ],
                    color: 'rgb(255,255,255)',
                    borderWidth: 1
                }]
            },
            options: {
                legend: {
                    labels: {
                        fontColor: "white",
                        fontSize: 18
                    }
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            fontColor: "white",
                            fontSize: 18,
                            stepSize: 1000,
                            beginAtZero: true
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            fontColor: "white",
                            fontSize: 14,
                            stepSize: 1,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    </script>
</div>
</body>
</html>


