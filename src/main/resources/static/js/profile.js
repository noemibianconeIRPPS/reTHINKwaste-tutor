window.chartColors = {
    green: 'rgb(28, 179, 167)'
};

$('#suggestedLearningMaterialTable').DataTable( {
    "pageLength": 5
});
$('#suggestedLearningMaterialTable').removeClass("d-none");

/* FUNCTION FOR PERCENTAGE GRAPH DRAWING */
function percentageGraphDraw(id) {
    var el = document.getElementById(id); // get canvas

    var options = {
        percent:  el.getAttribute('data-percent') || 25,
        size: el.getAttribute('data-size') || 220,
        lineWidth: el.getAttribute('data-line') || 15,
        rotate: el.getAttribute('data-rotate') || 0
    }

    var canvas = document.createElement('canvas');
    var span = document.createElement('span');
    span.textContent = options.percent + '%';

    if (typeof(G_vmlCanvasManager) !== 'undefined') {
        G_vmlCanvasManager.initElement(canvas);
    }

    var ctx = canvas.getContext('2d');
    canvas.width = canvas.height = options.size;

    el.appendChild(span);
    el.appendChild(canvas);

    ctx.translate(options.size / 2, options.size / 2); // change center
    ctx.rotate((-1 / 2 + options.rotate / 180) * Math.PI); // rotate -90 deg

    var radius = (options.size - options.lineWidth) / 2;

    var drawCircle = function(color, lineWidth, percent) {
        percent = Math.min(Math.max(0, percent || 1), 1);
        ctx.beginPath();
        ctx.arc(0, 0, radius, 0, Math.PI * 2 * percent, false);
        ctx.strokeStyle = color;
        ctx.lineCap = 'round'; // butt, round or square
        ctx.lineWidth = lineWidth
        ctx.stroke();
    };

    drawCircle('#efefef', options.lineWidth, 100 / 100);
    drawCircle('#c7d114', options.lineWidth, options.percent / 100);
}

var scoresArray = [];

$.ajax({
    url: "/registeredarea/profile/radarFillIn",
    type: "GET",
    contentType: "application/json",
    success: function (data) {
        data.forEach((entry) => {
            scoresArray.push(entry);
        });
        draw();
        percentageGraphDraw('percentageGraph');
    },
    error: function (data) {
        console.log("error");

    }
});

function draw() {
    console.log("SCORES ARRAY " + scoresArray);
    var color = Chart.helpers.color;
    var config = {
        type: 'radar',
        data: {
            labels: [
                "TTR", "ARM", "PM",
                "IGPP", "MNC",
                "OBP"],
            datasets: [{
                backgroundColor: color(window.chartColors.green).alpha(0).rgbString(),
                borderColor: window.chartColors.green,
                pointBackgroundColor: window.chartColors.green,
                data: scoresArray
            }]
        },
        options: {
            plugins: {
                legend: {
                    display: false,
                    labels: {
                        display: false
                    }
                }
            },
            title: {
                display: false
            },
            scales: {
                r: {
                    min: 0,
                    max: 3,
                    ticks: {
                        stepSize: 0.1
                    }
                }
            },
            tooltips: {
                enabled: false,
                callbacks: {
                    label: function (tooltipItem, data) {
                        var datasetLabel = data.datasets[tooltipItem.datasetIndex].label || '';
                        //This will be the tooltip.body
                        return datasetLabel + ': ' + tooltipItem.yLabel + ': ' + data.datasets[tooltipItem.datasetIndex].notes[tooltipItem.index];
                    }
                },
                custom: function (tooltip) {
                    // Tooltip Element
                    var tooltipEl = document.getElementById('chartjs-tooltip');
                    if (!tooltipEl) {
                        tooltipEl = document.createElement('div');
                        tooltipEl.id = 'chartjs-tooltip';
                        tooltipEl.innerHTML = "<table></table>"
                        document.body.appendChild(tooltipEl);
                    }
                    // Hide if no tooltip
                    if (tooltip.opacity === 0) {
                        tooltipEl.style.opacity = 0;
                        return;
                    }
                    // Set caret Position
                    tooltipEl.classList.remove('above', 'below', 'no-transform');
                    if (tooltip.yAlign) {
                        tooltipEl.classList.add(tooltip.yAlign);
                    } else {
                        tooltipEl.classList.add('no-transform');
                    }

                    function getBody(bodyItem) {
                        return bodyItem.lines;
                    }

                    // Set Text
                    if (tooltip.body) {
                        var titleLines = tooltip.title || [];
                        var bodyLines = tooltip.body.map(getBody);
                        var innerHtml = '<thead>';
                        titleLines.forEach(function (title) {
                            innerHtml += '<tr><th>' + title + '</th></tr>';
                        });
                        innerHtml += '</thead><tbody>';
                        bodyLines.forEach(function (body, i) {
                            var colors = tooltip.labelColors[i];
                            var style = 'background:' + colors.backgroundColor;
                            style += '; border-color:' + colors.borderColor;
                            style += '; border-width: 2px';
                            var span = '<span class="chartjs-tooltip-key" style="' + style + '"></span>';
                            innerHtml += '<tr><td>' + span + body + '</td></tr>';
                        });
                        innerHtml += '</tbody>';
                        var tableRoot = tooltipEl.querySelector('table');
                        tableRoot.innerHTML = innerHtml;
                    }
                    var position = this._chart.canvas.getBoundingClientRect();
                    // Display, position, and set styles for font
                    tooltipEl.style.opacity = 1;
                    tooltipEl.style.left = position.left + tooltip.caretX + 'px';
                    tooltipEl.style.top = position.top + tooltip.caretY + 'px';
                    tooltipEl.style.fontFamily = tooltip._fontFamily;
                    tooltipEl.style.fontSize = tooltip.fontSize;
                    tooltipEl.style.fontStyle = tooltip._fontStyle;
                    tooltipEl.style.padding = tooltip.yPadding + 'px ' + tooltip.xPadding + 'px';
                }
            }
        }
    }
    window.onload = function() {
        window.myRadar = new Chart(document.getElementById("canvas"), config);
    };
};

    var colorNames = Object.keys(window.chartColors);

$(document).delegate("#partialPercentageAnchor", "click", function() {
    if($("#modulePercentagesAndScores").hasClass("d-none")) {
        $("#modulePercentagesAndScores").removeClass("d-none");
    }
    else {
        $("#modulePercentagesAndScores").addClass("d-none");
    }
});

function downloadFullreport() {
    downloadReport("/registeredarea/general/downloadFullreport");
}

function downloadTTRreport() {
    downloadReport("/registeredarea/ttr/downloadTTRreport");
}

function downloadTTRassessment() {
    downloadAssessment("/registeredarea/ttr/downloadTTRassessment");
}

function downloadIGPPreport() {
    downloadReport("/registeredarea/igpp/downloadIGPPreport");
}

function downloadIGPPassessment() {
    downloadAssessment("/registeredarea/igpp/downloadIGPPassessment");
}

function downloadARMreport() {
    downloadReport("/registeredarea/arm/downloadARMreport");
}

function downloadARMassessment() {
    downloadAssessment("/registeredarea/arm/downloadARMassessment");
}

function downloadMNCreport() {
    downloadReport("/registeredarea/mnc/downloadMNCreport");
}

function downloadMNCassessment() {
    downloadAssessment("/registeredarea/mnc/downloadMNCassessment");
}

function downloadPMreport() {
    downloadReport("/registeredarea/pm/downloadPMreport");
}

function downloadPMassessment() {
    downloadAssessment("/registeredarea/pm/downloadPMassessment");
}

function downloadOBPreport() {
    downloadReport("/registeredarea/obp/downloadOBPreport");
}

function downloadOBPassessment() {
    downloadAssessment("/registeredarea/obp/downloadOBPassessment");
}

function downloadAssessment(path) {
    $(".overlay").removeClass("d-none");
    var xhr = new XMLHttpRequest();
    xhr.open('GET', path, true);
    xhr.responseType = 'arraybuffer';
    xhr.onload = function() {
        if(this.status == '200') {
            var filename = '';
            //get the filename from the header.
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches !== null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var type = xhr.getResponseHeader('Content-Type');
            var blob = new Blob([this.response],  {type: type});
            if(typeof window.navigator.msSaveBlob != 'undefined') {
                window.navigator.msSaveBlob(blob, filename);
            }
            else {
                var URL = window.URL || window.webkitURL;
                console.log("ELSE");
                var download_URL = URL.createObjectURL(blob);
                if(filename) {
                    var a_link = document.createElement('a');
                    if(typeof a_link.download == 'undefined') {
                        window.open(download_URL);
                        $(".overlay").addClass("d-none");
                    } else {
                        a_link.href = download_URL;
                        a_link.download = filename;
                        document.body.appendChild(a_link);
                        a_link.click();
                        $(".overlay").addClass("d-none");
                    }
                } else {
                    window.open(download_URL);
                    $(".overlay").addClass("d-none");
                }
                setTimeout(function() {
                    URL.revokeObjectURL(download_URL);
                }, 10000);
            }
        }else {
            alert('error');
        }
    };
    xhr.setRequestHeader('Content-type', 'application/*');
    xhr.send();
}

function downloadReport(path) {
    $(".overlay").removeClass("d-none");
    var xhr = new XMLHttpRequest();
    xhr.open('GET', path, true);
    xhr.responseType = 'arraybuffer';
    xhr.onload = function() {
        if(this.status == '200') {
            var filename = '';
            //get the filename from the header.
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches !== null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var type = xhr.getResponseHeader('Content-Type');
            var blob = new Blob([this.response],  {type: type});
            if(typeof window.navigator.msSaveBlob != 'undefined') {
                window.navigator.msSaveBlob(blob, filename);
            }
            else {
                var URL = window.URL || window.webkitURL;
                console.log("ELSE");
                var download_URL = URL.createObjectURL(blob);
                if(filename) {
                    var a_link = document.createElement('a');
                    if(typeof a_link.download == 'undefined') {
                        window.open(download_URL);
                        $(".overlay").addClass("d-none");
                    } else {
                        a_link.href = download_URL;
                        a_link.download = filename;
                        document.body.appendChild(a_link);
                        a_link.click();
                        $(".overlay").addClass("d-none");
                    }
                } else {
                    window.open(download_URL);
                    $(".overlay").addClass("d-none");
                }
                setTimeout(function() {
                    URL.revokeObjectURL(download_URL);
                }, 10000);
            }
        }else {
            alert('error');
        }
    };
    xhr.setRequestHeader('Content-type', 'application/*');
    xhr.send();
}



