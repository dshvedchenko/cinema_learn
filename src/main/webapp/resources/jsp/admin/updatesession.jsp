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

<div class="center_div">
    <form method="post" action="${req.contextPath}/admin/sessions/<c:out value="${cons.adminUpdateSessionUriSfx}"/>">
        <input type="hidden" name="<c:out value="${cons.getFormParamSessionId}"/>"
               value="<c:out value="${session.id}" />"/>
        <table border="0" cellpadding="3" cellspacing="3" class="session_update">
            <tr>
                <td>Date</td>
                <td><input type="datetime-local" step="3600" required="required"
                           value="<c:out value="${session.sessionDateTime}"></c:out>"
                           name="<c:out value="${cons.formParamSessionDateTime}"/>"/></td>
            </tr>
            <tr>
                <td>Movie</td>
                <td><select name="<c:out value="${cons.formParamSessionMovieId}"/>">
                    <c:forEach items="${movies}" var="movie">
                        <option value="${movie.id}" ${session.movie.id == movie.id ? 'selected' : ''} >${movie.title}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>Hall</td>
                <td><select name="<c:out value="${cons.formParamSessionHallId}"/>">
                    <c:forEach items="${halls}" var="hall">
                        <option value="${hall.id}" ${session.hall.id == hall.id ? 'selected' : ''} >${hall.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
        </table>
        <input type="submit" value="Update"   ${sessionAction.active ? '' : 'disabled'}  />
    </form>
</div>
</body>
</html>
