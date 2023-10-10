function highlightButton(buttonValue) {
    
    document.querySelectorAll('input[type="button"]').forEach(button => {
        button.classList.remove("selected")
    });

    event.target.classList.add("selected");

    document.getElementById('rval').value = buttonValue;
    drowRegion(buttonValue);
}

function clearTable() {
    fetch("results", {
        method: "DELETE",
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        window.location.href = "http://localhost:8080/app/results";
    });
}
