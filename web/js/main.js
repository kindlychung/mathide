
var build = "hg19";
var dataFile = "data/" + build + ".csv";

$("#hgBuild").on("change", function () {
    build = this.value;
    dataFile = "data/" + build + ".csv";
    updateCytoband();
})

var chrPosPattern = /(\d+|[xyXY]):(\d+)/g
var userInput = "";

var chrs = [];
var positions = [];
var interpreteInput = function () {
    chrs = [];
    positions = [];
    while (m = chrPosPattern.exec(userInput)) {
        var chr = m[1];
        if (chr.toLowerCase() === "x" || chr.toLowerCase() == "y") {
            chrs.push(chr.toUpperCase());
            var pos = m[2];
            positions.push(pos);
        } else {
            var chrInt = parseInt(chr);
            if (chrInt < 1 || chrInt > 22) {
                continue;
            } else {
                chrs.push(chr);
                var pos = m[2];
                positions.push(pos);
            }
        }
    }
    //console.log(chrs);
    //console.log(positions);
}


$("#sendBtn").click(function() {
    updateCytoband();
});
$("#chrPosInput").bind("keypress", function(event) {
    if(event.which == 13) {
        event.preventDefault();
        updateCytoband();
    }
})

var updateCytoband = function () {
    $("#cytoTable").empty();
    userInput = $("#chrPosInput").val()
    interpreteInput();
    getCytoBand(chrs, positions);
}


var getCytoBand = function (chrSymbol, basePosition) {
    d3.csv(dataFile, function (error, data) {
            filterByOneBasePosition = function (chrSymbol, basePosition) {
                var res = data.filter(function (row) {
                    return row.chr === chrSymbol && +(row.start) <= basePosition && +(row.end) >= basePosition;
                })
                return res;
            };
            var results = [];
            if (!(chrSymbol instanceof Array)) {
                var chrArray = [chrSymbol];
            } else {
                var chrArray = chrSymbol;
            }
            for (var i = 0; i < chrArray.length; i++) {
                chrArray[i] = "chr" + chrArray[i];
            }
            if (!(basePosition instanceof Array)) {
                var bpArray = [];
                bpArray.push(parseInt(basePosition))
            } else {
                var bpArray = [];
                for (var i = 0; i < basePosition.length; i++) {
                    bpArray.push(parseInt(basePosition[i]))
                }
            }
            var l1 = chrSymbol.length;
            var l2 = basePosition.length;
            var minLength = Math.min(l1, l2);
            for (var i = 0; i < minLength; i++) {
                var resOneBp = filterByOneBasePosition(chrSymbol[i], basePosition[i]);
                console.log(resOneBp);
                resOneBp = resOneBp[0];
                resOneBp.pos = basePosition[i];
                results.push([resOneBp["chr"], resOneBp["pos"], resOneBp["cyto"]]);
            }
            var cytoTable = d3.select("#cytoTable");
            var cytoThead = cytoTable.append("thead");
            var header = ["Chromosome", "Position", "Cytoband"];
            var cytoHeads = cytoThead.selectAll("th")
                .data(header)
                .enter()
                .append("th")
                .text(function (d) {
                    return d;
                })
                .style("font-size", "1.3em")
                .style("padding-bottom", "0.3em");

            var cytoTbody = cytoTable.append("tbody");
            var cytoCells = cytoTbody.selectAll("tr")
                .data(results)
                .enter()
                .append("tr")
                .selectAll("td")
                .data(function (d, i) {
                    return d;
                })
                .enter()
                .append("td")
                .text(function (d) {
                    return d;
                })
                .style("text-shadow", "0px 1px 0px rgba(155,155,155,.5)")
        }
    );
}

var popContent ="Chromosome and position seperated by ':'. If you have multiple queries, separate them by any non-digit character.";

$(document).ready(function(){
    $('[data-toggle="popover"]').popover({
        title: "Input format",
        content: popContent,
        placement: "bottom",
    });
});
