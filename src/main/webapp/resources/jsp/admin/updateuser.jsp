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
    <form method="post" action="${req.contextPath}/admin/users/<c:out value="${cons.adminUpdateUserUriSfx}"/>">
        <input type="hidden" name="<c:out value="${cons.formParamUserId}"/>" value="<c:out value="${user.id}" />"/>
        <table border="0" cellpadding="3" cellspacing="3" class="user_update">
            <tr>
                <td>Login</td>
                <td><c:out value="${user.login}"/></td>
            </tr>
            <tr>
                <td>Passowrd</td>
                <td><input type="password" name="<c:out value="${cons.formParamUserPassword}"/>"
                           value="<c:out value="${user.password}" />"/></td>
            </tr>
            <tr>
                <td>Firstname</td>
                <td><input type="text" name="<c:out value="${cons.formParamUserFirstname}"/>"
                           value="<c:out value="${user.firstName}" />"/></td>
            </tr>

            <tr>
                <td>Lastname</td>
                <td><input type="text" name="<c:out value="${cons.formParamUserLastname}"/>"
                           value="<c:out value="${user.lastName}" />"/></td>
            </tr>
            <tr>
                <td>Role</td>
                <td>
                    <select name="<c:out value="${cons.formParamUserRole}"/>">
                        <c:forEach items="${userRoles}" var="userRole">
                            <option value="${userRole}" ${user.role == userRole ? 'selected': ''} >${userRole}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Birthdate</td>
                <td><input type="date" name="<c:out value="${cons.formParamUserBirthdate}"/>"
                           value="<c:out value="${user.birthDate}" />"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="email" name="<c:out value="${cons.formParamUserEmail}"/>"
                           value="<c:out value="${user.email}" />"/></td>
            </tr>
        </table>
        <input type="submit" value="Update"/>
    </form>
</div>
</body>
</html>
