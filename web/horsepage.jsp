<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="current" value="${param.language}" scope="session"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="resource" scope="session"/>
<c:set var="isCreated" value="${isCreated}" scope="session"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Horse Races</title>
    <link href="${pageContext.request.contextPath}/userfiles/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/userfiles/styles.css" rel="stylesheet">
</head>
<body>
<script type="text/javascript">
    <c:choose>
    <c:when test="${isFailedAddHorse == true}">
    if (alert("<fmt:message key="message.addhorseerror"/> ")) {
        ${isFailedAddHorse = false}
    }
    </c:when>
    <c:when test="${isFailedEditHorse == true}">
    if (alert("<fmt:message key="message.edithorseerror"/> ")) {
        ${isFailedEditHorse = false}
    }
    </c:when>
    <c:when test="${isFailedDeleteHorse == true}">
    if (alert("<fmt:message key="message.deletehorseerror"/> ")) {
        ${isFailedDeleteHorse = false}
    }
    </c:when>
    </c:choose>
</script>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <ul class="menu">
        <form action="horsepage.jsp" name="form">
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
                <li><a href="controller?command=SHOW_RACES_HISTORY"><fmt:message key="history"/></a></li>
                <li><a href="controller?command=makeresult"><fmt:message key="menu_results"/></a></li>
                <li><a href="controller?command=LOGOUT"><fmt:message key="menu_logout"/></a></li>
            </ul>
        </div>
    </div>
</nav>
<section id="banner-horses">
</section>
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
                <h6 class="text-center"><fmt:message key="horse"/></h6>
            </div>
        </div>
        <c:choose>
        <c:when test="${isCreated == false}">
            <div class="row">
                <div class="col-xs-12">
                    <h6 class="text-center"><fmt:message key="no_info"/></h6>
                </div>
            </div>
        </c:when>
        <c:otherwise>
        <div class="block">
            <jsp:useBean id="horses" scope="session" type="java.util.List"/>
            <form method="post" action="controller">
                <input type="hidden" name="command" value="HORSE_ACTION">
                <table>
                    <thead>
                    <tr>
                        <th><fmt:message key="nickname"/></th>
                        <th><fmt:message key="breed"/></th>
                        <th><fmt:message key="age"/></th>
                        <th><fmt:message key="jockey"/></th>
                        <th><fmt:message key="rating"/></th>
                        <th><fmt:message key="delete"/></th>
                        <th><fmt:message key="edit"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="horse" items="${horses}">
                        <tr>
                            <td>${horse.nickname}</td>
                            <td>${horse.breed}</td>
                            <td>${horse.age}</td>
                            <td>${horse.jockeyName}</td>
                            <td>${horse.rating}</td>
                            <td>
                                <button name="delete_command" value="${horse.id}" class="red" type="submit"><fmt:message
                                        key="delete"/></button>
                            </td>
                            <td>
                                <button name="edit_command" class="green" value="${horse.id}" type="submit"><fmt:message
                                        key="edit"/></button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>
<section class="contact">
    <div class="container-fluid padding-lg">
        <div class="row">
            <div class="col-sm-12">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12">
                            <h6 class="text-center"><fmt:message key="form"/></h6>
                            <h3 class="text-center"><span><fmt:message key="form_text"/></span></h3>
                            <p class="text-center"><fmt:message key="form_rules"/></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box">
            <form method="POST" action="controller">
                <input type="hidden" name="command" value="ADD_HORSE"/>
                <div class="form-group">
                    <input type="text" name="nickname" pattern="^[а-яА-ЯёЁa-zA-Z]+$"
                           placeholder="<fmt:message key="nickname"/>" class="form-control" required>
                </div>
                <div class="form-group">
                    <input type="text" name="breed" pattern="^[а-яА-ЯёЁa-zA-Z]+$"
                           placeholder="<fmt:message key="breed"/>" class="form-control" required>
                </div>
                <div class="form-group">
                    <input type="number" name="age" min="2" max="15" placeholder="<fmt:message key="age"/>"
                           class="form-control" required>
                </div>
                <div class="form-group">
                    <input type="text" name="jockey" pattern="^[а-яА-ЯёЁa-zA-Z]+$"
                           placeholder="<fmt:message key="jockey"/>" class="form-control" required>
                </div>
                <div class="form-group">
                    <input type="number" name="rating" min="0.0" max="10.0" placeholder="<fmt:message key="rating"/>"
                           step="any" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-default"><fmt:message key="submit"/></button>
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
