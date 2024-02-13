<%@ page import="lab2.beans.RecordBean" %>
<%@ page import="lab2.beans.RequestsHistoryBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab2</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script src="js/form.js"></script>
</head>
<body>
    <table class="main">
        <tr>
            <td>
                <table class="results-table">
                    <tr>
                        <td>X</td>
                        <td>Y</td>
                        <td>R</td>
                        <td>RESULT</td>
                        <td>TIME</td>
                    </tr>
                    <jsp:useBean id="history" class="lab2.beans.RequestsHistoryBean" scope="session"/>

                    <c:forEach items="${history.records}" var="record">
                        <tr class="<c:choose><c:when test="${record.result}">succes</c:when><c:otherwise>fail</c:otherwise></c:choose>">
                            <td>${record.x}</td>
                            <td>${record.y}</td>
                            <td>${record.r}</td>
                            <td>${record.result}</td>
                            <td>${record.time}</td>
                        </tr>
                    </c:forEach>


                </table>
                <button class="clear-button" onclick="clearTable()">Clear Table</button>
                <form method="GET" action="main" class="back-form">
                    <input type="submit" value="Back" class="back-button"/>
                </form>
            </td>
        </tr>
    </table>
</body>
</html>