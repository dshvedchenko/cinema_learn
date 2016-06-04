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
<form method="post" action="${req.contextPath}/admin/users/<c:out value="${cons.adminDeleteMovieByIdUriSfx}"/>">
    User list
    <table border="0" cellpadding="3" cellspacing="3" class="user_list">
        <tr>
            <th>id (edit)</th>
            <th>Login</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Date of birth</th>
            <th>Role</th>
            <th>Email</th>
            <th>Tickets count.</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <a href="${req.contextPath}/admin/users/<c:out value="${cons.adminUpdateUserUriSfx}"/>?id=<c:out value="${user.getId()}"/>"><c:out
                            value="${user.getId()}"/></a></td>
                <td><c:out value="${user.getLogin()}"></c:out></td>
                <td><c:out value="${user.getFirstName()}"></c:out></td>
                <td><c:out value="${user.getLastName()}"></c:out></td>
                <td><c:out value="${user.getBirthDate()}"></c:out></td>
                <td><c:out value="${user.getRole()}"></c:out></td>
                <td><c:out value="${user.getEmail()}"></c:out></td>
                <td>
                    <a href="${req.contextPath}/admin/tickets/<c:out value="${cons.adminListTicketsByUserId}"/>?id=<c:out value="${user.getId()}"/>">
                        <c:out value="${user.getTickets().size()}"></c:out>
                    </a>
                </td>
                <td><input type="checkbox" name="<c:out value="${cons.adminDeleteUserCheckboxGroup}"/>"
                           value='<c:out value="${user.getId()}"/>'/></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Submit"/>
</form>

<form method="post" action="${req.contextPath}/admin/users/<c:out value="${cons.adminAddUserUriSfx}"/>">
    <table border="0" cellpadding="3" cellspacing="3" class="user_update">
        <tr>
            <td>Login</td>
            <td><input type="text" required="required" name="<c:out value="${cons.formParamUserLogin}"/>"/></td>
        </tr>
        <tr>
            <td>Passowrd</td>
            <td><input type="password" name="<c:out value="${cons.formParamUserPassword}"/>"
            /></td>
        </tr>
        <tr>
            <td>Firstname</td>
            <td><input type="text" name="<c:out value="${cons.formParamUserFirstname}"/>"
            /></td>
        </tr>

        <tr>
            <td>Lastname</td>
            <td><input type="text" name="<c:out value="${cons.formParamUserLastname}"/>"
            /></td>
        </tr>
        <tr>
            <td>Role</td>
            <td><select name="<c:out value="${cons.formParamUserRole}"/>">
                <c:forEach items="${userRoles}" var="userRole">
                    <option value="${userRole}">${userRole}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td>Birthdate</td>
            <td><input type="date" name="<c:out value="${cons.formParamUserBirthdate}"/>"
            /></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="email" name="<c:out value="${cons.formParamUserEmail}"/>"
            /></td>
        </tr>
    </table>
    <input type="submit" value="Add"/>
</form>

</body>
</html>
