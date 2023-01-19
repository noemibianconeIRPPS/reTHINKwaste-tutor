$(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    if ($("#answer_223").is(':checked')) {
        $("#question_84").removeClass("d-none");
        $("#question_85").removeClass("d-none");
    }

    if ($("#answer_230").is(':checked')) {
        $("#question_87").removeClass("d-none");
    }

    if ($("#answer_232").is(':checked')) {
        $("#question_86").removeClass("d-none");
    }

});

$("#armSteps").steps({
    headerTag: "h3",
    labels: {
        finish: "<i class=\"fas fa-arrow-right\"></i>",
        next: "<i class=\"fas fa-arrow-right\"></i>",
        previous: "<i class=\"fas fa-arrow-left\"></i>",
    },
    bodyTag: "section",
    transitionEffect: "slideLeft",
    autoFocus: true,
    onStepChanging: function (event, currentIndex, newIndex) {
        let result;
        $.when(onStepChanging()).done(function(response){
            console.log(response);
            result = response;
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

        let questionId = spanId.split("_")[1];
        let answerArray = [];

        $(this).find("input[type='checkbox'][name='" + radioName + "']:checked").each(function () {
            var checkboxId = $(this).attr("id");
            if (checkboxId !== undefined) {
                let answerId = checkboxId.split("_")[1];
                answerArray.push(answerId);
            };
        });

        let sendInfo = {
            questionId: questionId,
            answerId: answerArray
        };

        if(answerArray.length > 0) {
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
    $("[id^='armSteps-t-']").css("min-height","auto");
    $("[id^='armSteps-t-']").each(function() {
        if($(this).innerHeight()>step_max_height){step_max_height=$(this).innerHeight();}
    });
    $("[id^='armSteps-t-']").each(function() {
        $(this).css("min-height",step_max_height+"px")
    });
}

$(document).delegate("#answer_223", "click", function() {
    $("#question_84").removeClass("d-none");
    $("#question_85").removeClass("d-none");
});

$(document).delegate("#answer_230", "click", function() {
    $("#question_87").removeClass("d-none");
});

$(document).delegate("#answer_231", "click", function() {
    if(!$("#question_86").hasClass("d-none")) {
        $("#question_86").addClass("d-none");
        $("#question_86").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
    if(!$("#question_87").hasClass("d-none")) {
        $("#question_87").addClass("d-none");
        $("#question_87").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
});

$(document).delegate("#answer_232", "click", function() {
    $("#question_86").removeClass("d-none");

    if(!$("#question_87").hasClass("d-none")) {
        $("#question_87").addClass("d-none");
        $("#question_87").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
});

$(document).delegate("#answer_224", "click", function() {
    if(!$("#question_84").hasClass("d-none")) {
        $("#question_84").addClass("d-none");
        $("#question_84").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
    if(!$("#question_85").hasClass("d-none")) {
        $("#question_85").addClass("d-none");
        $("#question_85").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
});

$(document).delegate("#answer_225", "click", function() {
    if(!$("#question_84").hasClass("d-none")) {
        $("#question_84").addClass("d-none");
        $("#question_84").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
    if(!$("#question_85").hasClass("d-none")) {
        $("#question_85").addClass("d-none");
        $("#question_85").children('.white-background').each(function () {
            $(this).find("input[type='radio']:checked").prop("checked", false);
        })
    }
});

$(document).delegate("#answer_242", "click", function() {
    if($("#answer_242").prop("checked")) {
        $("#answer_238").prop('checked', false);
        $("#answer_239").prop('checked', false);
        $("#answer_240").prop('checked', false);
        $("#answer_241").prop('checked', false);
    }
    else {
        $("#answer_238").prop('checked', true);
        $("#answer_239").prop('checked', true);
        $("#answer_240").prop('checked', true);
        $("#answer_241").prop('checked', true);
    }
});

$(document).delegate("#answer_238", "click", function() {
    if($("#answer_242").prop("checked")) {
        $("#answer_242").prop('checked', false);
    }
});

$(document).delegate("#answer_239", "click", function() {
    if($("#answer_242").prop("checked")) {
        $("#answer_242").prop('checked', false);
    }
});

$(document).delegate("#answer_240", "click", function() {
    if($("#answer_240").prop("checked")) {
        $("#answer_242").prop('checked', false);
    }
});

$(document).delegate("#answer_241", "click", function() {
    if($("#answer_241").prop("checked")) {
        $("#answer_242").prop('checked', false);
    }
});

