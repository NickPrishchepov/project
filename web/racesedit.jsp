<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="current" value="${param.language}" scope="session"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="resource" scope="session"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Horse Races</title>
    <link href="userfiles/bootstrap.min.css" rel="stylesheet">
    <link href="userfiles/horsepagecss.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#banner"><span class="gold">HORSE </span>RACES</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="controller?command=SHOW_USERS"><fmt:message key="menu_users"/></a></li>
                <li><a href="controller?command=SHOW_RACES"><fmt:message key="menu_races"/></a></li>
                <li><a href="controller?command=SHOW_HORSES"><fmt:message key="menu_horses"/></a></li>
                <li><a href="controller?command=SHOW_RACES_HISTORY">History</a></li>
                <li><a href="controller?command=makeresult"><fmt:message key="menu_results"/></a></li>
                <li><a href="controller?command=LOGOUT"><fmt:message key="menu_logout"/></a></li>
            </ul>
        </div>
    </div>
</nav>
<section id="banner">
</section>
<section class="contact">
    <div class="container-fluid padding-lg">
        <div class="row">
            <div class="col-sm-12">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12">
                            <h6 class="text-center"><fmt:message key="editform"/></h6>
                            <h3 class="text-center"><span><fmt:message key="form_text"/></span></h3>
                            <p class="text-center"><fmt:message key="form_rules"/></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box">
            <form method="POST" action="controller">
                <input type="hidden" name="command" value="EDIT_RACE"/>
                <div class="form-group">
                    <input type="date" name="date" placeholder="<fmt:message key="date"/>" class="form-control"
                           required>
                </div>
                <div class="form-group">
                    <input type="text" pattern="^[а-яА-ЯёЁa-zA-Z]+$" name="location"
                           placeholder="<fmt:message key="location"/>" class="form-control" required>
                </div>
                <div class="form-group">
                    <select class="form-control" name="type" required>
                        <option value="flat_racing">flat_racing</option>
                        <option value="steeplechase">steeplechase</option>
                        <option value="harness_racing">harness_racing</option>
                    </select>
                </div>
                <button type="submit" name="ddd" class="btn btn-default"><fmt:message key="submit"/></button>
            </form>
        </div>
    </div>
</section>
<section id="footer-2">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <p><fmt:message key="horseraces"/> © 2018</p>
            </div>
        </div>
    </div>
</section>
</body>
</html>
