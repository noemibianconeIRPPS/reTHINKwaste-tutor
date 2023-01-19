$(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    if ($("#answer_35").is(':checked') || $("#answer_36").is(':checked')) {
        $("#question2Category2").removeClass("d-none");
    }
    if ($("#answer_38").is(':checked') || $("#answer_39").is(':checked')) {
        $("#questionChild_21").removeClass("d-none");
    }
    if ($("#answer_41").is(':checked') || $("#answer_42").is(':checked')) {
        $("#questionChild_22").removeClass("d-none");
    }
    if ($("#answer_44").is(':checked') || $("#answer_45").is(':checked')) {
        $("#questionChild_23").removeClass("d-none");
    }
    if ($("#answer_47").is(':checked') || $("#answer_48").is(':checked')) {
        $("#questionChild_24").removeClass("d-none");
    }
    if ($("#answer_50").is(':checked') || $("#answer_51").is(':checked')) {
        $("#questionChild_25").removeClass("d-none");
    }
    if ($("#answer_69").is(':checked')) {
        $("#question2Category3").removeClass("d-none");
    }

    checkquestion3category2label();
});

$("#ttrSteps").steps({
    headerTag: "h3",
    bodyTag: "section",
    labels: {
        finish: "<i class=\"fas fa-arrow-right\"></i>",
        next: "<i class=\"fas fa-arrow-right\"></i>",
        previous: "<i class=\"fas fa-arrow-left\"></i>",
    },
    transitionEffect: "slideLeft",
    autoFocus: true,
    onStepChanging: function (event, currentIndex, newIndex) {
        let result;
        $.when(onStepChanging()).done(function(response){
            console.log(response);
            result = response;
            $(window).scrollTop(0);
        });
        return result;
    },
    onFinished: function (event, currentIndex) {
        $.when(onStepChanging()).done(function(response){
            window.location.href = '/registeredarea/profile';
        });
    }
});


window.document.onkeydown = function(e) {
    if (!e) {
        e = event;
    }
    if (e.keyCode == 27) {
        lightbox_close();
    }
}

function lightbox_open(id, number) {
    var lightBoxVideo = document.getElementById(id);
    window.scrollTo(0, 0);
    $('#'+id).removeClass("d-none");
    document.getElementById('light'+number).style.display = 'block';
    document.getElementById('fade'+number).style.display = 'block';
    lightBoxVideo.play();
}

function lightbox_close(id, number) {
    var lightBoxVideo = document.getElementById(id);
    $('#'+id).addClass("d-none");
    document.getElementById('light'+number).style.display = 'none';
    document.getElementById('fade'+number).style.display = 'none';
    lightBoxVideo.pause();
}

function onStepChanging() {
    let result = false;
    let infoArray = new Object();
    let array = [];
    let categoryId = $('.body.current').find(".col-10").attr("id");
    $('.body.current').find(".form-group").find('.white-background').each(function () {
        let spanId = $(this).find("span").attr("id");
        let radioName = 'question_' + spanId.split("_")[1];
        let radioId = $(this).find("input[type='radio'][name='" + radioName + "']:checked").attr("id");
        if (radioId !== undefined) {
            let questionId = spanId.split("_")[1];
            let answerId = radioId.split("_")[1];
            let sendInfo = {
                questionId: questionId,
                answerId: answerId
            };
            array.push(sendInfo);
        }
    });
    infoArray.categoryId = categoryId;
    infoArray.infos = array;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: window.location.pathname + "/saveStep",
        data: JSON.stringify(infoArray),
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            console.log(data);
            result = true;
        },
        error: function (e) {
            result = false;
        }
    });
    return result;
}

step_height_adjustment();

$(window).resize(function() {
    console.log("resize");
    step_height_adjustment();
});

function step_height_adjustment(){
    var step_max_height=0;
    $("[id^='ttrSteps-t-']").css("min-height","auto");
    $("[id^='ttrSteps-t-']").each(function() {
        if($(this).innerHeight()>step_max_height){step_max_height=$(this).innerHeight();}
    });
    $("[id^='ttrSteps-t-']").each(function() {
        $(this).css("min-height",step_max_height+"px")
    });
}

$(document).delegate("#answer_35", "click", function() {
    $("#question2Category2").removeClass("d-none");
});

$(document).delegate("#answer_36", "click", function() {
    $("#question2Category2").removeClass("d-none");
});

$(document).delegate("#answer_34", "click", function() {
    if(!$("#question2Category2").hasClass("d-none")) {
        $("#question2Category2").addClass("d-none");
        $("#question2Answers").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
        $("#question3Category2").addClass("d-none");
        $("#question3Answers").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
});

$(document).delegate("#answer_38", "click", function() {
    $("#questionChild_21").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_39", "click", function() {
    $("#questionChild_21").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

function checkquestion3category2label() {
    if($("#questionChild_25").hasClass("d-none") && $("#questionChild_24").hasClass("d-none") &&
        $("#questionChild_23").hasClass("d-none") && $("#questionChild_22").hasClass("d-none") &&
        $("#questionChild_21").hasClass("d-none")) {
        $("#question3Category2").addClass("d-none");
    }
    else {
        $("#question3Category2").removeClass("d-none");
    }
}

$(document).delegate("#answer_37", "click", function() {
    $("#questionChild_21").addClass("d-none");
    $("#questionChild_21").children('.white-background').each(function () {
        $(this).find("input[type='radio']:checked").prop("checked", false);
    });
    checkquestion3category2label();
});

$(document).delegate("#answer_41", "click", function() {
    $("#questionChild_22").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_42", "click", function() {
    $("#questionChild_22").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_40", "click", function() {
    $("#questionChild_22").addClass("d-none");
    $("#questionChild_22").children('.white-background').each(function () {
        $(this).find("input[type='radio']:checked").prop("checked", false);
    });
    checkquestion3category2label();
});

$(document).delegate("#answer_44", "click", function() {
    $("#questionChild_23").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_45", "click", function() {
    $("#questionChild_23").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_43", "click", function() {
    $("#questionChild_23").addClass("d-none");
    $("#questionChild_23").children('.white-background').each(function () {
        $(this).find("input[type='radio']:checked").prop("checked", false);
    });
    checkquestion3category2label();
});

$(document).delegate("#answer_47", "click", function() {
    $("#questionChild_24").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_48", "click", function() {
    $("#questionChild_24").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_46", "click", function() {
    $("#questionChild_24").addClass("d-none");
    $("#questionChild_24").children('.white-background').each(function () {
        $(this).find("input[type='radio']:checked").prop("checked", false);
    });
    checkquestion3category2label();
});

$(document).delegate("#answer_50", "click", function() {
    $("#questionChild_25").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_51", "click", function() {
    $("#questionChild_25").removeClass("d-none");
    $("#question3Category2").removeClass("d-none");
});

$(document).delegate("#answer_49", "click", function() {
    $("#questionChild_25").addClass("d-none");
    $("#questionChild_25").children('.white-background').each(function () {
        $(this).find("input[type='radio']:checked").prop("checked", false);
    });
    checkquestion3category2label();
});

$(document).delegate("#answer_69", "click", function() {
    $("#question2Category3").removeClass("d-none");
});

$(document).delegate("#answer_68", "click", function() {
    if(!$("#question2Category3").hasClass("d-none")) {
        $("#question2Category3").addClass("d-none");
        $("#question3Answers").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
});

$(document).delegate("#answer_67", "click", function() {
    if(!$("#question2Category3").hasClass("d-none")) {
        $("#question2Category3").addClass("d-none");
        $("#question3Answers").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
});