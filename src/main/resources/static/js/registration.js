window.onload = function() {
    loadMap('map');
};

$(document).ready(function() {

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
    $('#confirm-password-icon').on('mousedown touchstart', function(e) {
        let passwordIcon = $(this);
        passwordIcon.html('<i class="fas fa-eye"></i>');
        $("#confirmPassword").attr('type', 'text');
        timeOut = setInterval(function(){
        }, 100);
    }).bind('mouseup mouseleave touchend', function() {
        let parent = $(this);
        parent.html('<i class="fas fa-eye-slash"></i>');
        $("#confirmPassword").attr('type', 'password');
        clearInterval(timeOut);
    });
});

$('#organizationType').on('change', function() {
    if(this.value === '7') {
        $("#otherOrganizationTypeFormGroup").removeClass("d-none");
    }
    else {
        $("#otherOrganizationTypeFormGroup").addClass("d-none");
        $("#otherOrganizationType").val('');
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
});