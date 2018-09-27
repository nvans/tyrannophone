<sec:authorize access="hasRole('EMPLOYEE')">
    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">

        <a class="navbar-brand" href="#"><sec:authentication property="principal.username" /></a>

        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarCustomer">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarEmployee">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customers">Customers</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/contracts">Contracts</a></li>
                <li class="nav-item">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle nav-item"
                                type="button" id="plansMenu" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            Plans
                        </button>
                        <div class="dropdown-menu" aria-labelledby="plansMenu">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/plans">Show plans</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/plans/add">Add plan</a>
                        </div>
                    </div>


                </li>
                <li class="nav-item">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle nav-item"
                                type="button" id="optionsMenu" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            Options
                        </button>
                        <div class="dropdown-menu" aria-labelledby="optionsMenu">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/options">Show options</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/options/add">Add option</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</sec:authorize>
