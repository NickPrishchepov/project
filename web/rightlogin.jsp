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
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/userfiles/logincs.css">
</head>
<body>
<script type="text/javascript">
    <c:choose>
    <c:when test="${isFailedRegister == true}">
    if (alert("<fmt:message key="message.registrationerror"/> ")) {
        ${isFailedRegister = false}
    }
    </c:when>
    <c:when test="${isFailedLogin == true}">
    if (alert("<fmt:message key="message.loginerror"/> ")) {
        ${isFailedLogin = false}
    }
    </c:when>
    </c:choose>
</script>
<ul class="menu">
    <form action="rightlogin.jsp" name="form">
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
<div class="six"><h1><span><fmt:message key="span"/></span></h1></div>
<form id="login" name="loginform" method="POST" action="controller">
    <input type="hidden" name="command" value="logreg"/>
    <h1><fmt:message key="h1"/></h1>
    <fieldset id="inputs">
        <input id="username" type="text" placeholder="<fmt:message key="login"/> " name="login" value="" autofocus required>
        <input id="password" type="password" placeholder="<fmt:message key="password"/> "  name="password" value="" required>
        <input id="email" type="text" placeholder="<fmt:message key="email"/> " name="e-mail" value="" autofocus required>
    </fieldset>
    <fieldset id="actions">
        <button type="submit" class="submit" id="first_position" name="action" value="sign_in"><fmt:message key="signin"/></button>
        <button type="submit" class="submit" id="second_position" name="action" value="register"><fmt:message key="register"/> </button>
    </fieldset>
    <br/>
    ${errorLoginPassMessage}
</form>
</body>
</html>
