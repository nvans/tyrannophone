<sec:authorize access="hasRole('EMPLOYEE')">
    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">

        <a class="navbar-brand" href="#"><sec:authentication property="principal.username" /></a>

        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarEmployee">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarEmployee">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link text-light" href="${pageContext.request.contextPath}/customers">Customers</a></li>
                <li class="nav-item"><a class="nav-link text-light" href="${pageContext.request.contextPath}/contracts">Contracts</a></li>
                <li class="nav-item">
                    <div class="nav-item dropdown">
                        <button class="btn btn-outline-dark text-light dropdown-toggle"
                                type="button" id="plansMenu" data-toggle="dropdown"
                                aria-haspopup="false" aria-expanded="false">
                            Plans
                        </button>
                        <div class="nav-item dropdown-menu" aria-labelledby="plansMenu">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/plans">Show plans</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/plans/add">Add plan</a>
                        </div>
                    </div>
                </li>
                <li class="nav-item">
                    <div class="dropdown">
                        <button class="btn btn-outline-dark text-light dropdown-toggle"
                                type="button" id="optionsMenu" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            Options
                        </button>
                        <div class="dropdown-menu nav-item" aria-labelledby="optionsMenu">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/options">Show options</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/options/add">Add option</a>
                        </div>
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">

                <li class="nav-item"><a class="navbar-brand text-warning" href="${pageContext.request.contextPath}/cart">Orders</a></li>
            </ul>
        </div>
    </nav>
</sec:authorize>
