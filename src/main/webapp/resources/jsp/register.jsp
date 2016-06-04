<%--
  Created by IntelliJ IDEA.
  User: dshvedchenko
  Date: 16.04.16
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
    <%@include file="/resources/jsp/stylesheets.jsp" %>
</head>
<body>
<%@include file="actionlist.jsp" %>
<hr/>
<div class="center_div">
    <form method="POST" action="${req.contextPath}/register" class="register_form" id="register_form">
        <div style="color:red">${errorMessage}</div>
        <table cellpadding="3" cellspacing="3" border="0">
            <tr>
                <td>Email</td>
                <td><input type="email" name="email" autofocus required/></td>
            </tr>
            <tr>
                <td>Username</td>
                <td><input type="text" name=login required/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" required/></td>
            </tr>
            <tr>
                <td>Password confirm</td>
                <td><input type="password" name="password_c" required/>
                    <div style="color:red">${passwordMismatch}</div>
                </td>
            </tr>
            <tr>
                <td>Birth date</td>
                <td><input type="date" name="birthDate" required/>
                    <div style="color:red">${birthDateMessage}</div>
                </td>
            </tr>

            <tr>
                <td>Firstname</td>
                <td><input type="text" name="firstName" required/></td>
            </tr>

            <tr>
                <td>Lastname</td>
                <td><input type="text" name="lastName" required/></td>
            </tr>

            <tr>
                <td><input type="submit" name="Register !"/></td>
                <td><a href="http://kronwerk.ua" target="_blank">Go real !</a></td>
            </tr>
        </table>


    </form>
</div>

</body>
</html>
