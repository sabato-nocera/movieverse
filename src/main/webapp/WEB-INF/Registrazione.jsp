<%--
  Created by IntelliJ IDEA.
  User: Sabato
  Date: 01/06/2021
  Time: 15:17
  To change this template use File | Settings | File Templates.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if(errorMessage == null){
        errorMessage = "";
    }
%>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Register</title>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
            crossorigin="anonymous"></script>
    <!-- SweetAlerts per la visualizzazione di messaggi di errore o di conferma -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.11.0/sweetalert2.all.min.js"></script>
</head>
<body class="bg-lg">
<div id="layoutAuthentication">
    <div id="layoutAuthentication_content">
        <main>
            <div class="container">
                <div class="row justify-content-center" >
                    <div class="col-lg-7 mt-5" style="background-color: #212529">
                        <div class="card shadow-lg border-0 rounded-lg">
                            <div class="card-header"><h3 class="text-center  my-4" style="color: #ffc107">Create
                                Account</h3></div>
                        </div>
                        <div class="card-body">
                            <form action="Registrazione" method="POST">
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3 mb-md-0">
                                            <input class="form-control" name="email" id="inputEmail" type="email"
                                                   placeholder="name@example.com*" required/>
                                            <label for="inputEmail" style="color: #212529">Email address*</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input class="form-control" name="username" id="inputUsername"
                                                   type="text" placeholder="Enter your username*" required/>
                                            <label for="inputUsername" style="color: #212529">Username*</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3 mb-md-0">
                                            <input class="form-control" name="password" id="inputPassword"
                                                   type="password" placeholder="Create a password*" required/>
                                            <label for="inputPassword" style="color: #212529">Password*</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input class="form-control" name="passwordConfirm"
                                                   id="inputPasswordConfirm" type="password"
                                                   placeholder="Confirm password*" required/>
                                            <label for="inputPasswordConfirm" style="color: #212529">Confirm
                                                Password*</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3 mb-md-0">
                                            <input class="form-control" name="firstName" id="inputFirstName"
                                                   type="text" placeholder="Enter your first name"/>
                                            <label for="inputFirstName" style="color: #212529">First name</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input class="form-control" name="lastName" id="inputLastName"
                                                   type="text" placeholder="Enter your last name"/>
                                            <label for="inputLastName" style="color: #212529">Last name</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3 mb-md-0">
                                            <input class="form-control" name="dateOfBirth" id="inputDateOfBirth"
                                                   type="date" placeholder="Enter your date of birth"/>
                                            <label for="inputDateOfBirth" style="color: #212529">Date of birth</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <span class="text-white">Gender</span> <br/>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="gender" value="male"
                                                   id="flexRadioDefault1">
                                            <label class="form-check-label" for="flexRadioDefault1"
                                                   style="color: #ffc107">
                                                Male
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="gender"
                                                   value="female" id="flexRadioDefault2">
                                            <label class="form-check-label" for="flexRadioDefault2"
                                                   style="color: #ffc107">
                                                Female
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="gender" value="other"
                                                   id="flexRadioDefault3">
                                            <label class="form-check-label" for="flexRadioDefault3"
                                                   style="color: #ffc107">
                                                Other
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center justify-content-center mt-4 mb-0">
                                    <button type="submit" class="btn btn-warning">Create Account</button>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-center py-3">
                            <div class="small"><a href="Index">Have an account? Go to login</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<div id="layoutAuthentication_footer" style="background-color: #212529">
    <footer class="py-4 mt-auto" style="background-color:#212529;">
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
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script type="text/javascript">
    var errorMessage = "<%=errorMessage%>";
    if (errorMessage != null && errorMessage.length > 0) {
        sweetAlert("Registration failed", errorMessage, "error");
    }
</script>
</body>
</html>