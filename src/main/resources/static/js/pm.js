$(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $('[data-bs-toggle="tooltip"]').tooltip();
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

let answer134 = false;
let answer140 = false;
let answer144 = false;
let answer146 = false;

function changeWizardContent() {
    var actualSection = $(".wizardContent.active").removeClass("active");
    var nextSection = actualSection.next('.wizardContent').next('.wizardContent');
    console.log("NEXT " + nextSection.attr("id"));
    nextSection.addClass("active slideLeft");
}

function changeWizardContent1Step() {
    var actualSection = $(".wizardContent.active").removeClass("active");
    var nextSection = actualSection.next('.wizardContent');
    nextSection.addClass("active slideLeft");
}

$(document).delegate(".wizardNext", "click", function (event) {
    let button = $(event.target).is('a') ? $(event.target) : $(event.target).parent();
    let categoryId = button.attr("id").replace('wizardNext_', '');
    let array = [];
    if(categoryId !== "10") {
        let infoArray = new Object();
        $('.wizardContent.active').find('.white-background').each(function () {
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
                console.log("infoARRAY " + JSON.stringify(sendInfo));
                array.push(sendInfo);
            }
        });
        infoArray.categoryId = categoryId;
        infoArray.infos = array;
        console.log("ESCO ");

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: window.location.pathname + "/saveStep",
            data: JSON.stringify(infoArray),
            dataType: 'json',
            cache: false,
            async: false,
            success: function (data) {
                console.log("ENTRO ");

                if(array[0].answerId === "134") {
                    changeWizardContent();
                    answer134 = true;
                }
                else if(array[0].answerId === "133") {
                    changeWizardContent1Step();
                    answer134 = false;
                }
                else if(array[0].answerId === "140") {
                    changeWizardContent();
                    answer140 = true;
                }
                else  if(array[0].answerId === "139") {
                    changeWizardContent1Step();
                    answer140 = false;
                }
                else if(array[0].answerId === "146") {
                    changeWizardContent();
                    answer146 = true;
                }
                else if(array[0].answerId === "145") {
                    changeWizardContent1Step();
                    answer146 = false;
                }
                else if(array[0].answerId === "143") {
                    changeWizardContent1Step();
                    answer144 = false;
                }
                else if(array[0].answerId === "144") {
                    var actualSection = $(".wizardContent.active").removeClass("active");
                    var nextSection = actualSection.next('.wizardContent').next('.wizardContent').next('.wizardContent');
                    nextSection.addClass("active slideLeft");
                    answer144 = true;
                }
                else if(array[0].answerId === "154" || categoryId === "24") {
                    window.location.href = '/registeredarea/profile';
                }
                else {
                    changeWizardContent1Step();
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    }
    else {
        var actualSection = $(".wizardContent.active").removeClass("active");
        var nextSection = actualSection.next('.wizardContent');
        nextSection.addClass("active slideLeft");
    }
});

$(document).delegate(".wizardPrevious", "click", function (event) {
    let button = $(event.target).is('a') ? $(event.target) : $(event.target).parent();
    let categoryId = button.attr("id").replace('wizardPrevious_', '');
    if(categoryId === "51" && answer134) {
        var actualSection = $(".wizardContent.active").removeClass("active");
        var nextSection = actualSection.prev('.wizardContent').prev('.wizardContent');
        nextSection.addClass("active slideLeft");
    }
    if(categoryId === "54" && answer140) {
        var actualSection = $(".wizardContent.active").removeClass("active");
        var nextSection = actualSection.prev('.wizardContent').prev('.wizardContent');
        nextSection.addClass("active slideLeft");
    }
    if(categoryId === "57" && answer146) {
        var actualSection = $(".wizardContent.active").removeClass("active");
        var nextSection = actualSection.prev('.wizardContent').prev('.wizardContent');
        nextSection.addClass("active slideLeft");
    }
    if(categoryId === "57" && answer144) {
        var actualSection = $(".wizardContent.active").removeClass("active");
        var nextSection = actualSection.prev('.wizardContent').prev('.wizardContent').prev('.wizardContent');
        nextSection.addClass("active slideLeft");
    }
    else {
        var actualSection = $(".wizardContent.active").removeClass("active");
        var nextSection = actualSection.prev('.wizardContent');
        nextSection.addClass("active slideLeft");
    }
});

$("#pmSteps").steps({
    headerTag: "h3",
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
    $("[id^='pmSteps-t-']").each(function() {
        $(this).addClass("d-none");
    });
}

$(document).delegate("#answer_134", "click", function() {
    $("#pmSteps-p-5").click();
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
    }
});

$(document).delegate("#answer_38", "click", function() {
    $("#questionChild_21").removeClass("d-none");
    $("#question3category2label").removeClass("d-none");
});

$(document).delegate("#answer_39", "click", function() {
    $("#questionChild_21").removeClass("d-none");
    $("#question3category2label").removeClass("d-none");
});