<%--
  Created by IntelliJ IDEA.
  User: Sabato
  Date: 01/06/2021
  Time: 15:16
  To change this template use File | Settings | File Templates.

 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if(errorMessage == null){
        errorMessage = "";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Login</title>
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
                <div class="row justify-content-center">
                    <div class="col-lg-5">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header"><img src="css/Image/Logo.png" style="width:300px;height: 150px; margin-left: 90px;">
                                <h3 class="text-center my-4" style="color: #ffc107">Login</h3></div>
                            <div class="card-body">
                                <form action="Catalogo" method="POST">
                                    <div class="form-floating mb-3">
                                        <input class="form-control" name="username" id="inputUsername" type="text"
                                               placeholder="Username*"/>
                                        <label for="inputUsername" style="color: #212529">Username*</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" name="password" id="inputPassword" type="password"
                                               placeholder="Password*"/>
                                        <label for="inputPassword" style="color: #212529">Password*</label>
                                    </div>
                                    <div class="d-flex align-items-center justify-content-center mt-4 mb-0">
                                        <button type="submit" class="btn btn-warning">Login</button>
                                    </div>
                                </form>
                            </div>
                            <div class="card-footer text-center py-3">
                                <div class="small"><a href="Registrazione">Need an account? Sign up!</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
    <div id="layoutAuthentication_footer">
        <footer class="py-4 mt-auto" style="background-color: #212529">
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
        sweetAlert("Login failed", errorMessage, "error");
    }
</script>
</body>
</html>