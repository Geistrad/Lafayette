<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">
    <head>
        <title>Page Not Found :(</title>
        <%@include file="WEB-INF/jspf/error/head.jspf" %>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/error/container_begin.jspf" %>

            <h1>Not found <span>:(</span></h1>

            <p>Sorry, but the page you were trying to view does not exist.</p>
            <p>It looks like this was the result of either:</p>

            <ul>
                <li>a mistyped address</li>
                <li>an out-of-date link</li>
            </ul>

        <%@include file="WEB-INF/jspf/error/container_end.jspf" %>
    </body>
</html>