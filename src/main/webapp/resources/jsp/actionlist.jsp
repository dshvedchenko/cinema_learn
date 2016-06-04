<%--
  Created by IntelliJ IDEA.
  User: dshvedchenko
  Date: 16.04.16
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
    <li><a href="${pageContext.request.contextPath}/login"> Login </a></li>
    <li><a href="${pageContext.request.contextPath}/logout"> Logout </a></li>
    <li><a href="${pageContext.request.contextPath}/user/profile"> User Action </a></li>
    <li><a href="${pageContext.request.contextPath}/user/movies"> Movie List </a></li>
    <li><a href="${pageContext.request.contextPath}/user/sessions"> Session List </a></li>
</ul>
<c:if test="${sessionScope.isAdmin}">
    <p> Admin Tasks</p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/movies"> Admin Movies </a></li>
        <li><a href="${pageContext.request.contextPath}/admin/users"> Admin Users </a></li>
        <li><a href="${pageContext.request.contextPath}/admin/sessions"> Cinema Sessions </a></li>
    </ul>
</c:if>
