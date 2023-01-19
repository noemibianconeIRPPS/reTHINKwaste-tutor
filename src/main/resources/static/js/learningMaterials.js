$(function() {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: window.location.pathname + "/createGraph",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var cy = cytoscape({
                container: document.getElementById('cy'),
                boxSelectionEnabled: false,
                autounselectify: true,

                style: cytoscape.stylesheet()
                    .selector('node')
                    .css({
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'color': 'white',
                        'text-outline-width': 1,
                        'width': 60,
                        'height': 60,
                        'text-outline-color': '#414141',
                        'background-color': '#1cb3a7'
                    })
                    .selector('.moduleNode')
                    .css({
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'color': '#414141',
                        'text-outline-width': 1,
                        'width': 200,
                        'height': 200,
                        'background-color': '#1cb3a7',
                        'font-size': '30px'
                    })
                    .selector('.Video')
                    .css({
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'color': '#414141',
                        'width': 100,
                        'height': 100,
                        'background-color': '#EF8354',
                        'display': 'none',
                        'font-weight': 'normal'
                    })
                    .selector('.Article')
                    .css({
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'color': '#414141',
                        'width': 100,
                        'height': 100,
                        'background-color': '#576066',
                        'display': 'none',
                        'font-weight': 'normal'
                    })
                    .selector('.Goodpractice')
                    .css({
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'color': '#414141',
                        'width': 100,
                        'height': 100,
                        'background-color': '#F2F3AE',
                        'display': 'none',
                        'font-weight': 'normal'
                    })
                    .selector('.Reportstudy')
                    .css({
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'color': '#414141',
                        'width': 100,
                        'height': 100,
                        'background-color': '#C3D350',
                        'display': 'none',
                        'font-weight': 'normal'
                    })
                    .selector('.Other')
                    .css({
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'color': '#414141',
                        'width': 100,
                        'height': 100,
                        'background-color': '#3454D1',
                        'display': 'none',
                        'font-weight': 'normal'
                    })
                    .selector(':selected')
                    .css({
                        'background-color': 'black',
                        'line-color': 'black',
                        'target-arrow-color': 'black',
                        'source-arrow-color': 'black',
                        'text-outline-color': 'black'
                    }).selector('edge')
                    .css({
                        'width': 1,
                        'line-color': '#8d8c8c'
                    }),

                elements: data,

                layout: {
                    name: 'random',
                    padding: 10,
                    spacingFactor: 2
                }
            }).animate({
                fit: {
                    padding: 20
                }
            }, {
                duration: 3000
            }).zoom({
                level: 5.0, // the zoom level
                position: { x: 0, y: 0 }
            });

            cy.on('tap', 'node', function(){
                try {
                    if(this.data('href') !== 'javascript:void(0)') {
                        window.open( this.data('href') );
                    }
                } catch(e){
                    window.location.href = this.data('href');
                }
            });

            cy.on('tap', '.moduleNode', function(){
                //if the node's children have been hidden
                //getting the element at 1 because the element at 0 is the node itself
                //want to check if its children are hidden
                console.log("ENTRO");
                if (this.connectedEdges().targets()[1].style("display") == "none"){
                    //show the nodes and edges
                    this.connectedEdges().targets().style("display", "element");
                } else {
                    //hide the children nodes and edges recursively
                    this.successors().targets().style("display", "none");
                }
            });
        },
        error: function () {

        }
    });



});