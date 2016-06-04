<!--
Created by IntelliJ IDEA.
User: dshvedchenko
Date: 13.04.16
Time: 6:31
To change this template use File | Settings | File Templates.
-->
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"
           prefix="fmt" %>


<html>

<head>
    <%@include file="/resources/jsp/stylesheets.jsp" %>
    <title>Server errrorrrrr</title>
</head>
<body bgcolor="white">

<p>
    Error occured : ${pageContext.errorData.throwable.cause}
</body>
</html>