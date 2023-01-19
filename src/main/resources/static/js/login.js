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