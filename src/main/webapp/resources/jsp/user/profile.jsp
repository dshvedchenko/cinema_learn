<%--
  Created by IntelliJ IDEA.
  User: dshvedchenko
  Date: 16.04.16
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="cons" class="com.cinema.Constants" scope="session"/>
<html>
<head>
    <title>User Registration</title>
    <%@include file="/resources/jsp/stylesheets.jsp" %>
</head>
<body>
<%@include file="/resources/jsp/actionlist.jsp" %>
<div style="color:red">${errorMessage}</div>
<hr/>
<div class="center_div">
    <table cellpadding="3" cellspacing="3" border="0">
        <tr>
            <td>Email</td>
            <td><c:out value="${user.getEmail()}"/></td>
        </tr>
        <tr>
            <td>Login</td>
            <td><c:out value="${user.getLogin()}"/></td>
        </tr>
        <tr>
            <td>Birth date</td>
            <td><c:out value="${user.getBirthDate()}"/>
            </td>
        </tr>

        <tr>
            <td>Firstname</td>
            <td><c:out value="${user.getFirstName()}"/></td>
        </tr>

        <tr>
            <td>Lastname</td>
            <td><c:out value="${user.getLastName()}"/></td>
        </tr>

    </table>

    <hr/>
    List of non-expired user tickets
    <form action="${req.contextPath}/user/profile/<c:out value="${cons.userReturnTicketsUriSfx}"/>" method="post">
        <table cellpadding="3" cellspacing="3" class="session_list">
            <tr>
                <th>Session Date</th>
                <th>Movie</th>
                <th>Row</th>
                <th>Seat</th>
                <th>Return</th>
            </tr>
            <c:forEach items="${user.getTickets()}" var="ticket">
                <tr>
                    <td><c:out value="${ticket.getSession().getSessionDateTime()}"/></td>
                    <td><c:out value="${ticket.getSession().getMovie().getTitle()}"/></td>
                    <td><c:out value="${ticket.getRow()}"/></td>
                    <td><c:out value="${ticket.getSeat()}"/></td>
                    <td><input type="checkbox" name="<c:out value="${cons.userReturnTicketsCheckbox}"/>"
                               value='<c:out value="${ticket.getId()}"/>'/></td>
                </tr>

                <br/>
            </c:forEach>
            <input type="submit" value="Return selected tickets"/>
        </table>
    </form>

</div>

</body>
</html>
