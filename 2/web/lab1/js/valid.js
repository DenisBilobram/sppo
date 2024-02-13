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
        if (document.getElementById("form-errors").querySelectorAll('*').length === 2) {
            document.getElementById("submitBut").removeAttribute("disabled");
        }
    }
}


const yInputElement = document.getElementById('yInput');
const rInputElement = document.getElementById('rInput');
const yErrorsElement = document.getElementById('yErrors');
const rErrorsElement = document.getElementById('rErrors');

yInputElement.addEventListener('input', fieldValidator.bind(null, yInputElement, -3, 5, yErrorsElement, "Неверное значение в поле Y."));
rInputElement.addEventListener('input', fieldValidator.bind(null, rInputElement, 1, 4, rErrorsElement, "Неверное значение в поле R."));

