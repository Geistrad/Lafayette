<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <title>Lafayette Server</title>
        <meta name="description" content="">

        <%@ include file="WEB-INF/jspf/head.jspf" %>
    </head>
    <body>
        <%@ include file="WEB-INF/jspf/legacy_browser.jspf" %>

        <div id="container">
            <img src="img/logo.png" title="Lafayette Server" alt="Lafayette Server"/>

            <p>
                <a href="registration/">Sign Up</a> |
                <a href="api/">REST API</a> |
                <a href="administration/">Administration</a>
            </p>

        </div>
        <%@ include file="WEB-INF/jspf/footer_scripts.jspf" %>
    </body>
</html>
