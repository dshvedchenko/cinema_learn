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
<form method="post" action="${req.contextPath}/admin/movies/<c:out value="${cons.adminDeleteMovieByIdUriSfx}"/>">
    <table border="0" cellpadding="3" cellspacing="3" class="movie_list">
        <tr>
            <th>id (edit)</th>
            <th>Title</th>
            <th>Description</th>
            <th>Duration (sec)</th>
            <th>Sessions count</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${movies}" var="movie">
            <tr>
                <td>
                    <a href="${req.contextPath}/admin/movies/<c:out value="${cons.adminUpdateMovieUriSfx}"/>?id=<c:out value="${movie.getId()}"/>"><c:out
                            value="${movie.getId()}"/></a></td>
                <td><c:out value="${movie.getTitle()}"></c:out></td>
                <td><c:out value="${movie.getDescription()}"></c:out></td>
                <td><c:out value="${movie.getDuration()}"></c:out></td>
                <td><c:out value="${movie.getSessionDTOList().size()}"></c:out></td>
                <td><input type="checkbox" name="<c:out value="${cons.adminDeleteMovieCheckboxGroup}"/>"
                           value='<c:out value="${movie.getId()}"/>'/></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Submit"/>
</form>

<form method="post" action="${req.contextPath}/admin/movies/<c:out value="${cons.adminAddMovieUriSfx}"/>">
    <table border="0" cellpadding="3" cellspacing="3" class="movie_add">
        <tr>
            <td>Title</td>
            <td><input type="text" name="<c:out value="${cons.formParamMovieTitle}"/>"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><input type="text" name="<c:out value="${cons.formParamMovieDescription}"/>"/></td>
        </tr>
        <tr>
            <td>Duration (sec)</td>
            <td><input type="number" name="<c:out value="${cons.formParamMovieDuration}"/>"/></td>
        </tr>
    </table>
    <input type="submit" value="Add"/>
</form>

</body>
</html>
