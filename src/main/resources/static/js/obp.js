$(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
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

$("#obpSteps").steps({
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
            result = response;
            $(window).scrollTop(0);;
            $(".content").scrollTop(0);
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
    var step_max_height=0;
    $("[id^='obpSteps-t-']").css("min-height","auto");
    $("[id^='obpSteps-t-']").each(function() {
        if($(this).innerHeight()>step_max_height){step_max_height=$(this).innerHeight();}
    });
    $("[id^='obpSteps-t-']").each(function() {
        $(this).css("min-height",step_max_height+"px")
    });
}