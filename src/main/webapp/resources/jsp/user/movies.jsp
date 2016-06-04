<%--
  Created by IntelliJ IDEA.
  User: dshvedchenko
  Date: 16.04.16
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="cons" class="com.cinema.Constants" scope="session"/>
<html>
<head>
    <title>Title</title>
    <%@include file="/resources/jsp/stylesheets.jsp" %>
</head>
<body>
<%@include file="/resources/jsp/actionlist.jsp" %>
<div style="color:red">${errorMessage}</div>
<table border="0" cellpadding="3" cellspacing="3" class="movie_list">
    <tr>
        <th>Sessions (count)</th>
        <th>Title</th>
        <th>Description</th>
        <th>Duration (sec)</th>
        <th>Sessions count</th>
    </tr>
    <c:forEach items="${movies}" var="movie">
        <tr>
            <td>
                <a href="${req.contextPath}/user/sessions/<c:out value="${cons.userMovieSessionsUriSfx}"/>?id=<c:out value="${movie.getId()}"/>">
                    <c:out value="${movie.getSessionDTOList().size()}"></c:out> </a></td>
            <td><c:out value="${movie.getTitle()}"></c:out></td>
            <td><c:out value="${movie.getDescription()}"></c:out></td>
            <td><c:out value="${movie.getDuration()}"></c:out></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
