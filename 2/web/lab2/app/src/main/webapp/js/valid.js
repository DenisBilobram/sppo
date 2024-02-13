function fieldValidator(fieldEl, borderMin, borderMax, errorsEl, message) {

    while (errorsEl.firstChild) {
        errorsEl.removeChild(errorsEl.firstChild);
    }

    const inputValue = fieldEl.value;

    // Выполняем валидацию (в данном случае, проверяем, что введенные данные не пусты)
    if (inputValue.trim() === '' || isNaN(inputValue) || parseFloat(inputValue) <= borderMin || parseFloat(inputValue) >= borderMax) {
        // Если валидация не прошла, выводим ошибку
        const newSpan = document.createElement('span');
        newSpan.textContent = message;
        newSpan.style.color = 'red';
        fieldEl.style.borderColor = 'red';
        errorsEl.appendChild(newSpan);
        document.getElementById("submitBut").setAttribute("disabled", "disabled");
    } else {
        // Если валидация прошла успешно, очищаем ошибку и стилизацию
        fieldEl.style.borderColor = '';
        if (document.getElementById("form-errors").querySelectorAll('*').length === 1) {
            document.getElementById("submitBut").removeAttribute("disabled");
        }
    }
}


const xInputElement = document.getElementById('xInput');
const xErrorsElement = document.getElementById('xErrors');


xInputElement.addEventListener('input', fieldValidator.bind(null, xInputElement, -3, 3, xErrorsElement, "Неверное значение в поле X."));

let rvalBut = document.getElementsByClassName("selected")[0];
if (rvalBut !== undefined) {
    let clickEvent = new MouseEvent("click", {
        bubbles: true,
        cancelable: true,
        view: window
    });
    rvalBut.dispatchEvent(clickEvent);
}