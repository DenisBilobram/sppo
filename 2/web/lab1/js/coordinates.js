
var canvas = document.getElementById("coordinates");
var ctx = canvas.getContext("2d");
ctx.beginPath();


ctx.moveTo(10, canvas.height / 2);
ctx.lineTo(canvas.width - 10, canvas.height / 2);
ctx.stroke();
ctx.lineTo(canvas.width - 10, canvas.height / 2 + 5);
ctx.lineTo(canvas.width, canvas.height / 2);
ctx.lineTo(canvas.width - 10, canvas.height / 2 - 5);
ctx.lineTo(canvas.width - 10, canvas.height / 2);
ctx.fillStyle = "black";
ctx.fill();
ctx.strokeText("X", canvas.width - 10, canvas.height / 2 - 15);

ctx.moveTo(canvas.width / 2 + 25, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 + 25, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 + 50, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 + 50, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 + 75, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 + 75, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 + 100, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 + 100, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 - 25, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 - 25, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 - 50, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 - 50, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 - 75, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 - 75, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 - 100, canvas.height / 2 + 5);
ctx.lineTo(canvas.width / 2 - 100, canvas.height / 2 - 5);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 25);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 25);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 50);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 50);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 75);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 75);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 100);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 100);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 25);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 25);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 50);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 50);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 75);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 75);
ctx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 100);
ctx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 100);
ctx.stroke();

ctx.moveTo(canvas.width / 2, canvas.height - 10);
ctx.lineTo(canvas.width / 2, 10);
ctx.stroke();
ctx.lineTo(canvas.width / 2 + 5, 10);
ctx.lineTo(canvas.width / 2, 0);
ctx.lineTo(canvas.width / 2 - 5, 10);
ctx.lineTo(canvas.width / 2, 10);
ctx.fill();
ctx.strokeText("Y", canvas.width / 2 + 15, 10);
ctx.closePath();


var queryString = window.location.search;
queryString = queryString.slice(1);
var queryParams = queryString.split("&?")
var params = {};
for (var i = 0; i < queryParams.length; i++) {
    var pair = queryParams[i].split("=");
    var key = decodeURIComponent(pair[0]);
    var value = decodeURIComponent(pair[1]);
    params[key] = value;
}


if (params.xcoord != undefined && params.ycoord != undefined && params.rval != undefined) {
    var xcoord = parseFloat(params.xcoord);
    var ycoord = parseFloat(params.ycoord);
    var rval = parseFloat(params.rval);
    if (!((xcoord >= -4 && xcoord <= 4) && (ycoord >= -3 && ycoord <= 5) && (rval >= 1 && rval <= 4))) {
        throw "Неверные параметры.";
    }

    var step = 25;
    ctx.beginPath();
    ctx.fillStyle = "rgba(0, 0, 255, 0.7)";

    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo((canvas.width / 2), (canvas.height / 2) - (rval / 2) * step);
    ctx.lineTo((canvas.width / 2) + (rval * step), canvas.height / 2);
    ctx.lineTo(canvas.width / 2, canvas.height / 2);
    ctx.fill();
    ctx.closePath();
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo(canvas.width / 2 + (rval * step), canvas.height / 2);
    ctx.lineTo(canvas.width / 2 + (rval * step), canvas.height / 2 + ((rval / 2) * step));
    ctx.lineTo(canvas.width / 2, canvas.height / 2 + ((rval / 2) * step));
    ctx.lineTo(canvas.width / 2, canvas.height / 2);
    ctx.fill();
    ctx.closePath();
    ctx.beginPath();
    ctx.arc(canvas.width / 2, canvas.height / 2, rval * step, Math.PI / 2, Math.PI);
    ctx.moveTo(canvas.width / 2 - (rval * step), canvas.height / 2);
    ctx.lineTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo(canvas.width / 2, canvas.height / 2 + (rval * step));
    ctx.fill();
    ctx.closePath();

    ctx.beginPath();
    ctx.fillStyle = "red";
    ctx.moveTo(canvas.width / 2 + (xcoord * step), canvas.height / 2 - (ycoord * step));
    ctx.arc(canvas.width / 2 + (xcoord * step), canvas.height / 2 - (ycoord * step), 3, 0, 2 * Math.PI);
    ctx.fill();
    ctx.closePath();

}
