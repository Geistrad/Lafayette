<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <title>Lafayette Server Registration</title>
        <meta name="description" content="">

        <%@ include file="WEB-INF/jspf/head.jspf" %>
    </head>
    <body>
        <%@ include file="WEB-INF/jspf/legacy_browser.jspf" %>

        <div id="container">
            <img src="img/logo.png" title="Lafayette Server" alt="Lafayette Server"/>

            <h1>Sign Up</h1>

            <div id="form">
                <form method="post" action="javascript:;">
                    <ul>
                        <li class="email">
                            <label>Email: </label><br/>
                            <input type="text" name="email" id="email" value="" />
                            <span class="error"></span>
                        </li>
                        <li class="username">
                            <label>Username: </label><br/>
                            <input type="text" name="username" id="username" value="" />
                            <span class="error"></span>
                        </li>
                        <li class="password">
                            <label>Password: </label><br/>
                            <input type="password" name="password" id="password" value="" />
                            <span class="error"></span>
                        </li>
                        <li class="submit">
                            <input type="submit" value=" Register " id='submit'/>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
        <%@ include file="WEB-INF/jspf/footer_scripts.jspf" %>
    </body>
</html>
