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
<form method="post" action="${req.contextPath}/admin/sessions/<c:out value="${cons.adminDeleteSessionByIdUriSfx}"/>">
    User list
    <table border="0" cellpadding="3" cellspacing="3" class="session_list">
        <tr>
            <th>id (edit)</th>
            <th>Date</th>
            <th>Movie</th>
            <th>Hall</th>
            <th>Tickets sold</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${sessions}" var="session">
            <tr>
                <td>
                    <a href="${req.contextPath}/admin/sessions/<c:out value="${cons.adminUpdateSessionUriSfx}"/>?id=<c:out value="${session.getId()}"/>"><c:out
                            value="${session.getId()}"/></a></td>
                <td><c:out value="${session.sessionDateTime}"></c:out></td>
                <td><c:out value="${session.getMovie().title}"></c:out></td>
                <td><c:out value="${session.getHall().name}"/> has <c:out value="${session.getHall().seatRows}"/> rows
                    with <c:out value="${session.getHall().seatCols}"/> seats
                </td>
                <td><c:out value="${session.getTickets().size()}"></c:out></td>
                <td><input
                        type="checkbox" ${sessionActions.get(session.id).removable ? '':'disabled title="there is tickets sold"' }
                        name="<c:out value="${cons.adminDeleteSessionCheckboxGroup}"/>"
                        value='<c:out value="${session.getId()}"/>'/></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Submit"/>
</form>

<form method="post" action="${req.contextPath}/admin/sessions/<c:out value="${cons.adminAddSessionUriSfx}"/>">
    <table border="0" cellpadding="3" cellspacing="3" class="session_update">
        <tr>
            <td>Date</td>
            <td><input type="datetime-local" step="3600" required="required"
                       name="<c:out value="${cons.formParamSessionDateTime}"/>"/></td>
        </tr>
        <tr>
            <td>Movie</td>
            <td><select name="<c:out value="${cons.formParamSessionMovieId}"/>">
                <c:forEach items="${movies}" var="movie">
                    <option value="${movie.id}">${movie.title}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td>Hall</td>
            <td><select name="<c:out value="${cons.formParamSessionHallId}"/>">
                <c:forEach items="${halls}" var="hall">
                    <option value="${hall.id}">${hall.name}</option>
                </c:forEach>
            </select></td>
        </tr>
    </table>
    <input type="submit" value="Add"/>
</form>

</body>
</html>
