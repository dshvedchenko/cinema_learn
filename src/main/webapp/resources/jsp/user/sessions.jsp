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

<table border="0" cellpadding="3" cellspacing="3" class="session_list">
    <tr>
        <th>Purchase</th>
        <th>Date</th>
        <th>Movie</th>
        <th>Hall</th>
        <th>Tickets sold</th>
    </tr>
    <c:forEach items="${sessions}" var="session">
        <tr>
            <td>
                <a href="${req.contextPath}/user/session/<c:out value="${cons.userSessionUriSfx}"/>?id=<c:out value="${session.getId()}"/>">
                        ${sessionActions.get(session.id).active ? 'buy ticket': '' } </a></td>
            <td><c:out value="${session.sessionDateTime}"></c:out></td>
            <td><c:out value="${session.getMovie().title}"></c:out></td>
            <td><c:out value="${session.getHall().name}"/> has <c:out value="${session.getHall().seatRows}"/> rows
                with <c:out value="${session.getHall().seatCols}"/> seats
            </td>
            <td><c:out value="${session.getTickets().size()}"></c:out></td>

        </tr>
    </c:forEach>
</table>

</body>
</html>
