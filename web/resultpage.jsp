<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link href="${pageContext.request.contextPath}/userfiles/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/userfiles/styles.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <ul class="menu">
        <form action="resultpage.jsp" name="form">
            <div class="styled-select slate">
                <select name="language" onchange="this.form.submit()">
                    <c:choose>
                        <c:when test="${current.equals('ru_RU')}">
                            <option selected value="ru_RU"><fmt:message key="russian"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="ru_RU"><fmt:message key="russian"/></option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${current.equals('en_EN')}">
                            <option selected value="en_EN"><fmt:message key="english"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="en_EN"><fmt:message key="english"/></option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${current.equals('bel_BEL')}">
                            <option selected value="bel_BEL"><fmt:message key="belarusian"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="bel_BEL"><fmt:message key="belarusian"/></option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </form>
    </ul>
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#banner"><span class="gold">HORSE </span>RACES</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="controller?command=SHOW_USERS"><fmt:message key="menu_users"/></a></li>
                <li><a href="controller?command=SHOW_RACES"><fmt:message key="menu_races"/></a></li>
                <li><a href="controller?command=SHOW_HORSES"><fmt:message key="menu_horses"/></a></li>
                <li><a href="controller?command=SHOW_RACES_HISTORY"><fmt:message key="history"/> </a></li>
                <li><a href="controller?command=makeresult"><fmt:message key="menu_results"/></a></li>
                <li><a href="controller?command=LOGOUT"><fmt:message key="menu_logout"/></a></li>
            </ul>
        </div>
    </div>
</nav>
<section id="banner">
</section>
<span class="anchor" id="about"></span>
<section class="about">
    <div class="container padding-2x">
        <div class="row">
            <div class="col-sm-12">
                <h6 class="text-center"><fmt:message key="about"/></h6>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <p><fmt:message key="horses_text"/>
                </p>
            </div>
        </div>
    </div>
</section>
<section class="services">
    <div class="container-fluid padding-lg">
        <div class="row">
            <div class="col-xs-12">
                <h6 class="text-center"><fmt:message key="menu_results"/></h6>
            </div>
        </div>
        <div class = "block">
                <table>
                    <thead>
                    <tr>
                        <th><fmt:message key="result_id"/> </th>
                        <th><fmt:message key="horseid"/></th>
                        <th><fmt:message key="raceid"/></th>
                        <th><fmt:message key="place"/></th>
                    </tr>
                    </thead>
                    <jsp:useBean id="list" scope="session" type="java.util.List"/>
                    <tbody>
                    <c:forEach var="post" items="${list}">
                        <tr><td>${post.id}</td>
                            <td>${post.horseId}</td>
                            <td>${post.raceId}</td>
                            <td>${post.place}</td>
                            </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="userfiles/bootstrap.min.js"></script>
<script src="userfiles/custom.js"></script>
</body>
</html>
