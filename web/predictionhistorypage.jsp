<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>Horse Races</title>
    <link href="./predictionfiles/bootstrap.css" rel="stylesheet" type="text/css" media="all">
   <script src="./predictionfiles/jquery-1.11.0.min.js"></script>

    <link href="./predictionfiles/style.css" rel="stylesheet" type="text/css" media="all"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script type="text/javascript" src="./predictionfiles/move-top.js"></script>
    <script type="text/javascript" src="./predictionfiles/easing.js"></script>
    <script type="text/javascript">
        jQuery(document).ready(function ($) {
            $(".scroll").click(function (event) {
                event.preventDefault();
                $('html,body').animate({scrollTop: $(this.hash).offset().top}, 1000);
            });
        });
    </script>
    <link rel="stylesheet" type="text/css" href="./predictionfiles/style_common.css"/>
    <link rel="stylesheet" type="text/css" href="./predictionfiles/style4.css"/>
</head>
<body>
<ul class="menu">

    <form action="predictionhistorypage.jsp" name="form">
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

<div class="header">
    <div class="container">
        <div class="header-main">
            <div class="header-left">
                <div class="logo">
                    <h1><a href="index.html"><fmt:message key="horseraces"/> </a></h1>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="top-navg">
                <span class="menu"> <img src="./predictionfiles/icon.png" alt=""/></span>
                <nav class="cl-effect-16">
                    <ul class="res">
                        <li><a data-hover="<fmt:message key="addprediction"/>" href="controller?command=SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION"><fmt:message key="addprediction"/> </a></li>
                        <li><a data-hover="<fmt:message key="menu_races"/>" href="controller?command=SHOW_RACES_USER_SIDE"><fmt:message key="menu_races"/> </a></li>
                        <li><a data-hover="<fmt:message key="menu_horses"/>" href="controller?command=SHOW_HORSES_USER_SIDE"><fmt:message key="menu_horses"/> </a></li>
                        <li><a data-hover="<fmt:message key="history"/>" href="controller?command=SHOW_PREDICTIONS_HISTORY"><fmt:message key="history"/></a></li>
                        <li><a data-hover="<fmt:message key="menu_logout"/>" href="controller?command=LOGOUT"><fmt:message key="menu_logout"/> </a></li>
                    </ul>
                    <script>
                        $("span.menu").click(function () {
                            $("ul.res").slideToggle(300, function () {
                            });
                        });
                    </script>

                </nav>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="banner">
    <div class="container">
        <div class="banner-main">
            <h3><fmt:message key="user_header"/></h3>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="about" id="about">
    <div class="container">
        <div class="abou-main">
            <div class="about-top">
                <h2><fmt:message key="about"/></h2>
                <p><fmt:message key="predictionpage"/></p>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="advantages">
    <div class="container">
        <div class="advantages-main">
            <h3><fmt:message key="mypredictions"/></h3>
            <c:set var="kek" value="${created}" scope="session"/>
            <c:choose>
                <c:when test="${kek == false}">
                    There's no info
                    <br/>
                </c:when>
                <c:otherwise>
                    <div class="pred_block">
                        <form name="action" method="post" action="controller">
                            <input type="hidden" name="command" value="PREDICTION_ACTION">
                            <table class="table-fill">
                                <thead>
                                <tr>
                                    <th class="text-left"><fmt:message key="predictionid"/></th>
                                    <th class="text-left"><fmt:message key="userid"/></th>
                                    <th class="text-left"><fmt:message key="quotationid"/></th>
                                    <th class="text-left"><fmt:message key="money"/></th>
                                    <th class="text-left"><fmt:message key="status"/></th>
                                    <th class="text-left"><fmt:message key="delete"/></th>
                                </tr>
                                </thead>
                                <jsp:useBean id="list" scope="session" type="java.util.List"/>
                                <tbody>
                                <c:forEach var="post" items="${list}">
                                    <tr>
                                        <td class="text-left">${post.id}</td>
                                        <td class="text-left">${post.userId}</td>
                                        <td class="text-left">${post.quotationId}</td>
                                        <td class="text-left">${post.money}</td>
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
                                        <td class="text-left">
                                            <button name="delete" value="${post.id}" type="submit"><fmt:message
                                                    key="delete"/></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="footer">
    <div class="copy-right">
        <p><fmt:message key="horseraces"/> 2018 </p>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            /*
            var defaults = {
                  containerID: 'toTop', // fading element id
                containerHoverID: 'toTopHover', // fading element hover id
                scrollSpeed: 1200,
                easingType: 'linear'
             };
            */

            $().UItoTop({easingType: 'easeOutQuart'});

        });
    </script>
    <a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>

</div>
</body>
</html>
