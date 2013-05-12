<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">
    <head>
        <title>Page Not Found :(</title>
        <%@include file="/error/includes/head.jsp" %>
    </head>
    <body>
        <%@include file="/error/includes/container_end.jsp" %>

            <h1>Not found <span>:(</span></h1>

            <p>Sorry, but the page you were trying to view does not exist.</p>
            <p>It looks like this was the result of either:</p>

            <ul>
                <li>a mistyped address</li>
                <li>an out-of-date link</li>
            </ul>

        <%@include file="/error/includes/container_end.jsp" %>
    </body>
</html>