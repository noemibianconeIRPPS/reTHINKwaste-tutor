var timeOut = 0;

$('#password-icon').on('mousedown touchstart', function(e) {
    let passwordIcon = $(this);
    passwordIcon.html('<i class="fas fa-eye"></i>');
    $("#password").attr('type', 'text');
    timeOut = setInterval(function(){
    }, 100);
}).bind('mouseup mouseleave touchend', function() {
    let parent = $(this);
    parent.html('<i class="fas fa-eye-slash"></i>');
    $("#password").attr('type', 'password');
    clearInterval(timeOut);
});


$(document).delegate('#change-password-icon', 'click', function(e) {
    if ($(".changePassword").attr("class").indexOf("d-none") >= 0) {
        $(".changePassword").removeClass("d-none");
    } else {
        $(".changePassword").addClass("d-none");
    }
});

function getPlacesContaining(request, response) {
    var places = $.get(window.location.href+'/getSuggestionsForPlaces', {keyword: request.term}, 'json');
    places.done(function(data) {
        response(JSON.parse(data));
    });
}

$(function() {

    $( "#places-autocomplete" ).autocomplete({
        source: getPlacesContaining,
        focus: function( event, ui ) {
            $( "#places-autocomplete" ).val( ui.item.label );
            return false;
        },
        select: function( event, ui ) {
            $( "#places-autocomplete" ).text( ui.item.label );
            $( "#places-autocomplete" ).val( ui.item.label );
            return false;
        }
    }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
        var newText = String(item.label).replace(
            new RegExp(this.term, "gi"),
            "<span class='ui-state-highlight'>$&</span>");

        return $("<li id='"+item.value+"'></li>")
            .data("item.autocomplete", item)
            .append(newText)
            .appendTo(ul);
    };

    $('#password'). keypress(function(event) {
        event. preventDefault();
        return false;
    });

    $('#organizationType').on('change', function() {
        console.log("DASHSDUSDH " + this.value);
        if(parseInt(this.value, 10) === 7) {
            $("#otherOrganizationTypeFormGroup").removeClass("d-none");
        }
        else {
            $("#otherOrganizationTypeFormGroup").addClass("d-none");
            $("#otherOrganizationType").val('');
        }
    });
});



function validURL(str) {
    var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
        '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
    return !!pattern.test(str);
}

$(document).delegate('#saveProfileChanges', 'click', function(e) {

    $(".passwordMismatch").addClass("d-none");
    $(".organizationOtherEmpty").addClass("d-none");
    $(".fillAllTheField").addClass("d-none");
    $(".invalidWebsite").addClass("d-none");

    var newPassword = '', confirmNewPassword = '';

    if ($(".changePassword").attr("class").indexOf("d-none") === -1) {
        newPassword = $("#newPassword").val();
        confirmNewPassword = $("#confirmNewPassword").val();
        console.log("PASSWORDS " + newPassword + " " + confirmNewPassword);
    }
    if(newPassword !== confirmNewPassword) {
        $(".passwordMismatch").removeClass("d-none");
        return false;
    }
    if(!validURL($("#website").val())) {
        $(".invalidWebsite").removeClass("d-none");
        return false;
    }
    else {
        var firstname, lastname, organizationName, organizationType, otherOrganizationType, website, country;
        firstname = $("#firstname").val();
        lastname = $("#lastname").val();
        organizationName = $("#organizationName").val();
        organizationType = $("#organizationType").val();
        otherOrganizationType = '';
        website = $("#website").val();
        country = $("#places-autocomplete").val();

        if(organizationType === '7') {
            otherOrganizationType = $("#otherOrganizationType").val()
        }
        if(organizationType === '7' && otherOrganizationType.length === 0) {
            $(".organizationOtherEmpty").removeClass("d-none");
        }
        else {
            if(firstname.length === 0 || lastname.length === 0 ||  organizationName.length === 0 ||
                website.length === 0 || country.length === 0) {
                $(".fillAllTheField").removeClass("d-none");
            }
            else {
                $.ajax({
                    type: "GET",
                    url: window.location.pathname + "/saveChanges",
                    traditional: true,
                    data: {firstname: firstname, lastname: lastname,
                        organizationName: organizationName,
                        organizationType: organizationType,
                        otherOrganizationType: otherOrganizationType,
                        website: website,
                        country: country,
                        newPassword: newPassword},
                    cache: false,
                    timeout: 600000,
                    success: function (data) {
                        window.location.href = '/registeredarea/profile'
                    },
                    error: function (e) {
                    }
                });
            }
        }
    }
});

$(document).ready(function() {
    $('#organizationType option').each(function() {
        var optionSelected = $('#organizationType option').filter(':selected').val();
        if (parseInt(optionSelected, 10) === 7) {
            $("#otherOrganizationTypeFormGroup").removeClass("d-none");
        }
    });
});