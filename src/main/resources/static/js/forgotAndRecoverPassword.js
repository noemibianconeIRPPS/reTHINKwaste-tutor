$(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$("#submitRecoverButton").on("click", function() {

    $("#alertForgotPasswordNoEmail").addClass("d-none");
    $("#alertForgotPasswordSuccess").addClass("d-none");

    var accountEmail = $("#accountEmail").val();
    $("#submitRecoverButton").attr("disabled", "disabled");
    $("#submitRecoverButton").html('<div class="spinner-border text-light mt-1" role="status"><span class="sr-only">Loading...</span></div>');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: window.location.pathname + "/recoverAccount",
        data: {accountEmail:accountEmail},
        cache: false,
        timeout: 600000,
        success: function (data) {
            if(data.status === '0') {
                $("#alertForgotPasswordNoEmail").removeClass("d-none");
            }
            if(data.status === '1') {
                $("#alertForgotPasswordSuccess").removeClass("d-none");
                $("#submitRecoverButton").addClass("d-none")
            }
        },
        error: function (data) {
            console.log("error");
        }
    });

});

$("#submitResetPasswordButton").on("click", function(event) {

    $("#alertPasswordSuccess").addClass("d-none");
    $("#alertPasswordMismatch").addClass("d-none");

    $("#submitResetPasswordButton").attr("disabled", "disabled");
    $("#submitResetPasswordButton").html('<div class="spinner-border text-light mt-1" role="status"><span class="sr-only">Loading...</span></div>');

    var token = $("#resetToken").val();
    var passwordReset = $("#passwordReset").val();
    var confirmPasswordReset = $("#confirmPasswordReset").val();
    if(passwordReset !== confirmPasswordReset) {
        $("#alertPasswordMismatch").removeClass("d-none");
        $("#submitResetPasswordButton").removeAttr("disabled");
        $("#submitResetPasswordButton").html('RESET');
    }
    else {
        $.ajax({
            url: window.location.origin + "/forgotPassword/confirmReset",
            type: "GET",
            contentType: "application/json",
            data: {token: token, passwordReset: passwordReset},
            cache: false,
            timeout: 600000,
            success: function (data) {

                $("#alertPasswordSuccess").removeClass("d-none");
                $("#submitResetPasswordButton").addClass("d-none");
                $("#goHomeButton").removeClass("d-none");
            },
            error: function (data) {
                console.log("errore");

            }
        });
    }

});