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


<form method="post" action="${req.contextPath}/admin/movies/<c:out value="${cons.adminUpdateMovieUriSfx}"/>">
    <input type="hidden" name="<c:out value="${cons.formParamMovieId}"/>" value="<c:out value="${movie.id}" />"/>
    <table border="0" cellpadding="3" cellspacing="3" class="movie_update">
        <tr>
            <td>Title</td>
            <td><input type="text" name="<c:out value="${cons.formParamMovieTitle}"/>"
                       value="<c:out value="${movie.title}" />"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><textarea rows="3" cols="60" name="<c:out value="${cons.formParamMovieDescription}"/>"><c:out
                    value="${movie.description}"/></textarea></td>
        </tr>
        <tr>
            <td>Duration (sec)</td>
            <td><input type="number" name="<c:out value="${cons.formParamMovieDuration}"/>"
                       value="<c:out value="${movie.duration}" />"/></td>
        </tr>
    </table>
    <input type="submit" value="Add"/>
</form>

</body>
</html>
