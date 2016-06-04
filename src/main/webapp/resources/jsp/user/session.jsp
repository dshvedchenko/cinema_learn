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
    <div style="color:red">${errorMessage}</div>

    <table border="0" cellpadding="3" cellspacing="3">
        <tr>
            <th>Date</th>
            <th>Movie</th>
            <th>Hall</th>
        </tr>
        <tr>
            <td><c:out value="${session.sessionDateTime}"></c:out></td>
            <td><c:out value="${session.getMovie().title}"></c:out></td>
            <td><c:out value="${session.getHall().name}"/></td>
        </tr>
    </table>
    <hr/>

    <form action="${req.contextPath}/user/session/<c:out value="${cons.buyTicketOnSessionUriSfx}"/>" method="post">
        <input type="hidden" name="id" value="<c:out value="${session.getId()}" />"/>
        <table cellspacing="2" cellpadding="2" border="1" class="session_hall_places">
            <c:forEach var="row" begin="1" end="${session.getHall().getSeatRows()}">
                <tr>
                    <c:forEach var="seat" begin="1" end="${session.getHall().getSeatCols()}">
                        <c:if test="${!bookedPlaces[row-1][seat-1]}">
                            <td>
                                <input
                                        type="checkbox"
                                        name="<c:out value="${cons.buyTicketCheckbox}"/>"
                                        value="${row}:${seat}"
                                    <%--${bookedPlaces[row-1][seat-1] ? 'disabled style="backgroun-color: red"':'' } --%>
                                />
                            </td>
                        </c:if>
                        <c:if test="${  bookedPlaces[row-1][seat-1]}">
                            <td style="background-color: red">
                            </td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value=" Buy !!!!! "/>
    </form>
</div>
</body>
</html>
