
let canvas = document.getElementById("coordinates");
let ctx = canvas.getContext("2d");
ctx.beginPath();


ctx.moveTo(0, canvas.height / 2);
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

ctx.moveTo(canvas.width / 2, canvas.height);
ctx.lineTo(canvas.width / 2, 10);
ctx.stroke();
ctx.lineTo(canvas.width / 2 + 5, 10);
ctx.lineTo(canvas.width / 2, 0);
ctx.lineTo(canvas.width / 2 - 5, 10);
ctx.lineTo(canvas.width / 2, 10);
ctx.fill();
ctx.strokeText("Y", canvas.width / 2 + 15, 10);
ctx.closePath();


canvas = document.getElementById("dots");
canvas.addEventListener('click', function(event) {

    var step = 25;
    var canvas = document.getElementById("dots");
    var rect = canvas.getBoundingClientRect();
    var x = (event.clientX - rect.left - 150)/step;
    var y = -(event.clientY - rect.top - 150)/step;
    

    document.getElementsByClassName("hiddenX")[0].value = x;
    document.getElementsByClassName("hiddenY")[0].value = y;
    document.getElementsByClassName("hiddenR")[0].value = document.getElementsByClassName("rValueInp")[0].value;

    remoteCommandWidget();
}
);

function drowDot(x, y, result) {
    var canvas = document.getElementById("dots");
    var step = 25;
    var canvas = document.getElementById("dots");
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.beginPath();
    ctx.arc(canvas.width/2 + x*step, canvas.height/2 - y*step, 2, 0, 2 * Math.PI);
    
    if (result) {
        ctx.fillStyle = "red";
    } else {
        ctx.fillStyle = "green";
    }
    ctx.fill();
    ctx.closePath();
}

function drowRegion(rval) {
    var canvas = document.getElementById("region");
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    var step = 25;
    ctx.beginPath();
    ctx.fillStyle = "rgba(0, 0, 255, 0.7)";

    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo((canvas.width / 2) + (rval)*step, (canvas.height / 2));
    ctx.lineTo((canvas.width / 2) + (rval)*step, (canvas.height / 2) + (rval/2)*step);
    ctx.lineTo(canvas.width / 2, (canvas.height / 2) + (rval/2)*step);
    ctx.lineTo(canvas.width / 2, (canvas.height / 2));
    ctx.fill();
    ctx.closePath();

    ctx.beginPath();
    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo(canvas.width / 2, canvas.height / 2 - rval*step);
    ctx.lineTo(canvas.width / 2 - (rval * step), canvas.height / 2);
    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.fill();
    ctx.closePath();

    ctx.beginPath();
    ctx.arc(canvas.width / 2, canvas.height / 2, (rval/2) * step, Math.PI / 2, Math.PI );
    ctx.lineTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo(canvas.width / 2, canvas.height / 2 + (rval/2)*step);
    ctx.fill();
    ctx.closePath();
}

drowRegion(document.getElementsByClassName("rValueInp")[0].value);

document.getElementsByClassName("rValueInp")[0].addEventListener("change", function() {
    drowRegion(this.value);
})

