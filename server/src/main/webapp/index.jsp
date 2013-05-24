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

        <h1>Lafayette Server</h1>

        <p>Hello World!</p>

        <small>
            Version: ${applicationScope['registry'].version} &ndash;
            Stage: ${applicationScope['registry'].stage}
            <c:if test="${applicationScope.registry.stage=='DEVELOPMENT'}">
                 &ndash; <a href="http://${pageContext.request.serverName}:8080/">Jenkins CI</a>
            </c:if>
        </small>
        <%@ include file="WEB-INF/jspf/footer_scripts.jspf" %>
    </body>
</html>
