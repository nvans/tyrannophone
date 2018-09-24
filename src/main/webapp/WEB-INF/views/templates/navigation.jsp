<nav class="navbar navbar-expand-md navbar-light">

    <a class="navbar-brand" href="#">Tyrannophone</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarContent">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarContent">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="#">Page 1</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Page 2</a></li>
            <li class="nav-item">
                <sec:authorize access="isAnonymous()">
                    <a class = "nav-link" data-toggle="modal" data-target="#login-modal">Sign in</a>
                    <%--<a class = "nav-link" href="${pageContext.request.contextPath}/login">Sign in</a>--%>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <a class = "nav-link" href="${pageContext.request.contextPath}/logout">Sign Out</a>
                </sec:authorize>
            </li>
        </ul>
    </div>
</nav>

<sec:authorize access="isAnonymous()">
    <div class="modal fade" id="login-modal">
        <div class="modal-dialog modal-dialog-centered modal-sm">
            <div class="modal-content">
                <!-- header -->
                <div class="modal-header">
                    <h3 class="modal-title">Login Form</h3>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <!-- body -->
                <div class="modal-header">
                    <form role="form" method="post" id="login-form" action="${pageContext.request.contextPath}/login">
                        <div class="form-group">
                            <input id="loguser" type="text" name="username" class="form-control" placeholder="Email or number" required/>
                            <input type="password" name="password" class="form-control" placeholder="Password" required/>
                        </div>
                    </form>
                </div>
                <!-- footer -->
                <div class="modal-footer">
                    <button name="submit" type="submit" class="btn btn-primary btn-block" form="login-form">Log In</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        $('.modal').on('shown.bs.modal', function() {
            $(this).find('#logUser').focus();
        });
    </script>
</sec:authorize>