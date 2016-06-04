<%--
  Created by IntelliJ IDEA.
  User: dshvedchenko
  Date: 15.04.16
  Time: 0:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>

<html>
<head>
    <title>Login Form</title>
    <link rel="stylesheet" type="text/css" href="${req.contextPath}/resources/css/main.css"/>
</head>
<body>
<%@include file="/resources/jsp/actionlist.jsp" %>
<div class="center_div">
    <form method="POST" action="${req.contextPath}/login" class="login_form">
        <div style="color:red">${errorMessage}</div>
        <div style="color: yellow">default admin : admin</div>
        <table cellpadding="3" cellspacing="3" border="0">
            <tr>
                <td>Username</td>
                <td><input type="text" name="username" autofocus/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td><input type="submit" name="Login"/></td>
                <td><a href="${req.contextPath}/register">No account</a></td>
            </tr>
        </table>


    </form>
</div>
</body>
</html>
