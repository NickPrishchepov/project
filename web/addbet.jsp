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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

     <link rel="stylesheet" href="./bookmakerfiles/bootstrap.min.css" type="text/css" media="all"/>
     <link rel="stylesheet" href="./bookmakerfiles/style.css" type="text/css" media="all" />
     <link rel="stylesheet" href="./bookmakerfiles/flexslider.css" type="text/css" media="all" />
     <link rel="stylesheet" href="./bookmakerfiles/chocolat.css" type="text/css" media="screen" />

</head>
<body>
<ul class="menu">

    <form action="addbet.jsp" name="form">
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
            <p><fmt:message key="addbet_text"/> </p>
        </div>
        <div class="clearfix"></div>

    </div>
</div>
<script type="text/javascript"><!--
//
function mChange3(obj){
    var el, s, n, v;
    el=obj.options;
    n=el.selectedIndex;//индекс выбранного оптиона
    v=el[n].value;
    s=obj.id+'_show';//получить ИД поля
    if(v=="place"){//только если выбран нужный оптион
        if(document.getElementById(s)){//существует ли такой элемент?
            document.getElementById(s).style.display="block";
        }};//показать
    if(v!="place"){//только если выбран нужный оптион
        if(document.getElementById(s)){//существует ли такой элемент?
            document.getElementById(s).style.display="none";
        }};//показать
};//
--></script>
<div class="contact" id="contact">
    <div class="container">

        <h3><fmt:message key="form"/></h3>
        <div class="heading-underline"></div>

        <form class="contact_form" method="post" action="controller">
            <input type="hidden" name="command" value="ADD_BET"/>
            <div class="message">
                <select name="description" id="description" onchange="mChange3(this);">
                    <option value="win">win</option>
                    <option value="first_three">first_three</option>
                    <option value="underdog">underdog</option>
                    <option value="place">place</option>
                </select>
            <input type="text" class="text" value="" name="description_show" placeholder="place" id="description_show">
                    <input type="number" class="text" name="coefficient" min="1.1" step="any" placeholder="<fmt:message key="coefficient"/>" required="" >
                <div class="clearfix"></div>

                <button type="submit" class="more_btn"><fmt:message key="submit"/></button>
            </div>
        </form>

    </div>
</div>
<div class="footer">
    <div class="container">

        <div class="copyright">
            <p>&copy; 2018 <fmt:message key="horseraces"/></p>
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
