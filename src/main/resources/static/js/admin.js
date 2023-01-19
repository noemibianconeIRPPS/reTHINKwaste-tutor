$(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

document.getElementById("addLearningMaterialModal").addEventListener('show.bs.modal', function (event) {
    $('#addLearningMaterialTypes').multiselect();
    $('#addLearningMaterialModules').multiselect();
});

$(document).delegate('#reportGeneralComments', 'click', function(e) {
    $.ajax({
        type: "GET",
        url: window.location.pathname + "/reportGeneralComments",
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $('#reportProfileLevelFragment').html(data);
            $("#tableFragment").html(data);
            console.log("?")
            $('#reportTable').DataTable();
            $('#reportTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.translationEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".translationEditIcon").addClass("d-none");
    td.find(".translationSaveIcon").removeClass("d-none");
    td.find(".translationCloseIcon").removeClass("d-none");
    td.find('.translationEditText').attr('contentEditable',true);
    td.find('.translationEditText').focus();
});

$(document).delegate( '.translationCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".translationSaveIcon").addClass("d-none");
    td.find(".translationCloseIcon").addClass("d-none");
    td.find('.translationEditText').attr('contentEditable',false);
    td.find(".translationEditIcon").removeClass("d-none");
});

$(document).delegate( '.translationSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let profileId = td.find(".profileId").attr("id").replace("profileId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.translationEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeReportGeneralComments",
        traditional: true,
        data: {profileId: profileId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#reportTable').DataTable();
            td.find(".translationEditIcon").removeClass("d-none");
            td.find(".translationSaveIcon").addClass("d-none");
            td.find(".translationCloseIcon").addClass("d-none");
            td.find('.translationEditText').attr('contentEditable',false);
            $('#reportTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.categoryTranslationEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".categoryTranslationEditIcon").addClass("d-none");
    td.find(".categoryTranslationSaveIcon").removeClass("d-none");
    td.find(".categoryTranslationCloseIcon").removeClass("d-none");
    td.find('.categoryTranslationEditText').attr('contentEditable',true);
    td.find('.categoryTranslationEditText').focus();
});

$(document).delegate( '.categoryTranslationCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".categoryTranslationSaveIcon").addClass("d-none");
    td.find(".categoryTranslationCloseIcon").addClass("d-none");
    td.find('.categoryTranslationEditText').attr('contentEditable',false);
    td.find(".categoryTranslationEditIcon").removeClass("d-none");
});

$(document).delegate( '.categoryTranslationSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let categoryId = td.find(".categoryId").attr("id").replace("categoryId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.categoryTranslationEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeCategoryGeneralComments",
        traditional: true,
        data: {categoryId: categoryId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#reportTable').DataTable();
            td.find(".categoryTranslationEditIcon").removeClass("d-none");
            td.find(".categoryTranslationSaveIcon").addClass("d-none");
            td.find(".categoryTranslationCloseIcon").addClass("d-none");
            td.find('.categoryTranslationEditText').attr('contentEditable',false);
            $('#reportTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

// PROFILE LEVEL TEXT

$(document).delegate('#reportProfileLevel', 'click', function(e) {

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/reportProfileLevel",
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#tableFragment').html(data);
            $('#reportProfileLevelTable').DataTable();
            $('#reportProfileLevelTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.subprofileNameTranslationEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".subprofileNameTranslationEditIcon").addClass("d-none");
    td.find(".subprofileNameTranslationSaveIcon").removeClass("d-none");
    td.find(".subprofileNameTranslationCloseIcon").removeClass("d-none");
    td.find('.subprofileNameTranslationEditText').attr('contentEditable',true);
    td.find('.subprofileNameTranslationEditText').focus();
});

$(document).delegate( '.subprofileNameTranslationCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".subprofileNameTranslationSaveIcon").addClass("d-none");
    td.find(".subprofileNameTranslationCloseIcon").addClass("d-none");
    td.find('.subprofileNameTranslationEditText').attr('contentEditable',false);
    td.find(".subprofileNameTranslationEditIcon").removeClass("d-none");
});

$(document).delegate( '.subprofileNameTranslationSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let subprofileId = td.find(".subprofileId").attr("id").replace("subprofileId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.subprofileNameTranslationEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeSuprofileName",
        traditional: true,
        data: {subprofileId: subprofileId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#reportProfileLevelTable').DataTable();
            td.find(".subprofileNameTranslationEditIcon").removeClass("d-none");
            td.find(".subprofileNameTranslationSaveIcon").addClass("d-none");
            td.find(".subprofileNameTranslationCloseIcon").addClass("d-none");
            td.find('.subprofileNameTranslationEditText').attr('contentEditable',false);
            $('#reportProfileLevelTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.subprofileEvaluationTranslationEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".subprofileEvaluationTranslationEditIcon").addClass("d-none");
    td.find(".subprofileEvaluationTranslationSaveIcon").removeClass("d-none");
    td.find(".subprofileEvaluationTranslationCloseIcon").removeClass("d-none");
    td.find('.subprofileEvaluationTranslationEditText').attr('contentEditable',true);
    td.find('.subprofileEvaluationTranslationEditText').focus();
});

$(document).delegate( '.subprofileEvaluationTranslationCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".subprofileEvaluationTranslationSaveIcon").addClass("d-none");
    td.find(".subprofileEvaluationTranslationCloseIcon").addClass("d-none");
    td.find('.subprofileEvaluationTranslationEditText').attr('contentEditable',false);
    td.find(".subprofileEvaluationTranslationEditIcon").removeClass("d-none");
});

$(document).delegate( '.subprofileEvaluationTranslationSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let subprofileId = td.find(".subprofileId").attr("id").replace("subprofileId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.subprofileEvaluationTranslationEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeSuprofileEvaluation",
        traditional: true,
        data: {subprofileId: subprofileId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#reportProfileLevelTable').DataTable();
            td.find(".subprofileEvaluationTranslationEditIcon").removeClass("d-none");
            td.find(".subprofileEvaluationTranslationSaveIcon").addClass("d-none");
            td.find(".subprofileEvaluationTranslationCloseIcon").addClass("d-none");
            td.find('.subprofileEvaluationTranslationEditText').attr('contentEditable',false);
            $('#reportProfileLevelTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
})

$(document).delegate( '.subprofileGeneralCommentTranslationEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".subprofileGeneralCommentTranslationEditIcon").addClass("d-none");
    td.find(".subprofileGeneralCommentTranslationSaveIcon").removeClass("d-none");
    td.find(".subprofileGeneralCommentTranslationCloseIcon").removeClass("d-none");
    td.find('.subprofileGeneralCommentTranslationEditText').attr('contentEditable',true);
    td.find('.subprofileGeneralCommentTranslationEditText').focus();
});

$(document).delegate( '.subprofileGeneralCommentTranslationCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".subprofileGeneralCommentTranslationSaveIcon").addClass("d-none");
    td.find(".subprofileGeneralCommentTranslationCloseIcon").addClass("d-none");
    td.find('.subprofileGeneralCommentTranslationEditText').attr('contentEditable',false);
    td.find(".subprofileGeneralCommentTranslationEditIcon").removeClass("d-none");
});

$(document).delegate( '.subprofileGeneralCommentTranslationSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let subprofileId = td.find(".subprofileId").attr("id").replace("subprofileId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.subprofileGeneralCommentTranslationEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeSuprofileGeneralComment",
        traditional: true,
        data: {subprofileId: subprofileId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#reportProfileLevelTable').DataTable();
            td.find(".subprofileGeneralCommentTranslationEditIcon").removeClass("d-none");
            td.find(".subprofileGeneralCommentTranslationSaveIcon").addClass("d-none");
            td.find(".subprofileGeneralCommentTranslationCloseIcon").addClass("d-none");
            td.find('.subprofileGeneralCommentTranslationEditText').attr('contentEditable',false);
            $('#reportProfileLevelTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

// QUESTIONS EDIT

$(document).delegate('#questions', 'click', function(e) {

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/questions",
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#tableFragment').html(data);
            $('#questionsTable').DataTable();
            $('#questionsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.questionEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".questionEditIcon").addClass("d-none");
    td.find(".questionSaveIcon").removeClass("d-none");
    td.find(".questionCloseIcon").removeClass("d-none");
    td.find('.questionEditText').attr('contentEditable',true);
    td.find('.questionEditText').focus();
});

$(document).delegate( '.questionCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".questionSaveIcon").addClass("d-none");
    td.find(".questionCloseIcon").addClass("d-none");
    td.find('.questionEditText').attr('contentEditable',false);
    td.find(".questionEditIcon").removeClass("d-none");
});

$(document).delegate( '.questionSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let questionId = td.find(".questionId").attr("id").replace("questionId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.questionEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeQuestion",
        traditional: true,
        data: {questionId: questionId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#questionsTable').DataTable();
            td.find(".questionEditIcon").removeClass("d-none");
            td.find(".questionSaveIcon").addClass("d-none");
            td.find(".questionCloseIcon").addClass("d-none");
            td.find('.questionEditText').attr('contentEditable',false);
            $('#questionsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});


$(document).delegate( '.questionGuidelineEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".questionGuidelineEditIcon").addClass("d-none");
    td.find(".questionGuidelineSaveIcon").removeClass("d-none");
    td.find(".questionGuidelineCloseIcon").removeClass("d-none");
    td.find('.questionGuidelineEditText').attr('contentEditable',true);
    td.find('.questionGuidelineEditText').focus();
});

$(document).delegate( '.questionGuidelineCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".questionGuidelineSaveIcon").addClass("d-none");
    td.find(".questionGuidelineCloseIcon").addClass("d-none");
    td.find('.questionGuidelineEditText').attr('contentEditable',false);
    td.find(".questionGuidelineEditIcon").removeClass("d-none");
});

$(document).delegate( '.questionGuidelineSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let questionId = td.find(".questionIdForGuideline").attr("id").replace("questionId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.questionGuidelineEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeQuestionGuideline",
        traditional: true,
        data: {questionId: questionId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#questionsTable').DataTable();
            td.find(".questionGuidelineEditIcon").removeClass("d-none");
            td.find(".questionGuidelineSaveIcon").addClass("d-none");
            td.find(".questionGuidelineCloseIcon").addClass("d-none");
            td.find('.questionGuidelineEditText').attr('contentEditable',false);
            $('#questionsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

// ANSWERS EDIT

$(document).delegate('#answers', 'click', function(e) {

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/answers",
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#tableFragment').html(data);
            $('#answersTable').DataTable();
            $('#answersTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.answerEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".answerEditIcon").addClass("d-none");
    td.find(".answerSaveIcon").removeClass("d-none");
    td.find(".answerCloseIcon").removeClass("d-none");
    td.find('.answerEditText').attr('contentEditable',true);
    td.find('.answerEditText').focus();
});

$(document).delegate( '.answerCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".answerSaveIcon").addClass("d-none");
    td.find(".answerCloseIcon").addClass("d-none");
    td.find('.answerEditText').attr('contentEditable',false);
    td.find(".answerEditIcon").removeClass("d-none");
});

$(document).delegate( '.answerSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let answerId = td.find(".answerId").attr("id").replace("answerId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.answerEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeAnswer",
        traditional: true,
        data: {answerId: answerId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#answersTable').DataTable();
            td.find(".answerEditIcon").removeClass("d-none");
            td.find(".answerSaveIcon").addClass("d-none");
            td.find(".answerCloseIcon").addClass("d-none");
            td.find('.answerEditText').attr('contentEditable',false);
            $('#answersTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

// LEARNING MATERIALS

$(document).delegate('#learningMaterials', 'click', function(e) {

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/learningMaterials",
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#tableFragment').html(data);
            $('#learningMaterialsTable').DataTable();
            $('#learningMaterialsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.learningMaterialLinkEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".learningMaterialLinkEditIcon").addClass("d-none");
    td.find(".learningMaterialLinkSaveIcon").removeClass("d-none");
    td.find(".learningMaterialLinkCloseIcon").removeClass("d-none");
    td.find('.learningMaterialLinkEditText').attr('contentEditable',true);
    td.find('.learningMaterialLinkEditText').focus();
});

$(document).delegate( '.learningMaterialLinkCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".learningMaterialLinkSaveIcon").addClass("d-none");
    td.find(".learningMaterialLinkCloseIcon").addClass("d-none");
    td.find('.learningMaterialLinkEditText').attr('contentEditable',false);
    td.find(".learningMaterialLinkEditIcon").removeClass("d-none");
});

$(document).delegate( '.learningMaterialLinkSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let learningMaterialId = td.find(".learningMaterialId").attr("id").replace("learningMaterialId_", "");
    let newText = td.find('.learningMaterialLinkEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeLearningMaterialLink",
        traditional: true,
        data: {learningMaterialId: learningMaterialId, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#learningMaterialsTable').DataTable();
            td.find(".learningMaterialLinkEditIcon").removeClass("d-none");
            td.find(".learningMaterialLinkSaveIcon").addClass("d-none");
            td.find(".learningMaterialLinkCloseIcon").addClass("d-none");
            td.find('.learningMaterialLinkEditText').attr('contentEditable',false);
            $('#learningMaterialsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.learningMaterialTitleEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".learningMaterialTitleEditIcon").addClass("d-none");
    td.find(".learningMaterialTitleSaveIcon").removeClass("d-none");
    td.find(".learningMaterialTitleCloseIcon").removeClass("d-none");
    td.find('.learningMaterialTitleEditText').attr('contentEditable',true);
    td.find('.learningMaterialTitleEditText').focus();
});

$(document).delegate( '.learningMaterialTitleCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".learningMaterialTitleSaveIcon").addClass("d-none");
    td.find(".learningMaterialTitleCloseIcon").addClass("d-none");
    td.find('.learningMaterialTitleEditText').attr('contentEditable',false);
    td.find(".learningMaterialTitleEditIcon").removeClass("d-none");
});

$(document).delegate( '.learningMaterialTitleSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let learningMaterialId = td.find(".learningMaterialId").attr("id").replace("learningMaterialId_", "");
    let newText = td.find('.learningMaterialTitleEditText').text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeLearningMaterialTitle",
        traditional: true,
        data: {learningMaterialId: learningMaterialId, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#learningMaterialsTable').DataTable();
            td.find(".learningMaterialTitleEditIcon").removeClass("d-none");
            td.find(".learningMaterialTitleSaveIcon").addClass("d-none");
            td.find(".learningMaterialTitleCloseIcon").addClass("d-none");
            td.find('.learningMaterialTitleEditText').attr('contentEditable',false);
            $('#learningMaterialsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.learningMaterialLanguageEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".learningMaterialLanguageEditIcon").addClass("d-none");
    td.find('.learningMaterialLanguageEditText').addClass("d-none");
    td.find(".learningMaterialLanguageSaveIcon").removeClass("d-none");
    td.find("#learningMaterialLanguageSelect").removeClass("d-none");

});

$(document).delegate( '.learningMaterialLanguageSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let learningMaterialId = td.find(".learningMaterialId").attr("id").replace("learningMaterialId_", "");
    let newText = $( "#learningMaterialLanguageSelect option:selected" ).text();

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeLearningMaterialLanguage",
        traditional: true,
        data: {learningMaterialId: learningMaterialId, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#learningMaterialsTable').DataTable();
            td.find(".learningMaterialLanguageEditIcon").removeClass("d-none");
            td.find(".learningMaterialLanguageSaveIcon").addClass("d-none");
            td.find("#learningMaterialLanguageSelect").addClass("d-none");
            td.find('.learningMaterialLanguageEditText').removeClass("d-none");
            $('#learningMaterialsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.learningMaterialTypeEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".learningMaterialTypeEditIcon").addClass("d-none");
    td.find('.learningMaterialTypeEditText').addClass("d-none");
    td.find(".learningMaterialTypeSaveIcon").removeClass("d-none");
    td.find('.learningMaterialTypeSelectClass').multiselect();
});


$(document).delegate( '.learningMaterialTypeSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let learningMaterialId = td.find(".learningMaterialId").attr("id").replace("learningMaterialId_", "");
    let newText = td.find('.learningMaterialTypeSelectClass').val();
    console.log("NEW TEXT " + newText);

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeLearningMaterialTypes",
        traditional: true,
        data: {learningMaterialId: learningMaterialId, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#learningMaterialsTable').DataTable();
            td.find(".learningMaterialTypeEditIcon").removeClass("d-none");
            td.find(".learningMaterialTypeSaveIcon").addClass("d-none");
            td.find("#learningMaterialTypeSelect").addClass("d-none");
            td.find(".learningMaterialTypeCloseIcon").addClass("d-none");
            td.find('.learningMaterialTypeEditText').removeClass("d-none");
            $('#learningMaterialsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

$(document).delegate( '.learningMaterialModuleEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".learningMaterialModuleEditIcon").addClass("d-none");
    td.find('.learningMaterialModuleEditText').addClass("d-none");
    td.find(".learningMaterialModuleSaveIcon").removeClass("d-none");
    td.find('.learningMaterialModuleSelectClass').multiselect();
});


$(document).delegate( '.learningMaterialModuleSaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let learningMaterialId = td.find(".learningMaterialId").attr("id").replace("learningMaterialId_", "");
    let newText = td.find('.learningMaterialModuleSelectClass').val();
    console.log("NEW TEXT " + newText);

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeLearningMaterialModules",
        traditional: true,
        data: {learningMaterialId: learningMaterialId, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#learningMaterialsTable').DataTable();
            td.find(".learningMaterialModuleEditIcon").removeClass("d-none");
            td.find(".learningMaterialModuleSaveIcon").addClass("d-none");
            td.find("#learningMaterialModuleSelect").addClass("d-none");
            td.find(".learningMaterialModuleCloseIcon").addClass("d-none");
            td.find('.learningMaterialModuleEditText').removeClass("d-none");
            $('#learningMaterialsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});

function isValidHttpUrl(string) {
    let url;

    try {
        url = new URL(string);
    } catch (_) {
        return false;
    }

    return url.protocol === "http:" || url.protocol === "https:";
}

$(document).delegate("#addLearningMaterial", "click", function() {

    $("#invalidUrl").addClass("d-none");

    let modules = $('#addLearningMaterialModules').val();
    let types = $('#addLearningMaterialTypes').val();
    let language = $( "#addLearningMaterialLanguage option:selected" ).text();
    let title = $('#addLearningMaterialText').val();
    let link = $('#addLearningMaterialLink').val();

    if(isValidHttpUrl(link)) {
        $.ajax({
            type: "GET",
            url: window.location.pathname + "/addLearningMaterial",
            traditional: true,
            data: {modules: modules, types: types, language: language, title: title, link: link},
            cache: false,
            timeout: 600000,
            success: function (data) {
                $('#addLearningMaterialModal').modal('hide');
                $('#addLearningMaterialModal').find("input,textarea,select").val('').end();

                $("#learningMaterialsFragment").html(data);
                $("#answersFragment").html(data);
                $("#questionsFragment").html(data);
                $("#tableFragment").html(data);
                $("#reportProfileLevelFragment").html(data);
                $('#learningMaterialsTable').DataTable();
                $('#learningMaterialsTable').removeClass("d-none");
            },
            error: function (e) {
            }
        });
    }
    else {
        $("#invalidUrl").removeClass("d-none");
    }

});

$(document).delegate(".deleteLearningMaterial", "click", function() {
    let learningMaterialId = $(this).attr("id").replaceAll("learningMaterialId_", "");
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#1cb3a7',
        cancelButtonColor: '#F24236',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "GET",
                url: window.location.pathname + "/deleteLearningMaterial",
                traditional: true,
                data: {learningMaterialId: learningMaterialId},
                cache: false,
                timeout: 600000,
                success: function (data) {
                    $("#learningMaterialsFragment").html(data);
                    $("#answersFragment").html(data);
                    $("#questionsFragment").html(data);
                    $("#tableFragment").html(data);
                    $("#reportProfileLevelFragment").html(data);
                    $('#learningMaterialsTable').DataTable();
                    $('#learningMaterialsTable').removeClass("d-none");
                },
                error: function (e) {
                }
            });
        }
    })
});

$(document).delegate( '.categoryEditIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".categoryEditIcon").addClass("d-none");
    td.find(".categorySaveIcon").removeClass("d-none");
    td.find(".categoryCloseIcon").removeClass("d-none");
    td.find('.categoryEditText').attr('contentEditable',true);
    td.find('.categoryEditText').focus();
});

$(document).delegate( '.categoryCloseIcon', 'click', function () {
    let td = $(this).closest("tr");
    td.find(".categorySaveIcon").addClass("d-none");
    td.find(".categoryCloseIcon").addClass("d-none");
    td.find('.categoryEditText').attr('contentEditable',false);
    td.find(".categoryEditIcon").removeClass("d-none");
});

$(document).delegate( '.categorySaveIcon', 'click', function () {
    let td = $(this).closest("tr");
    let categoryId = td.find(".categoryId").attr("id").replace("categoryId_", "");
    let language = td.find(".language").text();
    let newText = td.find('.categoryEditText').text();

    console.log(categoryId + " " + language + " " + newText);

    $.ajax({
        type: "GET",
        url: window.location.pathname + "/saveChangeCategory",
        traditional: true,
        data: {categoryId: categoryId, language: language, newText: newText},
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#learningMaterialsFragment").html(data);
            $("#answersFragment").html(data);
            $("#questionsFragment").html(data);
            $("#tableFragment").html(data);
            $("#reportProfileLevelFragment").html(data);
            $('#questionsTable').DataTable();
            td.find(".categoryEditIcon").removeClass("d-none");
            td.find(".categorySaveIcon").addClass("d-none");
            td.find(".categoryCloseIcon").addClass("d-none");
            td.find('.categoryEditText').attr('contentEditable',false);
            $('#questionsTable').removeClass("d-none");
        },
        error: function (e) {
        }
    });
});