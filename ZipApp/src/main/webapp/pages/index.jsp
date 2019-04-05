<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
    <div align="center">
        <h1>Welcome, ${login}!</h1>

        <form enctype="multipart/form-data" action="/tozip" method="POST">
            File:<br/><input type="file" name="file"><br/>
            <input type="submit" value="ToZIP"/>
        </form>

        <c:url value="/logout" var="logoutUrl" />
        <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>

        <table border="1">
            <c:forEach items="${zips}" var="zip">
                <tr>
                    <td><c:out value="${zip.pathZipFile}"/></td>
                </tr>
            </c:forEach>
        </table>

    </div>
</body>
</html>
