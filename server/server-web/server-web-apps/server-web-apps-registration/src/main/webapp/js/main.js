
(function($, global) {

    /*
     * http://www.9lessons.info/2011/01/gravity-registration-form-with-jquery.html
     */
    $(function() {
        // Regular Expressions
        var ck_username = /^[A-Za-z0-9_]{3,20}$/,
                ck_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i,
                ck_password = /^[A-Za-z0-9!@#$%^&*()_]{8,20}$/,
                easeOptions = {duration: "slow", easing: "easeOutElastic"};

        // Displaying first list email field
        $("ul li:first").show();

        // Email validation
        $('#email').focus().keyup(function() {
            var email = $(this).val();

            if (!ck_email.test(email)) {
                $(this).next().show().html("Enter valid email");
            } else {
                $(this).next().hide();
                $("li").next("li.username").slideDown(easeOptions).focus();
            }
        });

        // Username validation
        $('#username').keyup(function() {
            var username = $(this).val();
            if (!ck_username.test(username)) {
                $(this).next().show().html("Min 3 characters no space");
            } else {
                $(this).next().hide();
                $("li").next("li.password").slideDown(easeOptions).focus();
            }
        });

        // Password validation
        $('#password').keyup(function() {
            var password = $(this).val();

            if (!ck_password.test(password)) {
                $(this).next().show().html("Min 8 characters");
            } else {
                $(this).next().hide();
                $("li").next("li.submit").slideDown(easeOptions);
            }
        });

        // Submit button action
        $('#submit').click(function(event) {
            var email = $("#email").val(),
                username = $("#username").val(),
                password = $("#password").val();

            event.preventDefault();
            event.stopPropagation();

            if (ck_email.test(email) && ck_username.test(username) && ck_password.test(password)) {
                $("#form").hide()
                        .html("<img src=\"img/ajax-loader.gif\" /> "
                        + "&#10070; &nbsp; &#10070; &nbsp; &#10070; &nbsp; register "
                        + "&nbsp; &#10070; &nbsp; &#10070; &nbsp; &#10070;")
                        .show();
                $.ajax({
                    url:"api/user",
                    cache: false,
                    contentType: "application/json",
                    dataType: "json",
                    processData: false,
                    data: JSON.stringify({
                        email: email,
                        username: username,
                        password: password
                    })})
                .done(function(data, textStatus, jqXHR) {
                    console.debug(data, textStatus, jqXHR);
                    $("#form").show().html("<p>Thank you!</p>");
                })
                .fail(function(jqXHR, textStatus, errorThrown) {
                    console.debug(jqXHR, textStatus, errorThrown);
                    $("#form").show().html("<p>Fehler!</p>");
                });
            } else {
                // TODO error message.
            }
        });
    });

})(jQuery, window);
