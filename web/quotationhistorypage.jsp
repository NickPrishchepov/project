<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="current" value="${param.language}" scope="session"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="resource" scope="session"/>
<html>
<head>

    <title><fmt:message key="horseraces"/></title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <link rel="stylesheet" href="./bookmakerfiles/bootstrap.min.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="./bookmakerfiles/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="./bookmakerfiles/flexslider.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="./bookmakerfiles/chocolat.css" type="text/css" media="screen"/>

</head>
<body>
<ul class="menu">

    <form action="quotationhistorypage.jsp" name="form">
        <div class="styled-select slate">
            <select name="language" onchange="this.form.submit()">
                <c:choose>
                    <c:when test="${current.equals('ru_RU')}">
                        <option selected value="ru_RU"><fmt:message key="russian"/> </option>
                    </c:when>
                    <c:otherwise>
                        <option value="ru_RU"><fmt:message key="russian"/> </option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${current.equals('en_EN')}">
                        <option selected value="en_EN"><fmt:message key="english"/> </option>
                    </c:when>
                    <c:otherwise>
                        <option value="en_EN"><fmt:message key="english"/> </option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${current.equals('bel_BEL')}">
                        <option selected value="bel_BEL"><fmt:message key="belarusian"/> </option>
                    </c:when>
                    <c:otherwise>
                        <option value="bel_BEL"><fmt:message key="belarusian"/> </option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
    </form>
</ul>
<div class="header">

    <nav class="navbar navbar-default">
        <div class="container-fluid">

            <div class="navbar-header">
                <a class="navbar-brand" href="#"><fmt:message key="horseraces"/></a>
            </div>

            <div class="navbar-collapse collapse hover-effect" id="navbar">
                <ul>
                    <li><a href="controller?command=SHOW_RACES_BEFORE_ADD_BET"><span data-hover="<fmt:message key="addbet"/>"><fmt:message key="addbet"/></span></a></li>
                    <li><a href="controller?command=SHOW_RACES_BOOKMAKER_SIDE"><span data-hover="<fmt:message key="menu_races"/>"><fmt:message key="menu_races"/></span></a></li>
                    <li><a href="controller?command=SHOW_HORSES_BOOKMAKER_SIDE"><span data-hover="<fmt:message key="menu_horses"/>"><fmt:message key="menu_horses"/></span></a></li>
                    <li><a href="controller?command=SHOW_BETS_HISTORY"><span data-hover="<fmt:message key="history"/>"><fmt:message key="history"/> </span></a></li>
                    <li><a href="controller?command=LOGOUT"><span data-hover="<fmt:message key="menu_logout"/>"><fmt:message key="menu_logout"/></span></a></li>
                </ul>
            </div>

        </div>
    </nav>
    <div class="banner">
        <img src="./bookmakerfiles/banner.jpg" alt="Corporatus">
    </div>

</div>
<div class="about" id="about">
    <div class="container">
        <div class="col-md-6 col-sm-6 about-grid">
            <h1><fmt:message key="about"/></h1>
            <p><fmt:message key="bookmaker_text"/></p>
        </div>
        <div class="clearfix"></div>

    </div>
</div>
<div class="who">
    <div class="container">
        <h3><fmt:message key="bets"/></h3>
        <c:set var="kek" value="${created}" scope="session"/>
        <c:choose>
        <c:when test="${kek == false}">
            There's no info
            <br />
        </c:when>
        <c:otherwise>
        <div class = "block">
            <form name="action" method="post" action="controller">
                <input type="hidden" name="command" value="quotationaction">
                <table>
                    <thead>
                    <tr>
                        <th><fmt:message key="quotationid"/></th>
                        <th><fmt:message key="raceid"/></th>
                        <th><fmt:message key="horseid"/></th>
                        <th><fmt:message key="description"/></th>
                        <th><fmt:message key="coefficient"/></th>
                        <th><fmt:message key="status"/></th>
                        <th><fmt:message key="delete"/></th>
                    </tr>
                    </thead>
                    <jsp:useBean id="list" scope="session" type="java.util.List"/>
                    <tbody>
                    <c:forEach var="post" items="${list}">
                        <tr><td>${post.userId}</td>
                            <td>${post.raceId}</td>
                            <td>${post.horseId}</td>
                            <td>${post.quotateDescription}</td>
                            <td>${post.coefficient}</td>
                            <c:choose>
                                <c:when test="${post.resultType.getId() == 0}">
                                    <td class="text-left">TBD</td>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${post.resultType.getId() == 1}">
                                    <td class="text-left">WIN</td>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${post.resultType.getId() == 2}">
                                    <td class="text-left">LOSE</td>
                                </c:when>
                            </c:choose>
                            <td><button name="delete" value="${post.id}" type="submit"><fmt:message key="delete"/></button></td></tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<div class="footer">
    <div class="container">


        <div class="copyright">
            <p>&copy; 2018 <fmt:message key="horseraces"/>.</p>
        </div>

    </div>
</div>


 <script type="text/javascript" src="./bookmakerfiles/jquery.min.js"></script>

 <script type="text/javascript" src="./bookmakerfiles/bootstrap.min.js"></script>


<script type="text/javascript" src="./bookmakerfiles/numscroller-1.0.js"></script>

<script src="./bookmakerfiles/modernizr.custom.97074.js"></script>
<script src="./bookmakerfiles/jquery.chocolat.js"></script>
<script type="text/javascript">
    $(function() {
        $('.gallery-grids a').Chocolat();
    });
</script>
<script type="text/javascript">
    $(document).ready(function() {
        var defaults = {
            containerID: 'toTop', // fading element id
            containerHoverID: 'toTopHover', // fading element hover id
            scrollSpeed: 100,
            easingType: 'linear'
        };
        $().UItoTop({ easingType: 'easeOutQuart' });
    });
</script>
<a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 0;"> </span></a>

<script type="text/javascript" src="./bookmakerfiles/move-top.js"></script>
<script type="text/javascript" src="./bookmakerfiles/easing.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function($) {
        $(".scroll, .navbar li a, .footer li a").click(function(event){
            $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
        });
    });
</script>
<script defer src="./bookmakerfiles/jquery.flexslider.js"></script>
<script type="text/javascript">
    $(function(){
        SyntaxHighlighter.all();
    });
    $(window).load(function(){
        $('.flexslider').flexslider({
            animation: "slide",
            start: function(slider){
                $('body').removeClass('loading');
            }
        });
    });
</script>

</body>
</html>
